/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.client.model;

import limmen.hw2.bank.Account;

/**
 *
 * @author kim
 */
public class ClientImpl implements Client {
    
    private String name;
    private Account account;
    
    public ClientImpl(String name){
        this.name = name;
    }

    @Override
    public void wishNotification() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void itemNotification() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        return name;
    }    
    @Override
    public Account getAccount() {
        return account;
    }
   
}
