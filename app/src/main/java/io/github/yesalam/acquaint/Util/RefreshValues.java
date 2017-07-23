package io.github.yesalam.acquaint.Util;


import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.content.Context;
import android.database.ContentObservable;
import android.util.Log;
import android.widget.Toast;

import io.github.yesalam.acquaint.Pojo.Card.CasePojo;
import io.github.yesalam.acquaint.WebHelper;
import io.github.yesalam.acquaint.WebHelper.CallBack;
import okhttp3.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.Util.writeObject;


/**
 * Created by yesalam on 7/23/17.
 */

public class RefreshValues implements CallBack {
    private static final String LOG_TAG = "RefreshValues";
    private static final int CREATE_CASE_PAGE = 1;
    private static final int FETCH_BRANCH = 2;
    private static final int FETCH_CONTACT = 3;

    private Context context;
    private int requestCode;

    private String currentFetch;

    Queue<String> client_list = new LinkedList<>();

    Queue<String> branch_list = new LinkedList<>();

    ProgressReceiver mProgressReceiver = null;

    public void setProgressReceiver(ProgressReceiver progressReceiver) {
        this.mProgressReceiver = progressReceiver;
    }


    public RefreshValues(Context context) {
        this.context = context;
    }

    public void refresh() {
        if (mProgressReceiver != null) {
            mProgressReceiver.onProgress("Fetching Client");
        }
        loadData();
    }

    private void loadData() {
        //http://myacquaint.com/Users/Cases/NewCases?pno=1&psize=50
        String NEW_CASES_URL = "http://myacquaint.com/Users/Cases/Create";
        requestCode = CREATE_CASE_PAGE;
        loadData(NEW_CASES_URL);
    }

    private void loadData(String url) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Log.e(LOG_TAG, url);
        WebHelper.getInstance(context).requestCall(request, this);
    }


    @Override
    public void onPositiveResponse(String html) {
        switch (requestCode) {
            case CREATE_CASE_PAGE:
                createCasesResponesReader(html);
                if (mProgressReceiver != null) {
                    mProgressReceiver.onProgress("Fetching Branches");
                }
                fetchBranches();
                break;
            case FETCH_BRANCH:
                saveBranch(html);
                fetchBranches();
                break;

            case FETCH_CONTACT:
                saveContact(html);
                fetchContacts();
                break;

            default:
        }
    }

    @Override
    public void onNegativeResponse(int code) {
        if (mProgressReceiver != null) {
            mProgressReceiver.onNegativeProgress();
        }
    }


    public void createCasesResponesReader(String html) {
        Log.e(LOG_TAG, "called createCase");
        Document document = Jsoup.parse(html);
        Element element = document.getElementById("ClientId");
        if (element == null) {
            Log.e(LOG_TAG, "createCases not loaded");
            Element useridnode_error = document.getElementById("UserName");
            if (useridnode_error == null) {
                //noservice
                Log.e(LOG_TAG, "problem with service.retrying");
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(context, "Service Unavailable! Please try later", Toast.LENGTH_SHORT).show();
            } else {
                //credentials mismatch
                Log.e(LOG_TAG, "not LoggedIn. try to login");
                //activity.login();
                loadData();
            }
        } else {
            Log.e(LOG_TAG, "createCase loaded");
            //progressBar.setVisibility(View.GONE);
            parseData(html);
        }
    }

    private void parseData(String html) {
        Document document = Jsoup.parse(html);
        Element pickup_element = document.getElementById("PunchedBy");

        savePickup(pickup_element);

        Element assigned_element = document.getElementById("AssignedTo");

        saveAssigned(assigned_element);

        Element loantype_element = document.getElementById("LoanType");

        saveLoanType(loantype_element);

        Element client_element = document.getElementById("ClientId");

        saveClient(client_element);
    }

    private void fetchBranches() {
        Log.e(LOG_TAG,"called fetchBranches");
        currentFetch = client_list.poll();
        if (currentFetch == null) {
            if (mProgressReceiver != null) {
                mProgressReceiver.onProgress("Fetching Contacts");
            }
            fetchContacts();
            return;
        }

        Log.e(LOG_TAG,currentFetch);

        String BRANCH_URL = "http://myacquaint.com/Users/Cases/GetBranches?client_id=" + currentFetch;

        requestCode = FETCH_BRANCH;
        loadData(BRANCH_URL);
    }

    private String currentBranch;
    private void fetchContacts() {
        Log.e(LOG_TAG,"called fetchContacts");
        currentBranch = branch_list.poll();

        if (currentBranch == null) {
            if (mProgressReceiver != null) {
                mProgressReceiver.onFinishedProgress();
            }
            return;
        }

        Log.e(LOG_TAG,currentBranch);

        String CONTACT_URL = "http://myacquaint.com/Users/Cases/GetBranchContacts?branch_id=" + currentBranch;

        requestCode = FETCH_CONTACT;
        loadData(CONTACT_URL);
    }

    private void saveBranch(String json) {
        Log.e(LOG_TAG,"called saveBranch");
        Log.e(LOG_TAG,json);
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String value = object.getString("id");
                //Log.e(LOG_TAG,value);
                branch_list.add(value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            writeObject(context, "b" + currentFetch, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveContact(String json) {
        Log.e(LOG_TAG,json);
        try {
            writeObject(context, "c" + currentBranch, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveClient(Element client_element) {
        Log.e(LOG_TAG,"called saveClient");
        Elements pickup_elements = client_element.getElementsByTag("option");
        JSONArray jsonArray = new JSONArray();
        for (Element pickup : pickup_elements) {
            String value = pickup.val();
            String name = pickup.text();

            if(value.isEmpty()) value = "0" ;

            Log.e(LOG_TAG,name+" --> "+value);
            client_list.add(value);
            try {
                JSONObject object = new JSONObject();
                object.put("name", name);
                object.put("value", value);
                jsonArray.put(object);
            } catch (JSONException jsonEception) {

            }
        }


        String client = jsonArray.toString();
        try {
            writeObject(context, "client", client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLoanType(Element loantype_element) {
        String loantype = getElementToJsonString(loantype_element);
        try {
            writeObject(context, "loantype", loantype);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveAssigned(Element assigned_element) {
        String assigned = getElementToJsonString(assigned_element);
        try {
            writeObject(context, "assignedto", assigned);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void savePickup(Element pickup_element) {
        String pickup = getElementToJsonString(pickup_element);
        try {
            writeObject(context, "pickup", pickup);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getElementToJsonString(Element element) {
        Elements pickup_elements = element.getElementsByTag("option");
        JSONArray jsonArray = new JSONArray();
        for (Element pickup : pickup_elements) {
            String value = pickup.val();
            String name = pickup.text();
            if(value.isEmpty()) value = "0" ;
            try {
                JSONObject object = new JSONObject();
                object.put("name", name);
                object.put("value", value);
                Log.e(LOG_TAG,name+" --> "+value);
                jsonArray.put(object);
            } catch (JSONException jsonEception) {

            }
        }

        return jsonArray.toString();
    }

    public interface ProgressReceiver {
        void onProgress(String progress);

        void onFinishedProgress();

        void onNegativeProgress();
    }
}
