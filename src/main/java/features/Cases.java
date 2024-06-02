package features;

import io.restassured.path.json.JsonPath;


import podData.TestRailConfig;
import podData.POD_Date;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Cases extends Generic {

    HashMap<Integer, Set<Integer>> getTestIds(Set<Integer> testSuitIds, Plan.ReleaseType releaseType, int platform) {
        HashMap<Integer, Set<Integer>> map = new HashMap<>();
        Set<Integer> tempCaseId;
        for (int eachSuite : testSuitIds) {
            tempCaseId = new HashSet<>();
            String query = "/api/v2/get_cases/" + TestRailConfig.projectId + "&suite_id=" + eachSuite + "&template_id=" + platform;
            do {

                //         todo revoke it       JsonPath resp = getTestRailRequestSpecification().get(TestRailConfig.base + query).jsonPath();
                JsonPath resp = getTestRailRequestSpecification().get("https://testrail.phonepe.com/index.php?" + query).jsonPath();
                query = resp.getString("_links.next");
                String jsonQuery = "cases";
                if (releaseType == Plan.ReleaseType.BETA)
                    jsonQuery += ".findAll {cases -> cases.keySet().contains('custom_apps_test_suite') && cases.custom_apps_test_suite.contains(" + Plan.ReleaseType.BETA.getValue() + ")}";
                else if (releaseType == Plan.ReleaseType.RC)
                    jsonQuery += ".findAll {cases -> cases.keySet().contains('custom_apps_test_suite') && (cases.custom_apps_test_suite.contains(" + Plan.ReleaseType.BETA.getValue() + ")|| cases.custom_apps_test_suite.contains(" + Plan.ReleaseType.RC.getValue() + "))}";
                tempCaseId.addAll(resp.getList(jsonQuery + ".id"));
            } while (query != null);

            if (!tempCaseId.isEmpty())
                map.put(eachSuite, tempCaseId);
            else
                System.out.println("No Testcases for " + releaseType + " in " + POD_Date.testSuiteMapper.get(eachSuite));
        }
        return map;
    }

    public static void main(String[] args) {
        HashSet<Integer> set = new HashSet<>();
        set.add(1340);
        System.out.println(new Cases().getTestIds(set, Plan.ReleaseType.RC,5));
}
}