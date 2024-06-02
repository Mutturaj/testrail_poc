package features;




import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import podData.TestRailConfig;

public class Generic {

    public RequestSpecification getTestRailRequestSpecification() {
        return RestAssured.given()
                .header("Authorization", " Basic "+ TestRailConfig.authToken)
                .header("Content-Type", "application/json");
    }

}
