package features;

import io.restassured.response.Response;
import podData.TestRailConfig;
import podData.POD_Date;
import java.util.*;

public class Plan extends Generic {



    public enum ReleaseType {
        BETA(1),
        RC(2),
        REGRESSION(3);

        private final int value;

        ReleaseType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum PLATFORM {
        ANDROID(1),
        IOS(2),
        WEB(5);

        private final int value;

        PLATFORM(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    void storeTestRunIds(HashMap<Integer, Set<Integer>> testIds, Response response) {
        for (Object each : response.jsonPath().getList("entries")) {
            LinkedHashMap run = (LinkedHashMap) ((ArrayList) ((LinkedHashMap) each).get("runs")).get(0);
            POD_Date.testRunMapper.put((Integer) run.get(("id")), testIds.get((Integer) run.get("suite_id")));
        }
    }


    public void createTestPlan(ReleaseType releaseType, PLATFORM platform, int milestone, String version) {
        HashMap<String, Object> map = new HashMap<>();
        String name = platform + "_" + releaseType;
        if (version.trim().isEmpty()) name += " - "+ CommonUtil.formatDateWithOrdinal();
        else   name += "_" + version;
        map.put("name", name);
        if (milestone != 0)
            map.put("milestone_id", milestone);
        ArrayList<Object> eachSuite = new ArrayList<>();
        Cases cases = new Cases();
        HashMap<Integer, Set<Integer>> testIds = new HashMap<>(cases.getTestIds(POD_Date.testSuiteMapper.keySet(), releaseType, platform.getValue()));
        for (Map.Entry<Integer, Set<Integer>> each : testIds.entrySet())
            eachSuite.add(getTestPlanSuiteObject(each));
        map.put("entries", eachSuite);

        Response resp = getTestRailRequestSpecification().body(map).post(TestRailConfig.base + "/api/v2/add_plan/" + TestRailConfig.projectId);
        if (resp.statusCode() != 200) resp.prettyPrint();
        storeTestRunIds(testIds, resp);
    }

    HashMap<String, Object> getTestPlanSuiteObject(Map.Entry<Integer, Set<Integer>> entry) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("suite_id", entry.getKey());
        map.put("include_all", false);
        map.put("case_ids", entry.getValue());
        return map;
}

        }