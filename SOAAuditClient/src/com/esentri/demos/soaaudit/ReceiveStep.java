package com.esentri.demos.soaaudit;

import com.collaxa.cube.engine.audit.BPELAuditEvent;

public class ReceiveStep extends AuditStep {
    public ReceiveStep(BPELAuditEvent bpelAuditEvent) {
        super(bpelAuditEvent);
    }

    @Override
    public String getTrailMessage() {
        StringBuffer sb=generateBasicTrailMessage();
        sb.append("[Received Message: ");
        sb.append(details);    
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean isTrackable() {
        return true;
    }
}
