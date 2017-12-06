package com.esentri.demos.soaaudit;

import com.collaxa.cube.engine.audit.BPELAuditEvent;

public class ReplyStep extends AuditStep {
    public ReplyStep(BPELAuditEvent bpelAuditEvent) {
        super(bpelAuditEvent);
    }

    @Override
    public String getTrailMessage() {
        StringBuffer sb=generateBasicTrailMessage();
        sb.append("[Replied Message: ");
        sb.append(details);    
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean isTrackable() {
        return true;
    }
}
