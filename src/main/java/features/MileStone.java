package features;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import podData.TestRailConfig;
import java.util.*;

public class MileStone extends Generic{

    private String getMilestoneName() {
        return CommonUtil.getCurrentWeekMonday() + " Week Releases";
    }

    public int createMilestone() {
        return createMilestone(getMilestoneName());
    }

    public int createMilestone(String milestoneName) {
        String addMilestoneURL = "/api/v2/add_milestone/" + TestRailConfig.projectId;
        JsonPath resp = getTestRailRequestSpecification().body(Collections.singletonMap("name", milestoneName)).post(TestRailConfig.base + addMilestoneURL).jsonPath();
        startMileStone(resp.getInt("id"));
        return resp.getInt("id");
    }

    Integer getMilestone(String milestoneName) {
        String addMilestoneURL = "/api/v2/get_milestones/" + TestRailConfig.projectId;
        JsonPath resp = getTestRailRequestSpecification().get(TestRailConfig.base + addMilestoneURL).jsonPath();
        return resp.get("milestones.find {milestones -> milestones.name == '" + milestoneName + "' }.id");
    }

    void startMileStone(int milestoneId) {
        String url = "/api/v2/update_milestone/" + milestoneId;
        Response resp = getTestRailRequestSpecification().body(Collections.singletonMap("is_started", true)).post(TestRailConfig.base + url);
        if (resp.statusCode() != 200) resp.prettyPrint();
    }

    public int getMilestoneId() {
        return getMilestoneId(getMilestoneName());
    }

    public int getMilestoneId(String mileStoneName) {
        Integer milestoneId = getMilestone(mileStoneName);
        if (milestoneId == null)
            return createMilestone(mileStoneName);
        return milestoneId;
}
}
