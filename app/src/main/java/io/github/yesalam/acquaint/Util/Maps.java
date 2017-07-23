package io.github.yesalam.acquaint.Util;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;


/**
 * Created by yesalam on 19-06-2017.
 */

public class Maps {

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



    public static String getBranch(Context context,String id){
        String json = "" ;
        try {
            json = (String) Util.readObject(context,"b"+id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return json;
    }

    public static String getContact(Context context,String id){
        String contact = "";
        try {
            contact = (String) Util.readObject(context,"c"+id);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contact;
    }



}
