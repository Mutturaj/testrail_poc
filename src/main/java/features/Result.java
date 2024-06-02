package features;


import io.restassured.response.Response;
import podData.*;
import java.util.Collections;

public class Result extends Generic {
    Run runObj = new Run();

    enum TEST_STATUS {
        PASSED(1),
        BLOCKED(1),
        FAILED_BY_AUTOMATION(5),
        PASSED_BY_AUTOMATION(7);
        private final int value;

        TEST_STATUS(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    void updateResult(int caseId, TEST_STATUS testStatus) {
        Response resp = getTestRailRequestSpecification().body(Collections.singletonMap("status_id", testStatus.getValue())).post(TestRailConfig.base + "/api/v2/add_result_for_case/" + runObj.getTestRunId(caseId) + "/" + caseId);
        if (resp.statusCode() != 200) resp.prettyPrint();
}
}
