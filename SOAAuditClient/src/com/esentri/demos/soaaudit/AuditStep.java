package com.esentri.demos.soaaudit;

import com.collaxa.cube.engine.audit.BPELAuditEvent;

public abstract class AuditStep {
    
   // private static int NEW_INSTANCE
    
    public AuditStep(BPELAuditEvent event) {
        super();
        cikey= event.getCikey();
        scopeId= event.getScopeId();
        category= getStringProperty(event.getCategory());
        type=getStringProperty(event.getType());    
        level=getStringProperty(event.getLevel());
        message=event.getMessage();
        details=event.getDetails();
        eventId=event.getEventId();
        date=event.getDate();
        
    }
    
    protected long cikey;
    protected String scopeId;
    protected String category;
    protected String type;
    protected String level;
    protected String message;
    protected Object details;
    protected long eventId;
    protected long date;

    public void setCikey(long cikey) {
        this.cikey = cikey;
    }

    public long getCikey() {
        return cikey;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    public String getScopeId() {
        return scopeId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public Object getDetails() {
        return details;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;
    }
    
    
    private String getStringProperty(Object original){
        String value=null;
        if(original !=null){
            value=original.toString();
        }
        return value;
    }
    
    public abstract String getTrailMessage();
    
    public abstract boolean isTrackable();
    
    protected StringBuffer generateBasicTrailMessage() {
        StringBuffer sb= new StringBuffer("[Step ID:");
        sb.append(eventId);
        sb.append("][Type: ");
        sb.append(type);
        sb.append("] [Action: ");
        sb.append(message);
        sb.append("]");
        return sb;
    }
}
