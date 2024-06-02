package features;

import java.util.HashMap;
import java.util.LinkedHashMap;
import podData.*;
public class Suite extends Generic {


    public HashMap<Integer, String> getAllSuite() {
        HashMap<Integer, String> mapper = new HashMap<>();
        for (Object eachObject : getTestRailRequestSpecification().get(TestRailConfig.base + "/api/v2/get_suites/" + TestRailConfig.projectId).jsonPath().getList("")) {
            LinkedHashMap<String, Object> eachMap = (LinkedHashMap<String, Object>) eachObject;
            //todo remove after the cleanup for RC suite
            if (!eachMap.get("name").equals("RC Checklist-App"))
                mapper.put((int) eachMap.get("id"), (String) eachMap.get("name"));
        }
        return mapper;
}
}