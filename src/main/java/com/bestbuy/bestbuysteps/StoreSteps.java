package com.bestbuy.bestbuysteps;

import com.bestbuy.constants.EndPoint;
import com.bestbuy.model.ProductPojo;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.yecht.Data;

import java.util.HashMap;

public class StoreSteps {
    @Step("Creating store with Name :{0}, type:{1}, address:{2}, address2 :{3}, city:{4}, state:{5},zip:{6}, lat:{7} , lng:{8}, hours{9} ")
    public ValidatableResponse createStore(String name, String type, String address, String address2, String city, String state,
                                           String zip, int lat, int lng, String hours) {

        StorePojo storePojo = StorePojo.getStorePojo(name, type, address, address2, city, state, zip, lat, lng, hours);

        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .body(storePojo)
                .when()
                .post()
                .then();
    }

    @Step("Getting the store information with name : {0}")
    public HashMap<String, Object> getStoreInformationByName(String name) {

        String s1 = "findAll{it.name == '";
        String s2 = "'}.get(0)";

        return SerenityRest.given()
                .when()
                .get(EndPoint.GET_ALL_STORES)
                .then().statusCode(200)
                .extract()
                .path(s1 + name + s2);

    }

    @Step("Updating store information with storeId :{0} ,Name :{1}, type:{2}, address:{3}, address2 :{4}, city:{5}, state:{6},zip:{7}, lat:{8} , lng:{9}, hours{10} ")
    public ValidatableResponse updateStore(String storeId, String name, String type, String address, String address2, String city, String state,
                                           String zip, int lat, int lng, String hours) {

        StorePojo storePojo = StorePojo.getStorePojo(name, type, address, address2, city, state, zip, lat, lng, hours);

        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .pathParam("storeID", storeId)
                .body(storePojo)
                .when()
                .put(EndPoint.UPDATE_STORE_BY_ID)
                .then();
    }

    @Step("Deleting store information with productID :{0}")
    public ValidatableResponse deleteStore(int storeId) {

        return SerenityRest.given().log().all()
                .pathParam("storeID", storeId)
                .when()
                .delete(EndPoint.DELETE_STORE_BY_ID)
                .then();
    }

    @Step("Getting the product information with storeID : {0} ")
    public ValidatableResponse getStoreById(int storeId) {

        return SerenityRest.given().log().all()
                .pathParam("storeID", storeId)
                .when()
                .get(EndPoint.GET_SINGLE_STORE_BY_ID)
                .then();
    }
}