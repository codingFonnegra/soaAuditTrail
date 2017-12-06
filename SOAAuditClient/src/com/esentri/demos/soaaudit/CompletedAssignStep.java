package com.esentri.demos.soaaudit;

import com.collaxa.cube.engine.audit.BPELAuditEvent;

public class CompletedAssignStep extends AuditStep {
    public CompletedAssignStep(BPELAuditEvent bpelAuditEvent) {
        super(bpelAuditEvent);
    }

    @Override
    public String getTrailMessage() {
        return this.generateBasicTrailMessage().toString();
    }

    @Override
    public boolean isTrackable() {
        
        return true;
    }
}
