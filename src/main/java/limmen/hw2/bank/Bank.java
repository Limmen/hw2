package limmen.hw2.bank;

import limmen.hw2.client.util.RejectedException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/*
* Remote interface
*/
public interface Bank extends Remote {
    public Account newAccount(String name) throws RemoteException, RejectedException;

    public Account getAccount(String name) throws RemoteException;

    public boolean deleteAccount(String name) throws RemoteException;

    public String[] listAccounts() throws RemoteException;
}
