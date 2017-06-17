package io.github.yesalam.acquaint.Util;

import android.content.Context;

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

    public static final String client_101 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Branch\"},{\"idSortby\":2,\"id\":106,\"name\":\"GWALIOR\"}]";
    public static final String branch_106 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":20,\"name\":\"Team\"}]";
    public static final String client_100 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Branch\"},{\"idSortby\":2,\"id\":112,\"name\":\"AIRPORT ROAD\"},{\"idSortby\":3,\"id\":113,\"name\":\"ALAPUR\"},{\"idSortby\":4,\"id\":111,\"name\":\"CITY CENTER\"},{\"idSortby\":5,\"id\":16195,\"name\":\"D.R.D.E\"},{\"idSortby\":6,\"id\":148,\"name\":\"DABRA BUZURG \"},{\"idSortby\":7,\"id\":108,\"name\":\"G R M C\"},{\"idSortby\":8,\"id\":107,\"name\":\"GWALIOR MAIN BRANCH\"},{\"idSortby\":9,\"id\":9191,\"name\":\"HAZIRA\"},{\"idSortby\":10,\"id\":147,\"name\":\"HOSPITAL ROAD DABRA\"},{\"idSortby\":11,\"id\":114,\"name\":\"JAYENDRA GANJ\"},{\"idSortby\":12,\"id\":110,\"name\":\"KAMPOO\"},{\"idSortby\":13,\"id\":119,\"name\":\"LASHKAR BRANCH\"},{\"idSortby\":14,\"id\":109,\"name\":\"M P S T \"},{\"idSortby\":15,\"id\":120,\"name\":\"MAYUR MARKET\"},{\"idSortby\":16,\"id\":117,\"name\":\"MORAR BARADARI\"},{\"idSortby\":17,\"id\":115,\"name\":\"MORAR BRANCH\"},{\"idSortby\":18,\"id\":116,\"name\":\"MOTI MAHAL\"},{\"idSortby\":19,\"id\":118,\"name\":\"PBB\"},{\"idSortby\":20,\"id\":122,\"name\":\"R A C P C \"},{\"idSortby\":21,\"id\":124,\"name\":\"RAM BAGH COLONY \"},{\"idSortby\":22,\"id\":8188,\"name\":\"RBO\"},{\"idSortby\":23,\"id\":144,\"name\":\"Subhash ganj\"},{\"idSortby\":24,\"id\":6185,\"name\":\"TANSEN ROAD\"},{\"idSortby\":25,\"id\":121,\"name\":\"TRANSPORT NAGAR\"}]";
    public static final String branch_112 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":56,\"name\":\"JAGDISH BATHAM\"}]";
    public static final String branch_113 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":44,\"name\":\"ANKITA AGRAWAL\"},{\"idSortby\":3,\"id\":15116,\"name\":\"PRAVEEN SANGAN\"}]";
    public static final String branch_111 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":18,\"name\":\"SHAILESH BANJARE\"},{\"idSortby\":3,\"id\":31,\"name\":\"Neha makhija \"}]";
    public static final String branch_16195 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":18117,\"name\":\"D.R.D.E\"}]";
    public static final String branch_148 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":55,\"name\":\"SAKSHI DUBEY \"}]";
    public static final String branch_108 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":12,\"name\":\"KAJAL NIGAM\"}]";
    public static final String branch_107 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":10,\"name\":\"SHARAD KHATRI\"},{\"idSortby\":3,\"id\":11,\"name\":\"HEMANT MEENA\"}]";
    public static final String branch_9191 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":9111,\"name\":\"HAZIRA BRANCH\"}]";
    public static final String branch_147 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":54,\"name\":\"HINA\"}]";
    public static final String branch_114 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":19,\"name\":\"Mr.  Sudin mehto \"},{\"idSortby\":3,\"id\":22,\"name\":\"Hirdesh gupta\"}]";
    public static final String branch_110 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":32,\"name\":\"B R MEENA\"}]";
    public static final String branch_119 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"}]";
    public static final String branch_109 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":13,\"name\":\"V K VERMA\"},{\"idSortby\":3,\"id\":14,\"name\":\"ABHIJIT TIWARI\"},{\"idSortby\":4,\"id\":15,\"name\":\"RAKESH CHHABRA\"},{\"idSortby\":5,\"id\":16,\"name\":\"DINESH MITTAL\"},{\"idSortby\":6,\"id\":17,\"name\":\"AKHILESH SHARMA\"},{\"idSortby\":7,\"id\":24,\"name\":\"NARESH KUMAR RAHEJA\"},{\"idSortby\":8,\"id\":16116,\"name\":\"SHRIKANT KSHATRE\"},{\"idSortby\":9,\"id\":16117,\"name\":\"SANKALP SHRIVASTAVA\"},{\"idSortby\":10,\"id\":22120,\"name\":\"R.K KUKREJA\"},{\"idSortby\":11,\"id\":23120,\"name\":\"NEERAJ DWIVEDI\"}]";
    public static final String branch_120 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":53,\"name\":\"DHIRENDRA KUMAR SAHU\"}]";
    public static final String branch_117 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":9110,\"name\":\"SEMIULKAR\"}]";
    public static final String branch_115 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":45,\"name\":\"DILIP NIGDIKAR\"},{\"idSortby\":3,\"id\":10112,\"name\":\"BHARTI AGRAWAL\"}]";
    public static final String branch_116 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":25,\"name\":\"Himanshu gupta\"}]";
    public static final String branch_118 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":34,\"name\":\"CHANDRIKA DOSHI\"},{\"idSortby\":3,\"id\":35,\"name\":\"ANIL MAURYA\"}]";
    public static final String branch_122 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":4099,\"name\":\"UMESH AGRAWAL\"}]";
    public static final String branch_124 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":21,\"name\":\"PARAS JAIN\"},{\"idSortby\":3,\"id\":23,\"name\":\"O P BAKANA\"}]";
    public static final String branch_8188 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":8105,\"name\":\"VKR SIR\"}]";
    public static final String branch_144 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":51,\"name\":\"SATISH PAL\"}]";
    public static final String branch_6185 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":4102,\"name\":\"AJAY SINGH JADON\"}]";
    public static final String branch_121 = "[{\"idSortby\":1,\"id\":0,\"name\":\"Select a Name\"},{\"idSortby\":2,\"id\":49,\"name\":\"PRAVEEN SANGAMNERKAR\"}]";


    public static Map<Integer, String> getClientHash() {
        Map<Integer, String> map = new HashMap<>();
        map.put(100, client_100);
        map.put(101, client_101);
        return map;
    }

    public static Map<Integer, String> getBranchHash() {
        Map<Integer, String> map = new HashMap<>();
        map.put(106,branch_106);
        map.put(107,branch_107);
        map.put(108,branch_108);
        map.put(109,branch_109);
        map.put(110,branch_110);
        map.put(111,branch_111);
        map.put(112,branch_112);
        map.put(113,branch_113);
        map.put(114,branch_114);
        map.put(115,branch_115);
        map.put(116,branch_116);
        map.put(117,branch_117);
        map.put(118,branch_118);
        map.put(119,branch_119);
        map.put(120,branch_120);
        map.put(121,branch_121);
        map.put(122,branch_122);
        map.put(124,branch_124);
        map.put(144,branch_144);
        map.put(148,branch_148);
        map.put(147,branch_147);
        map.put(16195,branch_16195);
        map.put(6185,branch_6185);
        map.put(8188,branch_8188);
        map.put(9191,branch_9191);

        return map;
    }


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

    public static ArrayList<SpinnerItem> getLoanTypes() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Loan Type", "0"));
        list.add(new SpinnerItem("Business", "BL"));
        list.add(new SpinnerItem("Car Loan", "CL"));
        list.add(new SpinnerItem("Education Loan", "EL"));
        list.add(new SpinnerItem("Gold Loan", "GL"));
        list.add(new SpinnerItem("Home Loan", "HL"));
        list.add(new SpinnerItem("Mortgage Loan", "ML"));
        list.add(new SpinnerItem("Pension Loan", "NL"));
        list.add(new SpinnerItem("Personal Loan", "PL"));

        return list;
    }

    public static ArrayList<SpinnerItem> getPickupByType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select option", "0"));
        list.add(new SpinnerItem("Ankit Mathur", "5072"));
        list.add(new SpinnerItem("Anshul Jain", "3060"));
        list.add(new SpinnerItem("ATUL SATNA", "1043"));
        list.add(new SpinnerItem("BABBAN", "6072"));
        list.add(new SpinnerItem("BRAJENDRA", "3057"));
        list.add(new SpinnerItem("Brajesh singh", "1034"));
        list.add(new SpinnerItem("DHARMVEER SHAKYA", "1012"));
        list.add(new SpinnerItem("DHEERAJ", "1037"));
        list.add(new SpinnerItem("MANISH JAIN", "1018"));
        list.add(new SpinnerItem("MUNESH RAGHUVANSHI", "1019"));
        list.add(new SpinnerItem("R.S. TOMAR", "2057"));
        list.add(new SpinnerItem("RAHUL NIGAM", "1051"));
        list.add(new SpinnerItem("RAJNISH", "1053"));
        list.add(new SpinnerItem("RAM AVTAR KUSHWAH", "1010"));
        list.add(new SpinnerItem("SAHID KHAN", "3067"));
        list.add(new SpinnerItem("SAMEER DIXIT", "1054"));
        list.add(new SpinnerItem("SHUBHAM SHINDE", "1013"));
        list.add(new SpinnerItem("SURVESH", "1052"));


        return list;
    }

    public static ArrayList<SpinnerItem> getAssignedToType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select option", "0"));
        list.add(new SpinnerItem("Ankit Mathur", "5072"));
        list.add(new SpinnerItem("ATUL SATNA", "1043"));
        list.add(new SpinnerItem("BABBAN", "6072"));
        list.add(new SpinnerItem("BRAJENDRA", "3057"));
        list.add(new SpinnerItem("Brajesh singh", "1034"));
        list.add(new SpinnerItem("DHARMVEER SHAKYA", "1012"));
        list.add(new SpinnerItem("DHEERAJ", "1037"));
        list.add(new SpinnerItem("MUNESH RAGHUVANSHI", "1019"));
        list.add(new SpinnerItem("R.S. TOMAR", "2057"));
        list.add(new SpinnerItem("RAHUL NIGAM", "1051"));
        list.add(new SpinnerItem("RAJNISH", "1053"));
        list.add(new SpinnerItem("RAM AVTAR KUSHWAH", "1010"));
        list.add(new SpinnerItem("SAHID KHAN", "3067"));
        list.add(new SpinnerItem("SAMEER DIXIT", "1054"));
        list.add(new SpinnerItem("SHUBHAM SHINDE", "1013"));
        list.add(new SpinnerItem("SURVESH", "1052"));


        return list;
    }

    public static ArrayList<SpinnerItem> getBinaryType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Option","0"));
        list.add(new SpinnerItem("NO", "false"));
        list.add(new SpinnerItem("YES", "true"));
        return list;
    }

    public static ArrayList<SpinnerItem> getGenderType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Female", "F"));
        list.add(new SpinnerItem("Male", "M"));
        return list;
    }

    public static ArrayList<SpinnerItem> getAddressType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Option","0"));
        list.add(new SpinnerItem("Office", "O"));
        list.add(new SpinnerItem("Residence", "R"));
        return list;
    }

    public static ArrayList<SpinnerItem> getStatusType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Status","0"));
        list.add(new SpinnerItem("In Process", "I"));
        list.add(new SpinnerItem("Negative", "F"));
        list.add(new SpinnerItem("New", "N"));
        list.add(new SpinnerItem("Positive", "T"));
        list.add(new SpinnerItem("Refer to Credit", "R"));
        list.add(new SpinnerItem("Untraceable", "U"));
        return list;
    }

    public static ArrayList<SpinnerItem> getAddressConfirmedByType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Option","0"));
        list.add(new SpinnerItem("Self", "S"));
        list.add(new SpinnerItem("Family Member", "F"));
        list.add(new SpinnerItem("Guard", "G"));
        list.add(new SpinnerItem("Neighbour", "N"));
        return list;
    }

    public static ArrayList<SpinnerItem> getResidenceProofType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Proof Type","0"));
        list.add(new SpinnerItem("PAN Card", "P"));
        list.add(new SpinnerItem("Voter Card", "T"));
        list.add(new SpinnerItem("D Licence", "M"));
        list.add(new SpinnerItem("Passport", "N"));
        list.add(new SpinnerItem("R Card", "L"));
        list.add(new SpinnerItem("Utility Bills", "I"));
        return list;
    }

    public static ArrayList<SpinnerItem> getOfficeProofType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Proof Type","0"));
        list.add(new SpinnerItem("Visiting Card", "V"));
        list.add(new SpinnerItem("Letter Head", "L"));
        list.add(new SpinnerItem("Old Envelope", "O"));
        list.add(new SpinnerItem("Bill Copy", "B"));
        list.add(new SpinnerItem("Not Provided", "N"));
        return list;
    }

    public static ArrayList<SpinnerItem> getRecommendationType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Recommendation","0"));
        list.add(new SpinnerItem("Negative", "F"));
        list.add(new SpinnerItem("Positive", "T"));
        list.add(new SpinnerItem("Refer to Credit", "R"));
        list.add(new SpinnerItem("Untraceable", "U"));
        return list;
    }

    public static ArrayList<SpinnerItem> getRelationType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Relation","0"));
        list.add(new SpinnerItem("Aunty", "A"));
        list.add(new SpinnerItem("Brother", "B"));
        list.add(new SpinnerItem("Brother in Law", "AB"));
        list.add(new SpinnerItem("BROTHER'S WIFE", "X"));
        list.add(new SpinnerItem("COUSIN", "CC"));
        list.add(new SpinnerItem("Daughter", "D"));
        list.add(new SpinnerItem("DAUGHTER-IN-LAW", "Y"));
        list.add(new SpinnerItem("Father", "F"));
        list.add(new SpinnerItem("Father in Law", "FL"));
        list.add(new SpinnerItem("Grand Father", "G"));
        list.add(new SpinnerItem("Grand Mother", "I"));
        list.add(new SpinnerItem("Grand Son", "AA"));
        list.add(new SpinnerItem("Guard", "Z"));
        list.add(new SpinnerItem("Husband", "H"));
        list.add(new SpinnerItem("LandLord", "L"));
        list.add(new SpinnerItem("MOTHER", "M"));
        list.add(new SpinnerItem("MOTHER IN LAW", "C"));
        list.add(new SpinnerItem("Neighbour", "P"));
        list.add(new SpinnerItem("Nephew", "N"));
        list.add(new SpinnerItem("Self", "O"));
        list.add(new SpinnerItem("Sister", "T"));
        list.add(new SpinnerItem("Son", "S"));
        list.add(new SpinnerItem("Tenent", "E"));
        list.add(new SpinnerItem("Uncle", "U"));
        list.add(new SpinnerItem("Wife", "W"));

        return list;
    }

    public static ArrayList<SpinnerItem> getFamilyMemberType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Option","0"));
        list.add(new SpinnerItem("1", "1"));
        list.add(new SpinnerItem("2", "2"));
        list.add(new SpinnerItem("3", "3"));
        list.add(new SpinnerItem("4", "4"));
        list.add(new SpinnerItem("5", "5"));
        list.add(new SpinnerItem("6", "6"));
        list.add(new SpinnerItem("7", "7"));
        list.add(new SpinnerItem("8", "8"));
        list.add(new SpinnerItem("9", "9"));
        list.add(new SpinnerItem("10", "10"));
        return list;
    }

    public static ArrayList<SpinnerItem> getResidenceStatus() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Option","0"));
        list.add(new SpinnerItem("Owned", "O"));
        list.add(new SpinnerItem("Rented", "R"));
        list.add(new SpinnerItem("Coprovided", "C"));
        list.add(new SpinnerItem("PG", "G"));
        list.add(new SpinnerItem("Parents", "P"));
        list.add(new SpinnerItem("Relative", "F"));
        return list;
    }

    public static ArrayList<SpinnerItem> getMonthType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Month  ","0"));
        list.add(new SpinnerItem("Jan", "Jan-"));
        list.add(new SpinnerItem("Feb", "Feb-"));
        list.add(new SpinnerItem("Mar", "Mar-"));
        list.add(new SpinnerItem("Apr", "Apr-"));
        list.add(new SpinnerItem("May", "May-"));
        list.add(new SpinnerItem("Jun", "Jun-"));
        list.add(new SpinnerItem("Jul", "Jul-"));
        list.add(new SpinnerItem("Aug", "Aug-"));
        list.add(new SpinnerItem("Sep", "Sep-"));
        list.add(new SpinnerItem("Oct", "Oct-"));
        list.add(new SpinnerItem("Nov", "Nov-"));
        list.add(new SpinnerItem("Dec", "Dec-"));

        return list;
    }

    public static ArrayList<SpinnerItem> getYearType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Year","0"));
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year; i >= 1900; i--) {
            list.add(new SpinnerItem(String.valueOf(i),String.valueOf(i)));
        }

        return list;
    }

    public static ArrayList<SpinnerItem> getEaseofLocatingType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Option","0"));
        list.add(new SpinnerItem("Easy", "E"));
        list.add(new SpinnerItem("Difficult", "D"));
        list.add(new SpinnerItem("Untraceable", "U"));
        return list;
    }

    public static ArrayList<SpinnerItem> getLocalityType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Option","0"));
        list.add(new SpinnerItem("Posh", "P"));
        list.add(new SpinnerItem("Upper", "U"));
        list.add(new SpinnerItem("Middle", "M"));
        list.add(new SpinnerItem("Lower", "L"));
        return list;
    }

    public static ArrayList<SpinnerItem> getAccomodationType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Option","0"));
        list.add(new SpinnerItem("Bungalow", "B"));
        list.add(new SpinnerItem("Independent House", "N"));
        list.add(new SpinnerItem("Flat", "F"));
        list.add(new SpinnerItem("Hutment", "H"));
        list.add(new SpinnerItem("High Income Area", "I"));
        list.add(new SpinnerItem("L Chawl", "L"));
        list.add(new SpinnerItem("U Chawl", "U"));
        return list;
    }

    public static ArrayList<SpinnerItem> getLivingStandardType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Option","0"));
        list.add(new SpinnerItem("Upper", "U"));
        list.add(new SpinnerItem("U Middle", "T"));
        list.add(new SpinnerItem("Middle", "M"));
        list.add(new SpinnerItem("L Middle", "N"));
        list.add(new SpinnerItem("Lower", "L"));
        return list;
    }

    public static ArrayList<SpinnerItem> getEmployerType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Option","0"));
        list.add(new SpinnerItem("Government", "G"));
        list.add(new SpinnerItem("Public Ltd.", "U"));
        list.add(new SpinnerItem("Private Ltd.", "I"));
        list.add(new SpinnerItem("Proprietorship", "R"));
        list.add(new SpinnerItem("Partnership", "P"));
        list.add(new SpinnerItem("Other", "O"));
        return list;
    }

    public static ArrayList<SpinnerItem> getBusinessType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Option","0"));
        list.add(new SpinnerItem("Manufacturing", "M"));
        list.add(new SpinnerItem("Trading", "T"));
        list.add(new SpinnerItem("Services", "S"));
        list.add(new SpinnerItem("Government", "G"));
        list.add(new SpinnerItem("Other", "O"));
        return list;
    }

    public static ArrayList<SpinnerItem> getBusinessLevelType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Option","0"));
        list.add(new SpinnerItem("Normal", "N"));
        list.add(new SpinnerItem("Average", "A"));
        list.add(new SpinnerItem("Low", "L"));
        return list;
    }

    public static ArrayList<SpinnerItem> getOfficeAmbienceType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Option","0"));
        list.add(new SpinnerItem("Excellent", "E"));
        list.add(new SpinnerItem("Good", "G"));
        list.add(new SpinnerItem("Poor", "P"));
        return list;
    }

    public static ArrayList<SpinnerItem> getOfficeLocalityType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Option","0"));
        list.add(new SpinnerItem("Commercial", "C"));
        list.add(new SpinnerItem("Residential", "R"));
        list.add(new SpinnerItem("Project/Security Area", "P"));
        return list;
    }

    public static ArrayList<SpinnerItem> getEmployementTermType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Option","0"));
        list.add(new SpinnerItem("Full Time", "F"));
        list.add(new SpinnerItem("Pard Time", "P"));
        list.add(new SpinnerItem("Temporary", "I"));
        list.add(new SpinnerItem("Contract", "C"));
        return list;
    }

    public static ArrayList<SpinnerItem> getGradeType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Option","0"));
        list.add(new SpinnerItem("Executive", "F"));
        list.add(new SpinnerItem("Supervisory", "P"));
        list.add(new SpinnerItem("Clerical", "T"));
        list.add(new SpinnerItem("Subordinate", "C"));
        list.add(new SpinnerItem("Other", "O"));
        return list;
    }

    public static ArrayList<SpinnerItem> getClientType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select client", "0"));
        list.add(new SpinnerItem("Indiabulls", "101"));
        list.add(new SpinnerItem("State Bank of India", "100"));

        return list;
    }

}
