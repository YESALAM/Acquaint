package io.github.yesalam.acquaint;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getAddressId(){
        String value = "EditCoApplicant('4816631','4644872')" ;
        int firstA = value.indexOf("'");
        int firstB = value.indexOf("'",firstA);
        String id = value.substring(17,24);
        assertTrue(id.equalsIgnoreCase("4816631"));
    }

    @Test
    public void jsonTest(){
        String json = "{\"Address\":\"GUHISAR BHIND \",\"PersonId\":4946821,\"AddressId\":4946820,\"AssignedTo\":null,\"City\":\"BHIND\",\"CompanyAddress\":null,\"CompanyAddressId\":0,\"CompanyAssignedTo\":null,\"CompanyCity\":null,\"CompanyMobile\":null,\"CompanyName\":null,\"CompanyNeedsVerification\":null,\"CompanyPhone\":null,\"CompanyState\":null,\"DOB\":\"17-02-1985\",\"EMail\":null,\"Name\":\"NEHA TOMAR\",\"Gender\":\"M\",\"Mobile\":null,\"NeedsVerification\":false,\"OfficeStatus\":\"New\",\"PAN\":null,\"Phone\":null,\"Pin\":null,\"State\":\"MADHYA PRADESH\",\"UpdatedByName\":\"MANISH JAIN\",\"UpdatedLast\":\"17-06-2017\",\"ResidenceStatus\":\"New\",\"TeleResiStatus\":\"N\",\"TeleOfficeStatus\":\"N\"}";
        try {
            JSONObject jsonObject = new JSONObject(json);
            Object email = jsonObject.get("AddressId");
            assertNotNull(email);
            assertTrue(email.toString().equalsIgnoreCase("4946820"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}