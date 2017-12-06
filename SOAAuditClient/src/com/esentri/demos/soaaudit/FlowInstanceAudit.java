package com.esentri.demos.soaaudit;

import java.util.ArrayList;
import java.util.List;

public class FlowInstanceAudit {
    public FlowInstanceAudit() {
        super();
        steps=new ArrayList<AuditStep>();
    }
    
    private String flowID;
    private String startedDate;
    private List<AuditStep> steps;

    public void setFlowID(String flowID) {
        this.flowID = flowID;
    }

    public void setSteps(List<AuditStep> steps) {
        this.steps = steps;
    }

    public List<AuditStep> getSteps() {
        return steps;
    }

    public String getFlowID() {
        return flowID;
    }

    public void setStartedDate(String startedDate) {
        this.startedDate = startedDate;
    }

    public String getStartedDate() {
        return startedDate;
    }
    
    public void printTrace(){
        StringBuffer sb=new StringBuffer("******************** FLOW ID:  ");
        sb.append(flowID);
        sb.append(" ********************");
        System.out.println(sb.toString());
        int i=1;
        for(AuditStep step: steps){
            if(step.isTrackable()){
                sb=new StringBuffer("-------------------- Step ");
                sb.append(i);
                i++;
                sb.append(" --------------------");
                System.out.println(sb.toString());
                System.out.println(step.getTrailMessage());
            }
        }
    }
    
     
}
