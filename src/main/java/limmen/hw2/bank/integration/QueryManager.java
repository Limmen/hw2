/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.bank.integration;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import limmen.hw2.bank.model.Account;
import limmen.hw2.bank.model.AccountImpl;

/**
 *
 * @author kim
 */
public class QueryManager {
    private final DBhandler db;
    private PreparedStatement createAccountStatement;
    private PreparedStatement findAccountStatement;
    private PreparedStatement deleteAccountStatement;
    private PreparedStatement updateBalanceStatement;
    private PreparedStatement getBalanceStatement;
    public QueryManager(DBhandler db){
        this.db = db;
        prepareStatements();
    }
    
    private void prepareStatements() {
        try{
            createAccountStatement = db.getConnection().prepareStatement("INSERT INTO "
                    +"account VALUES (?, 0)");
            findAccountStatement = db.getConnection().prepareStatement("SELECT * from "
                    +"account WHERE username = ?");
            deleteAccountStatement = db.getConnection().prepareStatement("DELETE FROM "
                    + "account WHERE username = ?");
            getBalanceStatement = db.getConnection().prepareStatement("SELECT * FROM "
                    + "account WHERE username = ?");
            updateBalanceStatement = db.getConnection().prepareStatement("UPDATE account "
                    + "SET balance= ? WHERE username = ?");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean createAccount(String user){
        if(db.isConnected()){
            try{
                createAccountStatement.setString(1, user);
                createAccountStatement.executeUpdate();
            }
            catch(SQLException e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
    
    public Account findAccount(String user) throws RemoteException{
        ResultSet res = null;
        AccountImpl acc = null;
        if(db.isConnected()){
            try{
                findAccountStatement.setString(1, user);
                res = findAccountStatement.executeQuery();
                while(res.next()){
                    acc = new AccountImpl(res.getString("username"), this);
                }
            }
            catch(SQLException e){
                e.printStackTrace();
                return null;
            }
            finally{
                try{
                    if(res != null)
                        res.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return acc;
    }
    
    public void deleteAccount(String user){
        if(db.isConnected()){
            try{
                deleteAccountStatement.setString(1, user);
                deleteAccountStatement.executeUpdate();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    
    public float getBalance(String user) throws RemoteException{
        ResultSet res = null;
        float balance = 0;
        if(db.isConnected()){
            try{
                getBalanceStatement.setString(1, user);
                res = getBalanceStatement.executeQuery();
                while(res.next()){
                    balance = res.getFloat("balance");
                }
            }
            catch(SQLException e){
                e.printStackTrace();
                return balance;
            }
            finally{
                try{
                    if(res != null)
                        res.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                    return balance;
                }
            }
        }
        return balance;
    }
    
    public void withdraw(String user, float amount) throws RemoteException{
        ResultSet res = null;
        float balance = 0;
        if(db.isConnected()){
            try{
                getBalanceStatement.setString(1, user);
                res = getBalanceStatement.executeQuery();
                while(res.next()){
                    balance = res.getFloat("balance");
                }
                updateBalanceStatement.setFloat(1, balance - amount);
                updateBalanceStatement.setString(2, user);
                updateBalanceStatement.executeUpdate();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                try{
                    if(res != null)
                        res.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void deposit(String user, float amount) throws RemoteException{
        ResultSet res = null;
        float balance = 0;
        if(db.isConnected()){
            try{
                getBalanceStatement.setString(1, user);
                res = getBalanceStatement.executeQuery();
                while(res.next()){
                    balance = res.getFloat("balance");
                }
                updateBalanceStatement.setFloat(1, balance + amount);
                updateBalanceStatement.setString(2, user);
                updateBalanceStatement.executeUpdate();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                try{
                    if(res != null)
                        res.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public void cleanUp(){
        try{
            createAccountStatement.close();
            findAccountStatement.close();
            deleteAccountStatement.close();
            updateBalanceStatement.close();
            getBalanceStatement.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}

