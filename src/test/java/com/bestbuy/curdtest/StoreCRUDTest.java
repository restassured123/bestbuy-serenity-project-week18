package com.bestbuy.curdtest;

import com.bestbuy.bestbuysteps.StoreSteps;
import com.bestbuy.testbase.StoreTestBase;
import com.bestbuy.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Title;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yecht.Data;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoreCRUDTest extends StoreTestBase {
    static String name = "Prime Store" + TestUtils.getRandomValue();
    static String type = "power" + TestUtils.getRandomValue();
    static String address = "buchanan gardens" + TestUtils.getRandomValue();
    static String address2 = "kensal rise" + TestUtils.getRandomValue();
    static String city = "London" + TestUtils.getRandomValue();
    static String state = "London" + TestUtils.getRandomValue();
    static String zip = "nw10 9se" + TestUtils.getRandomValue();
    static int lat = 20;
    static int lng = 45;
    static String hours = "Mon :9-9; Tue :10-5; Wed: 11-9; Thu : 10-9; Fri : 10-9; Sat: 10-5; Sun:10-5";
    static int storeId;

    StoreSteps storeSteps;

    @Title("This will create new store")
    @Test
    public void test01() {
        storeSteps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours).statusCode(201);
    }

    @Title("Verify if the store was added successfully")
    @Test
    public void test02() {
        HashMap<String, Object> storeMap = storeSteps.getStoreInformationByName(name);
        Assert.assertThat(storeMap, hasValue(name));
        storeId = (int) storeMap.get("id");
    }

    @Title("Update the product information and verify the updated information")
    @Test
    public void test003() {
        name = name + "_updated";

        storeSteps.updateStore(String.valueOf(storeId), name, type, address, address2, city, state, zip, lat, lng, hours).statusCode(200);

        HashMap<String, Object> storeMap = storeSteps.getStoreInformationByName(name);
        Assert.assertThat(storeMap, hasValue(name));
    }
    @Title("Delete the store and verify if the store is deleted")
    @Test
    public void test004(){
        storeSteps.deleteStore(storeId).statusCode(204);
        storeSteps.getStoreById(storeId).statusCode(404);
    }


}
