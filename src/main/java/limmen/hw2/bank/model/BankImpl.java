package limmen.hw2.bank.model;

import limmen.hw2.client.util.RejectedException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import limmen.hw2.bank.integration.DBhandler;
import limmen.hw2.bank.integration.QueryManager;

/*
* Bank-implementation. extends UniCastRemoteObject to automaticly export the remote
* object.
* This is the class that represents the bank.
*/
@SuppressWarnings("serial")
public class BankImpl extends UnicastRemoteObject implements Bank {
    private final String bankName;
    private final DBhandler db;
    private final QueryManager qm;

    public BankImpl(String bankName) throws RemoteException {
        super();
        this.bankName = bankName;
        db = new DBhandler();
        qm = new QueryManager(db);
    }


    @Override
    public synchronized Account newAccount(String name) throws RemoteException,
                                                               RejectedException {
        Account account = null;
        if(qm.createAccount(name)){
            account = new AccountImpl(name, qm);
            System.out.println("se.kth.id2212.ex2.Bank: " + bankName + " Account: " + account
                    + " has been created for " + name);
        }
        else{
            throw new RejectedException("Rejected: se.kth.id2212.ex2.Bank: " + bankName
                                        + " Account for: " + name + " already exists: " + account);
        }
        return account;
    }

    @Override
    public synchronized Account getAccount(String name) throws RemoteException {
        return qm.findAccount(name);
    }

    @Override
    public synchronized boolean deleteAccount(String name) throws RemoteException {
        if (!hasAccount(name)) {
            return false;
        }
        qm.deleteAccount(name);
        System.out.println("se.kth.id2212.ex2.Bank: " + bankName + " Account for " + name
                           + " has been deleted");
        return true;
    }
    
    private boolean hasAccount(String name) throws RemoteException {
        return qm.findAccount(name) != null;
    }
    void shutDown(){
        qm.cleanUp();
        db.disconnect();        
    }
}
