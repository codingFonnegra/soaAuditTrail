package com.esentri.demos.soaaudit;

import com.collaxa.cube.engine.audit.BPELAuditEvent;

public class StepFactory {
    public StepFactory() {
        super();
    }
    
    public static AuditStep createAuditStep(BPELAuditEvent bpelAuditEvent){
        String message= bpelAuditEvent.getMessage();
        if(message.contains("New instance of BPEL process")){
            return new NewInstanceStep(bpelAuditEvent);
        }
        if(message.contains("_cr_")){
            return new CRStep(bpelAuditEvent);
        }
        if(message.contains("Received \"process\" call")){
            return new ReceiveStep(bpelAuditEvent);
        }
        if(message.contains("Updated variable")){
            return new UpdateVariableStep(bpelAuditEvent);
        }
        if(message.contains("Completed assign")){
            return new CompletedAssignStep(bpelAuditEvent);
        }
        if(message.contains("Started invocation of operation")){
            return new StartedInvocationStep(bpelAuditEvent);
        }
        if(message.contains("Invoked 2-way operation")){
            return new InvocationStep(bpelAuditEvent);
        }
        if(message.contains("Invoked 2-way operation")){
            return new InvocationStep(bpelAuditEvent);
        }
        if(message.contains("Reply to partner")){
            return new ReplyStep(bpelAuditEvent);
        }
        if(message.contains("_cl_")){
            return new CLStep(bpelAuditEvent);
        }
        if(message.contains("BPEL process instance")){
            return new CompletedProcessStep(bpelAuditEvent);
        }
        if(message.contains("Faulted while invoking operation")){
            return new FaultedInvocationStep(bpelAuditEvent);
        }
        if(message.contains("has not been caught by a catch block")){
            return new ErrorHandlingStep(bpelAuditEvent);
        }
        if(message.contains("The transaction was rolled back")){
            return new TransactionAnalysisStep(bpelAuditEvent);
        }
        System.out.println("Message could not be parsed " + message);
        return null;

    }
}
