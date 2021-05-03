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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import movement.map.MapRoute;

/**
 *
 * @author WINDOWS_X
 */
public class Greedy implements RoutingDecisionEngineWithCalculation {

    List<DTNHost> destination = new LinkedList<>();
    private Map<DTNHost, Integer> direct = new HashMap<DTNHost, Integer>();

    public Greedy(Settings s) {

    }

    public Greedy(Greedy r) {

    }

    @Override
    public void connectionUp(DTNHost thisHost, DTNHost peer) {
    }

    @Override
    public void connectionDown(DTNHost thisHost, DTNHost peer) {

    }

    @Override
    public void doExchangeForNewConnection(Connection con, DTNHost peer) {

        DTNHost myHost = con.getOtherNode(peer);// mantul = membuat koneksi dari drii sendiri untuk node lain terus kekita sendiri untuk mengetahui diri sendiri

        if (destination.isEmpty()) {
            destination = getDestinasi();
        }
        if (direct.isEmpty()) {
            List<Integer> conect = getRouteDirect(myHost);
            List<DTNHost> tujuan = getDestinasi();
            int i = 0;
            for(DTNHost dest : tujuan){
                direct.put(dest, conect.get(i));
                i++;
            }

            }
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
        if (m.getTo() == otherHost) {
            return true;

        }

        if (otherHost.toString().startsWith("s") || otherHost.toString().startsWith("t")) {
            return false;
        } else {

            DTNHost tujuan = m.getTo();
            //   System.out.println(""+tujuan);
            //  System.out.println(m+" : "+tujuan);
            Greedy de = this.getOtherDecisionEngine(otherHost);

            //  System.out.println(otherHost);
            int jarakku = 0;
            double jarakmu = 0;
            if (direct.get(tujuan) != null) {
                jarakku = direct.get(tujuan);
            }

            if (de.direct.get(tujuan) != null) {
                jarakmu = de.direct.get(tujuan);
            }
            //  System.out.println(de.getNearestPoint());
//            
            if(jarakku == 0){
                return false;
            }
            if (jarakku < jarakmu) {

                //System.out.println("tidak kirim");
                return false;
            } else {
                //   System.out.println("kirim");
                return true;

            }
            //if(getRouteDirect(otherHost) <= getRouteDirect(this))

        }
    }

    private Greedy getOtherDecisionEngine(DTNHost h) {
        MessageRouter otherhost = h.getRouter();
        assert otherhost instanceof DecisionEngineRouter : "This router only works "
                + " with other routers of same type";
        return (Greedy) ((DecisionEngineRouter) otherhost).getDecisionEngine();
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
        return new Greedy(this);
    }

    public List<Integer> getRouteDirect(DTNHost thisHost) {
        String route = thisHost.toString();
        List<Integer> baru = new ArrayList<Integer>();
        if (route.startsWith("1A_")) {
            baru.add(1);
            baru.add(1);
            return baru;
        } else if (route.startsWith("1B_")) {
            baru.add(1);
            baru.add(1);
            return baru;
        } else if (route.startsWith("2A_")) {
            baru.add(1);
            baru.add(1);
            return baru;
        } else if (route.startsWith("2B_")) {
            baru.add(1);
            baru.add(1);
            return baru;
        } else if (route.startsWith("3A_")) {
            baru.add(1);
            baru.add(0);
            return baru;
        } else if (route.startsWith("3B_")) {
            baru.add(1);
            baru.add(0);
            return baru;
        } else if (route.startsWith("4A_")) {
            baru.add(1);
            baru.add(1);
            return baru;
        } else if (route.startsWith("4B_")) {
            baru.add(1);
            baru.add(1);
            return baru;
        } else if (route.startsWith("5A_")) {
            baru.add(1);
            baru.add(0);
            return baru;
        } else if (route.startsWith("5B_")) {
            baru.add(1);
            baru.add(0);
            return baru;
        } else if (route.startsWith("6A_")) {
            baru.add(1);
            baru.add(1);
            return baru;
        } else if (route.startsWith("6B_")) {
            baru.add(1);
            baru.add(1);
            return baru;
        } else if (route.startsWith("7_")) {
            baru.add(1);
            baru.add(1);
            return baru;
        } else if (route.startsWith("8_")) {
            baru.add(1);
            baru.add(1);
            return baru;
        } else if (route.startsWith("9_")) {
            baru.add(1);
            baru.add(1);
            return baru;
        } else if (route.startsWith("10_")) {
            baru.add(0);
            baru.add(1);
            return baru;
        } else {
            baru.add(1);
            baru.add(1);
            return baru;
        }
    }

    public List<DTNHost> getDestinasi() {
        List<DTNHost> allNodes = SimScenario.getInstance().getHosts();
        List<DTNHost> dest = new LinkedList<>();
        for (DTNHost h : allNodes) {
            if (h.toString().startsWith("d")) {
                dest.add(h);
            }
        }
        return dest;
    }

}
