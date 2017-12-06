package com.esentri.demos.soaaudit;

import com.collaxa.cube.engine.audit.BPELAuditEvent;

public class InvocationStep extends AuditStep {
    public InvocationStep(BPELAuditEvent bpelAuditEvent) {
        super(bpelAuditEvent);
    }

    @Override
    public String getTrailMessage() {
        StringBuffer sb=generateBasicTrailMessage();
        sb.append("[Invocation Messages: ");
        sb.append(details);    
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean isTrackable() {
        return true;
    }
}
