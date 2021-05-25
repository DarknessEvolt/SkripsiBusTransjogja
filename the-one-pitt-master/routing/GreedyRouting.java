/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routing;

import core.Connection;
import core.Coord;
import core.DTNHost;
import core.Message;
import core.Settings;
import core.SimScenario;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import movement.map.MapRoute;

/**
 *
 * @author WINDOWS_X
 */
public class GreedyRouting implements RoutingDecisionEngineWithCalculation {

    List<DTNHost> destination = new LinkedList<>();
    private final double MAX_NP = 28000;
    private Map<DTNHost, Double> np = new HashMap<DTNHost, Double>();

    public GreedyRouting(Settings s) {

    }

    public GreedyRouting(GreedyRouting r) {

    }

    @Override
    public void connectionUp(DTNHost thisHost, DTNHost peer) {
//        System.out.println("me " + thisHost);
//        System.out.println("de " + peer);
    }

    @Override
    public void connectionDown(DTNHost thisHost, DTNHost peer) {

    }

    @Override
    public void doExchangeForNewConnection(Connection con, DTNHost peer) {

        

    }

    public Map<DTNHost, Double> getNearestPoint() {

        return np;

    }

    @Override
    public boolean newMessage(Message m) {
        return true;
    }

    @Override
    public boolean isFinalDest(Message m, DTNHost aHost) {
        return m.getTo() == aHost;
    }

    public boolean shouldSaveReceivedMessage(Message m, DTNHost thisHost) { // pesan yang masuk dimasukan buffer engga?
        return m.getTo() != thisHost;
    }

    @Override
    public boolean shouldSendMessageToHost(Message m, DTNHost otherHost) {
        
        //mengambil jalur dari pesan
        List<String> jalur = (List) m.getProperty("jalur");
        
        if (m.getTo() == otherHost) {
            return true;

        }
        
        //jika jalur kosong, berarti pesan sudah sampai di rute terakhir, tinggal tunggu ketemu tujuan
        if(jalur.isEmpty()){
            return false;
        
        //jika jalur masih ada isinya
        } else {
            //jika node yang ditemui merupakan node yang tertulis di jalur yang harus dilewati berikutnya
            
//            if(jalur.get(0).contains(otherHost.toString().substring(0, 2))){ 
            if(otherHost.toString().equals(jalur.get(0))){
                //hapus node dari jalur
                jalur.remove(0);
                //update jalur ke pesan
                m.updateProperty("jalur", jalur);
                //kirim pesan
                return true;
            } else {
                //jika node yang ditemui berbeda dengan yang tercatat di jalur pesan, tidak dikirim
                return false;
            }
        }
    }

    private GreedyRouting getOtherDecisionEngine(DTNHost h) {
        MessageRouter otherhost = h.getRouter();
        assert otherhost instanceof DecisionEngineRouter : "This router only works "
                + " with other routers of same type";
        return (GreedyRouting) ((DecisionEngineRouter) otherhost).getDecisionEngine();
    }

    @Override
    public boolean shouldDeleteSentMessage(Message m, DTNHost otherHost) {
        return true;
    }

    @Override
    public boolean shouldDeleteOldMessage(Message m, DTNHost hostReportingOld) {
        return true;
    }

    @Override
    public Map<DTNHost, Double> HitungDistance(DTNHost thisHost) {
        return null;
    }

    @Override
    public RoutingDecisionEngineWithCalculation replicate() {
        return new GreedyRouting(this);
    }
}
