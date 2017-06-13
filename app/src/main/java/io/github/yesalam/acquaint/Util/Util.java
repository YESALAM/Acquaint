package io.github.yesalam.acquaint.Util;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by yesalam on 06-06-2017.
 */

public class Util {

    public static final String USER_ID_KEY = "userid";
    public static final String PASSWORD_KEY = "password";
    public static final String IS_LOGGED_KEY = "logged";
    public static final String USER_KEY = "user";
    public static final String ACQUAINT_URL = "http://myacquaint.com" ;

   public enum AcquaintRequestType{
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


}
