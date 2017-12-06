package com.esentri.demos.soaaudit;

import com.collaxa.cube.engine.audit.BPELAuditEvent;

public class StartedInvocationStep extends AuditStep {
    public StartedInvocationStep(BPELAuditEvent bpelAuditEvent) {
        super(bpelAuditEvent);
    }

    @Override
    public String getTrailMessage() {
        return generateBasicTrailMessage().toString();
    }

    @Override
    public boolean isTrackable() {
        return true;
    }
}
