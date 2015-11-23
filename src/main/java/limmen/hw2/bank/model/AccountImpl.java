package limmen.hw2.bank.model;

import limmen.hw2.client.util.RejectedException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import limmen.hw2.bank.integration.QueryManager;
/*
* Account-implementation. extends UniCastRemoteObject to automaticly export the remote
* object.
* This class represents a account in the Bank.
*/
@SuppressWarnings("serial")
public class AccountImpl extends UnicastRemoteObject implements Account {
    private String name;
    private QueryManager qm;

    /**
     * Constructs a persistently named object.
     */
    public AccountImpl(String name, QueryManager qm) throws RemoteException {
        super();
        this.name = name;
        this.qm = qm;
    }

    @Override
    public synchronized void deposit(float value) throws RemoteException,
                                                         RejectedException {
        if (value < 0) {
            throw new RejectedException("Rejected: Account " + name + ": Illegal value: " + value);
        }
        qm.deposit(name, value);
        float balance = qm.getBalance(name);
        System.out.println("Transaction: Account " + name + ": deposit: $" + value + ", balance: $"
                           + balance);
    }

    @Override
    public synchronized void withdraw(float value) throws RemoteException,
                                                          RejectedException {
        if (value < 0) {
            throw new RejectedException("Rejected: Account " + name + ": Illegal value: " + value);
        }
        float balance = qm.getBalance(name);
        if ((balance - value) < 0) {
            throw new RejectedException("Rejected: Account " + name
                                        + ": Negative balance on withdraw: " + (balance - value));
        }
        qm.withdraw(name, value);
        balance = qm.getBalance(name);
        System.out.println("Transaction: Account " + name + ": withdraw: $" + value + ", balance: $"
                           + balance);
    }

    @Override
    public synchronized float getBalance() throws RemoteException {
        return qm.getBalance(name);
    }
}
