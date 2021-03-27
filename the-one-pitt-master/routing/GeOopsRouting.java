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
import core.SimScenario;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import movement.map.MapRoute;

/**
 *
 * @author User
 */
public class GeOopsRouting implements RoutingDecisionEngineWithCalculation {

    List<DTNHost> destination = new LinkedList<>();
    private Map<DTNHost, Double> Np = new HashMap<DTNHost, Double>();
    private final double MAX_NP = 28000;

    public GeOopsRouting() {
        
    }

     public GeOopsRouting(GeOopsRouting r) {

        //  destinasi2 = getDestinasi2();
    }
    @Override
    public void connectionUp(DTNHost thisHost, DTNHost peer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void connectionDown(DTNHost thisHost, DTNHost peer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doExchangeForNewConnection(Connection con, DTNHost peer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean newMessage(Message m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isFinalDest(Message m, DTNHost aHost) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean shouldSaveReceivedMessage(Message m, DTNHost thisHost) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean shouldSendMessageToHost(Message m, DTNHost otherHost) {
        if (destination.isEmpty()) {
            destination = getDestinasi();
        }

        if (this.nP == MAX_NP) {     
            this.nP = HitungDistance(otherHost);
        }

       
        double otherNp = de.nP;

        if (this.nP < otherNp) {
            return true;
        } else {
            return false;
        }
    }
  

    @Override
    public boolean shouldDeleteSentMessage(Message m, DTNHost otherHost) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean shouldDeleteOldMessage(Message m, DTNHost hostReportingOld) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RoutingDecisionEngineWithCalculation replicate() {
         return new GeOopsRouting(this);
    }

    @Override
    public Map<DTNHost, Double> HitungDistance(DTNHost thisHost) {
        double hasilEuclidian = 0;
        List<List<Coord>> awal = MapRoute.getRouteBus(getRouteIndex(thisHost));
        if (!thisHost.toString().startsWith("s") && (!thisHost.toString().startsWith("t"))) {
            for (List<Coord> a : awal) {
                for (Coord b : a) {
                    double hasil = MAX_NP;
                    for (DTNHost c : destination) {
                        double jarak = b.distance(c.getLocation());
                        hasilEuclidian = (jarak < hasil) ? jarak : hasil;
                        Np.put(c, hasilEuclidian);
                    }
                }
            }
        }
        return Np;
    }

    public List<DTNHost> getDestinasi() {
        //   System.out.println("1");
        List<DTNHost> allNodes = SimScenario.getInstance().getHosts();
//        System.out.println(""+ SimScenario.getInstance().getHosts());
//        System.out.println("data" + allNodes);
        List<DTNHost> dest = new LinkedList<>();
        for (DTNHost h : allNodes) {
            if (h.toString().startsWith("d")) {
                dest.add(h);
            }
        }
        return dest;
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

}
