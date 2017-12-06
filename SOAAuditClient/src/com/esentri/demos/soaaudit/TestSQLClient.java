package com.esentri.demos.soaaudit;

import com.collaxa.cube.engine.audit.BPELAuditEvent;

import com.tangosol.io.WrapperBufferInput;
import com.tangosol.io.pof.ConfigurablePofContext;
import com.tangosol.io.pof.PofBufferReader;
import com.tangosol.io.pof.PofContext;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

import java.nio.charset.Charset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import oracle.jdbc.OracleDriver;

import oracle.soa.tracking.core.audit.AuditEvent;

public class TestSQLClient {
    public TestSQLClient() {
        super();
    }

    public void executeQuery() {
        String sql =
            "SELECT CI.COMPOSITE_NAME AS COMPOSITE_NAME,CI.CMPST_ID AS INSTANCE_ID," +
            "  to_char(CI.CREATION_DATE, 'dd/mm/yyyy hh24:mi:ss') AS CREATION_DATE, CI.STATUS AS STEP," +
            "  SOA_UTIL.GET_AUDIT_TRAIL('DEV_SOAINFRA', CI.CIKEY) AS AUDIT_TRAIL FROM" +
            "  DEV_SOAINFRA.CUBE_INSTANCE CI WHERE CI.COMPOSITE_NAME = ?" +
            "  AND CI.CREATION_DATE BETWEEN (sysdate - 1) AND (sysdate) ORDER BY CI.CREATION_DATE DESC ";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = this.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "Sensors");
            rs = ps.executeQuery();
            while (rs.next()) {
                java.sql.Clob clob = rs.getClob("AUDIT_TRAIL");
                char clobVal[] = new char[(int) clob.length()];
                BufferedReader in=new BufferedReader(new InputStreamReader(clob.getAsciiStream(),Charset.forName("UTF-8")));
                in.read(clobVal);
                System.out.println("Composite Name " + new String(clobVal));

                //System.out.println("Composite Name " + rs.getString("AUDIT_TRAIL"));
            }
        } catch (SQLException |IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
                if(con!=null){
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
    
    public void executeSecondQuery() {
        String sql =
            "SELECT CI.CIKEY AS CIKEY,CI.LOG AS LOG FROM DEV_SOAINFRA.AUDIT_TRAIL CI";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String,FlowInstanceAudit> flowInstances= new HashMap<String,FlowInstanceAudit>();
        try {
            con = this.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                byte auditLog[]=rs.getBytes("LOG");
                ByteArrayOutputStream baos= new ByteArrayOutputStream(auditLog.length);
                ByteArrayInputStream bais = new ByteArrayInputStream(auditLog, 0, auditLog.length);
                GZIPInputStream zis = new GZIPInputStream(bais);
                byte[] buf = new byte[2048];
                int j=zis.read(buf, 0, 2048);
                while (j > 0) {
                    baos.write(buf, 0, j);
                    j = zis.read(buf, 0, 2048);
                }
                zis.close();
                /*ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
                int approximateSize = ois.readInt();
                int nEvents = ois.readInt();
                StringBuilder sb = new StringBuilder(approximateSize);
                for (int i = 0; i < nEvents; i++) {
                    sb.append(ois.readChar());
                }
                ois.close();*/
                
                baos.close();
                bais.close();
                //baos.write(auditLog);
                ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
                //PofContext pofContext = new ConfigurablePofContext("com/esentri/demos/soaaudit/soa-audit-pof-config-fabric.xml");
                PofContext pofContext = new ConfigurablePofContext("oracle/soa/tracking/fabric/audit/serializer/soa-audit-pof-config.xml");
                
                WrapperBufferInput wrapperBufferInput = new WrapperBufferInput(ois);
                PofBufferReader pofBufferReader = new PofBufferReader(wrapperBufferInput, pofContext);
                List<AuditEvent> auditEvents = new ArrayList<AuditEvent>(10);
                pofBufferReader.readCollection(0, auditEvents);
                
                for(AuditEvent event:auditEvents){
                    String flowID=Long.toString(event.getFlowId());
                    FlowInstanceAudit fia= flowInstances.get(flowID);
                    if(fia==null){
                        fia= new FlowInstanceAudit();
                        fia.setFlowID(flowID);
                        flowInstances.put(flowID,fia);
                    }
                    if(event.getPayload() != null ){
                        List<AuditStep> steps=fia.getSteps();
                        BPELAuditEvent bpelAuditEvent=(BPELAuditEvent)event.getPayload();
                        steps.add(StepFactory.createAuditStep(bpelAuditEvent));
                    }
                }
            }
            Iterator<FlowInstanceAudit> it=flowInstances.values().iterator();
            while(it.hasNext()){
                FlowInstanceAudit fia=it.next();
                fia.printTrace();
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
                if(con!=null){
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() throws SQLException {
        String username = "dev_soainfra";
        String password = "welcome1";
        String thinConn = "jdbc:oracle:thin:@localhost:1521/SOADB.ORACLE.COM";
        DriverManager.registerDriver(new OracleDriver());
        Connection conn = DriverManager.getConnection(thinConn, username, password);
        conn.setAutoCommit(false);
        return conn;
    }
    
    public static void main(String[] args) {
        new TestSQLClient().executeSecondQuery();
   }
    
    private String getAuditMessage(BPELAuditEvent event){
        String message = event.getCategory()+": " + event.getType() + " -- "+ event.getMessage() + " " + event.getDetails(); 
        return message;
    }
}
