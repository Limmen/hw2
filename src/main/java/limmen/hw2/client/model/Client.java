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
public interface Client {
    
    public void wishNotification();
    public void itemNotification();
    public String getName();
    public Account getAccount();
    public void setAccount(Account acc);
    
}
