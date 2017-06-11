package io.github.yesalam.acquaint.Util;

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
}
