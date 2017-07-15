package io.github.yesalam.acquaint.Util;

import java.util.ArrayList;
import java.util.Calendar;

import io.github.yesalam.acquaint.Pojo.SpinnerItem;

/**
 * Created by yesalam on 19-06-2017.
 */

public class SpinnerLists {

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



    public static ArrayList<SpinnerItem> getInvestigationType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Type","0"));
        list.add(new SpinnerItem("Office", "O"));
        list.add(new SpinnerItem("Residence", "R"));
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

    public static ArrayList<SpinnerItem> getOfficeAddressConfirmedByType() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("Select Option","0"));
        list.add(new SpinnerItem("Self", "S"));
        list.add(new SpinnerItem("Colleagues", "C"));
        list.add(new SpinnerItem("Receptionist", "R"));
        list.add(new SpinnerItem("Guard", "G"));
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
        list.add(new SpinnerItem("Part Time", "P"));
        list.add(new SpinnerItem("Temporary", "T"));
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
