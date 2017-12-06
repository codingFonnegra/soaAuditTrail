package com.esentri.demos.soaaudit;

import com.collaxa.cube.engine.audit.BPELAuditEvent;

public class CRStep extends AuditStep {
    public CRStep(BPELAuditEvent bpelAuditEvent) {
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
