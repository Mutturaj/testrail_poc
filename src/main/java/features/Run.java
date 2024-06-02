package features;

import podData.POD_Date;

import java.util.Map;
import java.util.Set;

public class Run extends Generic {
    public Integer getTestRunId(int caseId){
        for (Map.Entry<Integer, Set<Integer>> entry: POD_Date.testRunMapper.entrySet())
            if (entry.getValue().contains(caseId)) return entry.getKey();
        return null;
    }
}
