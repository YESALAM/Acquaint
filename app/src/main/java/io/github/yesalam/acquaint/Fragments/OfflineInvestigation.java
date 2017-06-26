package io.github.yesalam.acquaint.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.yesalam.acquaint.Activity.Offline;
import io.github.yesalam.acquaint.Adapters.OfflineInvestigationAdapter;
import io.github.yesalam.acquaint.Pojo.Card.InvestigationPojo;
import io.github.yesalam.acquaint.R;
import io.github.yesalam.acquaint.Util.Id.OVerificationId;
import io.github.yesalam.acquaint.Util.Util;
import io.github.yesalam.acquaint.WebHelper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import static io.github.yesalam.acquaint.Util.Util.ACQUAINT_URL;
import static io.github.yesalam.acquaint.Util.Util.ACTION_CANCEL;
import static io.github.yesalam.acquaint.Util.Util.ACTION_REMARK;
import static io.github.yesalam.acquaint.Util.Util.ACTION_SUP_REMARK;
import static io.github.yesalam.acquaint.Util.Util.PENDING_INVESTIGATION;
import static io.github.yesalam.acquaint.Util.Util.writeObject;

/**
 * Created by yesalam on 25-06-2017.
 */

public class OfflineInvestigation extends Fragment implements SwipeRefreshLayout.OnRefreshListener, WebHelper.CallBack {

    String LOG_TAG = "OfflineCases";
    OfflineInvestigationAdapter adapter;
    SwipeRefreshLayout refreshLayout;
    ProgressDialog progressDialog;
    View parentView;
    String type;
    String investigationId;
    String image_file;
    String index;
    InvestigationPojo pojo;

    Offline activity;
    private int position;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Offline) context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_card, container, false);
        refreshLayout = (SwipeRefreshLayout) parentView.findViewById(R.id.swipeContainer);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Submitting Data");


        RecyclerView recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerview);


        adapter = new OfflineInvestigationAdapter(this, new ArrayList<InvestigationPojo>());
        try {
            List<InvestigationPojo> pendingCases = (List<InvestigationPojo>) Util.readObject(getContext(), Util.PENDING_INVESTIGATION);
            if (pendingCases.size() > 0) {
                passData(pendingCases);
            } else {
                //loadData();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //loadData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //loadData();
        }

        setupRecyclerView(recyclerView);
        return parentView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }


    public void passData(List<? extends Object> data) {
        adapter.setDataset((ArrayList<InvestigationPojo>) data);
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
    }

    public void submitData(InvestigationPojo pojo,int position) {
        progressDialog.show();
        this.type = pojo.type;
        this.image_file = pojo.file_name;
        this.investigationId = pojo.id;
        this.pojo = pojo;
        this.position = position;
        loadData(investigationId);
    }

    private void loadData(String investigationId) {
        String TELE_VERIFICATION_DETAIL = "";
        if (type.equalsIgnoreCase("office")) {
            TELE_VERIFICATION_DETAIL = "/Users/FieldInvestigation/OfficeVerification/" + investigationId;
        } else {
            TELE_VERIFICATION_DETAIL = "/Users/FieldInvestigation/ResidenceVerification/" + investigationId;
        }
        final Request request = new Request.Builder()
                .url(ACQUAINT_URL + TELE_VERIFICATION_DETAIL)
                .build();
        WebHelper.getInstance(getContext()).requestCall(request, this);
    }

    private Map<String, String> getValues() {
        Map<String, String> pendingCase = null;
        try {
            pendingCase = (Map<String, String>) Util.readObject(getContext(), investigationId);

        } catch (IOException e) {
            e.printStackTrace();
            //loadData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //loadData();
        }

        return pendingCase;
    }

    @Override
    public void onPositiveResponse(final String html) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> fieldMap = parse(html);
                fieldMap.remove("img_src");
                Map<String, String> valuesMap = getValues();
                if (valuesMap == null) {
                    Log.e(LOG_TAG, "Reading " + investigationId + " file : Error!!!");
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Unexpected Error!", Toast.LENGTH_SHORT).show();
                    removeOffline();
                    return;
                }
                fieldMap.putAll(valuesMap);
                submitMultiPart(fieldMap);
            }
        });

    }

    @Override
    public void onNegativeResponse(int code) {
        progressDialog.dismiss();
        Toast.makeText(activity, "Network Unavailable!", Toast.LENGTH_SHORT).show();
    }


    public void submitMultiPart(final Map<String, String> map) {
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (String key : map.keySet()) {
            requestBodyBuilder.addFormDataPart(key, map.get(key));
        }

        if (image_file != null) {
            File sourceFile = new File(image_file);
            Log.d(LOG_TAG, "File...::::" + sourceFile + " : " + sourceFile.exists());
            final MediaType MEDIA_TYPE = image_file.endsWith("png") ?
                    MediaType.parse("image/png") : MediaType.parse("image/jpeg");
            String filename = image_file.substring(image_file.lastIndexOf("/") + 1);

            requestBodyBuilder
                    .addFormDataPart(OVerificationId.file_name, filename, RequestBody.create(MEDIA_TYPE, sourceFile));

        }


        MultipartBody requestBody = requestBodyBuilder.build();

        String TELE_VERIFICATION_DETAIL = "";
        if (type.equalsIgnoreCase("Office")) {
            TELE_VERIFICATION_DETAIL = "/Users/FieldInvestigation/OfficeVerification/" + investigationId;
        } else {
            TELE_VERIFICATION_DETAIL = "/Users/FieldInvestigation/ResidenceVerification/" + investigationId;
        }

        Request request = new Request.Builder()
                .url(ACQUAINT_URL + TELE_VERIFICATION_DETAIL)
                .post(requestBody)
                .build();
        Log.e(LOG_TAG, ACQUAINT_URL + TELE_VERIFICATION_DETAIL + " submitting data");

        WebHelper.getInstance(getContext()).requestCall(request, new WebHelper.CallBack() {
            @Override
            public void onPositiveResponse(String html) {

                removeOffline();
                Toast.makeText(getContext(), "Data Submited", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                //finish();
            }

            @Override
            public void onNegativeResponse(int code) {
                //cacheData(map);
                Toast.makeText(getContext(), "Error Occured!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                //finish();
            }
        });


    }

    private void removeOffline() {
        try {
            List<InvestigationPojo> pendingCases = (List<InvestigationPojo>) Util.readObject(getContext(), Util.PENDING_INVESTIGATION);
            if (pendingCases.size() > 0) {
                pendingCases.remove(position);
                //progressBar.setVisibility(View.GONE);
            } else {
                //loadData();
            }
            passData(pendingCases);
            writeObject(getContext(),PENDING_INVESTIGATION,pendingCases);
        } catch (IOException e) {
            e.printStackTrace();
            //loadData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //loadData();
        }

        boolean deleted = getContext().deleteFile(investigationId);
    }

    private Map<String, String> parse(String html) {
        Map<String, String> map = new HashMap<>();

        Document document = Jsoup.parse(html);
        if (type.equalsIgnoreCase("Office")) {
            String applicantName = document.select("#formzipcode > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(2) > td.table-number.Impair").text();
            String coApplicantName = document.select("#formzipcode > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(4) > td.table-number").text();
            map.put("applicantName",applicantName);
            map.put("coApplicantName",coApplicantName);
        }else{
            String applicantName = document.select("#formzipcode > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(2) > td.table-number.Impair").text();
            String coApplicantName = document.select("#formzipcode > aside > aside.col-md-8.pull-right.section-right-main.Impair > aside > table > tbody > tr:nth-child(1) > td > table > tbody > tr > td > aside > aside > fieldset > table > tbody > tr:nth-child(4) > td.table-number").text();
            map.put("applicantName", applicantName);
            map.put("coApplicantName", coApplicantName);
        }

        Element body = document.getElementById("body");
        Element form = body.getElementsByTag("form").first();
        Elements elements = form.getElementsByTag("input");
        for (Element input : elements) {
            String name = input.attr("name");
            String value = input.attr("value");

            String type = input.attr("type");
            if (type.equalsIgnoreCase("radio")) {
                String checked = input.attr("checked");
                if (checked.equalsIgnoreCase("checked")) {
                    map.put(name, value);
                }
            } else {
                map.put(name, value);
            }

        }

        Elements selects = form.getElementsByTag("select");
        for (Element select : selects) {
            String id = select.id();
            //Log.e(LOG_TAG,id);
            try {
                String value = select.getElementsByAttributeValue("selected", "selected").first().attr("value");
                //Log.e(LOG_TAG,value);
                map.put(id, value);
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                map.put(id, "");
            }
        }

        Elements imgs = form.getElementsByTag("img");
        if (imgs != null) {
            for (int i = 0; i < imgs.size(); i++) {
                String src = imgs.get(i).attr("src");
                map.put("img_src" + i, src);
                Log.e(LOG_TAG, "img : " + src);
            }
        }

        Elements textareas = form.getElementsByTag("textarea");
        for (Element textarea : textareas) {
            map.put(textarea.id(), textarea.text());
        }

        map.remove("");
        map.remove(ACTION_CANCEL);
        map.remove(ACTION_REMARK);
        map.remove(ACTION_SUP_REMARK);

        return map;
    }




}
