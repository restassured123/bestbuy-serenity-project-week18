package com.bestbuy.bestbuysteps;

import com.bestbuy.constants.EndPoint;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.yecht.Data;

import java.util.HashMap;
import java.util.Objects;

public class ProductSteps {
    @Step("Creating product with Name : {0}, type : {1}, price:{2} , upc :{3}, description : {4}, model:{5}")

    public ValidatableResponse createProduct(String name, String type, double price, String upc, String description, String model) {

        ProductPojo productPojo = ProductPojo.getProductPojo(name, type, price, upc, description, model);

        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .body(productPojo)
                .when()
                .post()
                .then();
    }

    public HashMap<String, Object> getProductInfoByName(String name) {

        String s1 = "findAll{it.name == '";
        String s2 = "'}.get(0)";

        return SerenityRest.given()
                .when()
                .get(EndPoint.GET_ALL_PRODUCTS)
                .then().statusCode(200)
                .extract()
                .path(s1 + name + s2);
    }

    @Step("Updating product information with productId : {0}, name : {1}, type : {2}, upc : {3}, price : {4},  description :  {5}, model : {6}")
    public ValidatableResponse updateProduct(int productId, String name, String type, String upc, double price, String description, String model) {

        ProductPojo productPojo = ProductPojo.getProductPojo(name, type, price, upc, description, model);
        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .pathParam("productID", productId)
                .body(productPojo)
                .when()
                .put(EndPoint.UPDATE_PRODUCT_BY_ID)
                .then();

    }

    @Step("Deleting product information with productID :{0}")
    public ValidatableResponse deleteProduct(int productId) {

        return SerenityRest.given().log().all()
                .pathParam("productID", productId)
                .when()
                .delete(EndPoint.DELETE_PRODUCT_BY_ID)
                .then();
    }

    @Step("Getting the product information with productID : {0} ")
    public ValidatableResponse getProductById(int productId) {

        return SerenityRest.given().log().all()
                .pathParam("productID", productId)
                .when()
                .get(EndPoint.GET_SINGLE_PRODUCT_BY_ID)
                .then();


    }
}
