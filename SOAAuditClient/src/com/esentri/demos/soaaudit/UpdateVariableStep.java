package com.esentri.demos.soaaudit;

import com.collaxa.cube.engine.audit.BPELAuditEvent;

public class UpdateVariableStep extends AuditStep {
    public UpdateVariableStep(BPELAuditEvent bpelAuditEvent) {
        super(bpelAuditEvent);
    }

    @Override
    public String getTrailMessage() {
        StringBuffer sb=generateBasicTrailMessage();
        sb.append("[Updated Variable: ");
        sb.append(details);    
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean isTrackable() {
        
        return true;
    }
}
