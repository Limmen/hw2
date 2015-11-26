/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.marketplace.model;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * MarketServer. Registers the marketplace object at the registry and binds it
 * to a name.
 * @author kim
 */
public class MarketServer {
    private static final String DEFAULT_MARKET_NAME = "ID2212_Buy_and_Sell";
    public MarketServer(){
        try {
            MarketPlace marketobj = new MarketPlaceImpl(DEFAULT_MARKET_NAME);
            // Register the newly created object at rmiregistry.
            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            Naming.rebind(DEFAULT_MARKET_NAME, marketobj);
            System.out.println(marketobj + " is ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        new MarketServer();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                System.out.println("CleanUp fussnc!");
            }
        }));
    }
}
