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
public class GeOops implements RoutingDecisionEngineWithCalculation {

    List<DTNHost> destination = new LinkedList<>();
    private final double MAX_NP = 28000;
    private Map<DTNHost, Double> np = new HashMap<DTNHost, Double>();

    public GeOops(Settings s) {

    }

    public GeOops(GeOops r) {

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

        DTNHost myHost = con.getOtherNode(peer);
       // System.out.println("me " + myHost);
        if (destination.isEmpty()) {
            destination = getDestinasi();
        }
       // System.out.println("dest " + destination);
        if (np.isEmpty()) {
            this.np = HitungDistance(myHost);
        }
       // System.out.println("np " + np);

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

        if (m.getTo() == otherHost) {
            return true;

        }

        if (otherHost.toString().startsWith("s") || otherHost.toString().startsWith("t")) {
            return false;
        } else {

            DTNHost tujuan = m.getTo();
         //   System.out.println(""+tujuan);
          //  System.out.println(m+" : "+tujuan);
            GeOops de = this.getOtherDecisionEngine(otherHost);

          //  System.out.println(otherHost);
            double jarakku = MAX_NP;
            double jarakmu = MAX_NP;
            if (np.get(tujuan) != null) {
                jarakku = np.get(tujuan);
            }
            

            if (de.getNearestPoint().get(tujuan) != null) {
                jarakmu = de.getNearestPoint().get(tujuan);
            }
          //  System.out.println(de.getNearestPoint());
          //  System.out.println("jarakku : "+jarakku);
          //  System.out.println("Jarakmu ("+otherHost+"): "+jarakmu);
          if(otherHost.toString().startsWith("10_")){
             // System.out.println(""+np);
          }
            if (jarakku < jarakmu) {
                //System.out.println("tidak kirim");
                return false;
            } else {
             //   System.out.println("kirim");
                return true;
            }
        }
    }

    private GeOops getOtherDecisionEngine(DTNHost h) {
        MessageRouter otherhost = h.getRouter();
        assert otherhost instanceof DecisionEngineRouter : "This router only works "
                + " with other routers of same type";
        return (GeOops) ((DecisionEngineRouter) otherhost).getDecisionEngine();
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

           double hasilEuclidian = 0;
        List<List<Coord>> awal = MapRoute.getRouteBus(getRouteIndex(thisHost));
        if (!thisHost.toString().startsWith("s") && (!thisHost.toString().startsWith("t"))) {
            for (DTNHost c : destination) {
                double hasil = MAX_NP;
                for (List<Coord> a : awal) {
                    for (Coord b : a) {
                        double jarak = b.distance(c.getLocation());
                        hasil = (jarak < hasil) ? jarak : hasil;
                        
                    }
                    np.put(c, hasil);
                    hasilEuclidian = hasil;
                }
            }
        }
        
        if (thisHost.toString().startsWith("s") || (thisHost.toString().startsWith("t"))){
            for (DTNHost c : destination) {
                np.put(c, MAX_NP);
            }
        }
        
        return np;
    }

    @Override
    public RoutingDecisionEngineWithCalculation replicate() {
        return new GeOops(this);
    }

    public int getRouteIndex(DTNHost thisHost) {
        String route = thisHost.toString();
        if (route.startsWith("1A_")) {
            return 0;
        } else if (route.startsWith("1B_")) {
            return 1;
        } else if (route.startsWith("2A_")) {
            return 2;
        } else if (route.startsWith("2B_")) {
            return 3;
        } else if (route.startsWith("3A_")) {
            return 4;
        } else if (route.startsWith("3B_")) {
            return 5;
        } else if (route.startsWith("4A_")) {
            return 6;
        } else if (route.startsWith("4B_")) {
            return 7;
        } else if (route.startsWith("5A_")) {
            return 8;
        } else if (route.startsWith("5B_")) {
            return 9;
        } else if (route.startsWith("6A_")) {
            return 10;
        } else if (route.startsWith("6B_")) {
            return 11;
        } else if (route.startsWith("7_")) {
            return 12;
        } else if (route.startsWith("8_")) {
            return 13;
        } else if (route.startsWith("9_")) {
            return 14;
        } else if (route.startsWith("10_")) {
            return 15;
        } else {
            return 16;
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
