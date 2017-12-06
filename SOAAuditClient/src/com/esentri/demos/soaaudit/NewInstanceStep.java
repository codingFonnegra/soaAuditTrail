package com.esentri.demos.soaaudit;

import com.collaxa.cube.engine.audit.BPELAuditEvent;

public class NewInstanceStep extends AuditStep {
    public NewInstanceStep(BPELAuditEvent bpelAuditEvent) {
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
