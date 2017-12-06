package com.esentri.demos.soaaudit;

import com.collaxa.cube.engine.audit.BPELAuditEvent;

public class CLStep extends AuditStep {
    public CLStep(BPELAuditEvent bpelAuditEvent) {
        super(bpelAuditEvent);
    }

    @Override
    public String getTrailMessage() {
        return null;
    }

    @Override
    public boolean isTrackable() {
        return false;
    }
}
