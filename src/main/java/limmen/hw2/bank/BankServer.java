package limmen.hw2.bank;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
/*
* Bank server. Creates a instance of a exportable object and binds it to
* a name in the registry.
*/
public class BankServer {
    private static final String USAGE = "java bankrmi.Server <bank_rmi_url>";
    private static final String BANK = "Nordea";

    
    public BankServer(String bankName) {
        try {
            Bank bankobj = new BankImpl(bankName);
            // Register the newly created object at rmiregistry.
            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            //binds the exported object to a name in the RMI registry.
            Naming.rebind(bankName, bankobj);
            System.out.println(bankobj + " is ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length > 1 || (args.length > 0 && args[0].equalsIgnoreCase("-h"))) {
            System.out.println(USAGE);
            System.exit(1);
        }

        String bankName;
        if (args.length > 0) {
            bankName = args[0];
        } else {
            bankName = BANK;
        }

        new BankServer(bankName);
    }
}
