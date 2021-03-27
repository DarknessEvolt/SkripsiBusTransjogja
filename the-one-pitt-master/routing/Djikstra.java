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
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import movement.MapBasedMovement;
import static movement.MapRouteMovementWithStop.ROUTE_FILE_S;
import static movement.MapRouteMovementWithStop.ROUTE_TYPE_S;
import movement.map.DijkstraPathFinder;
import movement.map.MapNode;
import movement.map.MapRoute;

/**
 *
 * @author WINDOWS_X
 */
public class Djikstra implements RoutingDecisionEngine {
    
    private List<MapRoute> allRoutes = null;
    

    public Djikstra(Settings s) {

        String fileName = s.getSetting(ROUTE_FILE_S);  
        int type = s.getInt(ROUTE_TYPE_S);
        allRoutes = MapRoute.readRoutes(fileName, type, SimScenario.getInstance().getMap());
        
        

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public RoutingDecisionEngine replicate() {
        return null;

    }

  

   

}
