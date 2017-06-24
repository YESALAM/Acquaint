package io.github.yesalam.acquaint.Util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.github.yesalam.acquaint.Pojo.Card.InvestigationPojo;
import io.github.yesalam.acquaint.Pojo.SpinnerItem;

/**
 * Created by yesalam on 06-06-2017.
 */

public class Util {

    public static final String USER_ID_KEY = "userid";
    public static final String PASSWORD_KEY = "password";
    public static final String IS_LOGGED_KEY = "logged";
    public static final String USER_KEY = "user";
    public static final String ACQUAINT_URL = "http://myacquaint.com";

    public static final String PENDING_CASES = "pendingcases" ;





    public enum AcquaintRequestType {
        NO_LOGIN,
        LOGIN,
        NEW_CASES,
        COMPLETE_CASES,
        CASE_EDIT_BASIC,
        CASE_EDIT_COAPPLICANT,
        CASE_EDIT_GUARANTOR,
        NEW_FIELD_INVESTIGATION,
        COMPLETE_FIELD_INVESTIGATION,
        FIELD_INVESTIGATION_RESIDENCE_VERIFICATION_ID,
        FIELD_INVESTIGATION_OFFICE_VERIFICATION_ID,
        TELE_VERIFICATION,
        TELE_VERIFICATION_ID,
    }

    public static void writeObject(Context context, String key, Object object) throws IOException {
        FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();
    }

    public static Object readObject(Context context, String key) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = context.openFileInput(key);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        return object;
    }

   public static void deletePreference(Context context){
       File sharedPreferenceFile = new File("/data/data/"+context.getPackageName()+"/shared_prefs/");
       File[] listFiles = sharedPreferenceFile.listFiles();
       for(File file:listFiles){
           file.delete();
       }
   }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getFilesDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


}
