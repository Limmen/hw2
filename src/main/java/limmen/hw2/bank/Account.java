package limmen.hw2.bank;

import limmen.hw2.client.util.RejectedException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/*
* Remote interface
*/
public interface Account extends Remote {
    public float getBalance() throws RemoteException;

    public void deposit(float value) throws RemoteException, RejectedException;

    public void withdraw(float value) throws RemoteException, RejectedException;
}
