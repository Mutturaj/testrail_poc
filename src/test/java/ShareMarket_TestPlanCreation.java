import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import podData.*;
import features.*;

public class ShareMarket_TestPlanCreation {

    public static void main(String[] args) {
        TestRailConfig.base = "https://testrail.phonepe.com/index.php?";
        POD_Date.testSuiteMapper.putAll(new Suite().getAllSuite());
        new Plan().createTestPlan(Plan.ReleaseType.RC, Plan.PLATFORM.WEB, new MileStone().getMilestoneId(), "");
    }

    @BeforeMethod
    @Parameters("executionType")
    public void beforeMethod(String parameter) {
        if (parameter.equals("Local"))
            TestRailConfig.base = "https://testrail.phonepe.com/index.php?";
        else
            TestRailConfig.base = "https://testrail.ittproxy.phonepe.mh1";
    }

    @Test
    @Parameters("MileStoneName")
    public void createMileStone(String mileStoneName) {
        if (mileStoneName.trim().isEmpty())
            new MileStone().createMilestone();
        else
            new MileStone().createMilestone(mileStoneName);
}


        }