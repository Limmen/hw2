/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.marketplace.integration;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import limmen.hw2.marketplace.model.Item;
import limmen.hw2.marketplace.model.ItemImpl;
import limmen.hw2.marketplace.model.ListedItem;
import limmen.hw2.marketplace.model.ListedItemImpl;
import limmen.hw2.marketplace.model.SoldItem;
import limmen.hw2.marketplace.model.SoldItemImpl;
import limmen.hw2.marketplace.model.Wish;
import limmen.hw2.marketplace.model.WishImpl;

/**
 *
 * @author kim
 */
public class QueryManager {
    private DBhandler db;
    public QueryManager(DBhandler db){
        this.db = db;
    }
    
    public ArrayList<Item> getItems() throws RemoteException{
        ArrayList<Item> items = new ArrayList();
        ResultSet res = null;
        Statement stmt = null;
        if(db.isConnected()){
            try{
                String sql = "SELECT * FROM item;";
                stmt = db.getConnection().createStatement();
                res = stmt.executeQuery(sql);
                while(res.next()){
                    items.add(new ItemImpl(res.getInt("itemId"),res.getString("itemname"),
                            res.getString("description"), res.getFloat("price")));
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                try{
                    if(res != null)
                        res.close();
                    if(stmt != null)
                        stmt.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return items;
    }
    
    public ArrayList<ListedItem> getListedItems() throws RemoteException{
        ArrayList<ListedItem> listedItems = new ArrayList();
        ResultSet res = null;
        Statement stmt = null;
        if(db.isConnected()){
            try{
                String sql = "SELECT * FROM listeditem;";
                stmt = db.getConnection().createStatement();
                res = stmt.executeQuery(sql);
                while(res.next()){
                    listedItems.add(new ListedItemImpl(getItem(res.getInt("itemId")),
                            res.getString("seller")));
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                try{
                    if(res != null)
                        res.close();
                    if(stmt != null)
                        stmt.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return listedItems;
    }
    public Item getItem(int id) throws RemoteException{
        Item item = null;
        ResultSet res = null;
        Statement stmt = null;
        if(db.isConnected()){
            try{
                String sql = "SELECT * FROM item"
                        + "WHERE itemId = " + id
                        + ";";
                stmt = db.getConnection().createStatement();
                res = stmt.executeQuery(sql);
                while(res.next()){
                    item = new ItemImpl(res.getInt("itemId"), res.getString("name"),
                            res.getString("description"), res.getFloat("price"));
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                try{
                    if(res != null)
                        res.close();
                    if(stmt != null)
                        stmt.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return item;
    }
    
    public void newSoldItem(String buyer, String seller, int itemId) throws RemoteException{
        Statement stmt = null;
        if(db.isConnected()){
            try{
                String sql = "INSERT INTO solditem(buyer,seller,itemID)"
                        + " VALUES('" + buyer + "' ,'" + seller + "' , " + itemId
                        + ");";
                stmt = db.getConnection().createStatement();
                stmt.executeUpdate(sql);
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                try{
                    if(stmt != null)
                        stmt.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public void newItem(String descr, String name, float price) throws RemoteException{
        Statement stmt = null;
        if(db.isConnected()){
            try{
                String sql = "INSERT INTO item(description,name,price)"
                        + " VALUES('" + descr + "' ,'" + name + "' , " + price
                        + ");";
                stmt = db.getConnection().createStatement();
                stmt.executeUpdate(sql);
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                try{                    
                    if(stmt != null)
                        stmt.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public void newWish(String itemname, float price, String user) throws RemoteException{
        newItem("",itemname,price);
        ResultSet res = null;
        Statement stmt = null;
        if(db.isConnected()){
            try{
                String sql = "SELECT itemID FROM item WHERE itemname ='"
                        + itemname + "' AND price  =" + price + ";";
                stmt = db.getConnection().createStatement();
                res = stmt.executeQuery(sql);
                int itemid = -1;
                while(res.next()){
                    itemid = res.getInt("itemId");                    
                }
                sql = "INSERT INTO wish(itemid, member)"
                        + "VALUES(" + itemid + ",'" + user+"');";
                stmt = db.getConnection().createStatement();
                stmt.executeUpdate(sql);
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                try{
                    if(res != null)
                        res.close();
                    if(stmt != null)
                        stmt.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public ArrayList<String> getUsers() throws RemoteException{
        ResultSet res = null;
        Statement stmt = null;
        ArrayList<String> users = new ArrayList();
        if(db.isConnected()){
            try{
                String sql = "SELECT * FROM member;";
                stmt = db.getConnection().createStatement();
                res = stmt.executeQuery(sql);
                while(res.next()){
                    users.add (res.getString("username"));                    
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                try{
                    if(res != null)
                        res.close();
                    if(stmt != null)
                        stmt.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return users;
    }
    public void newUser(String user, String pw) throws RemoteException{
        Statement stmt = null;
        if(db.isConnected()){
            try{
                String sql = "INSERT INTO member(username,password)"
                        + " VALUES('" + user + "' ,'" + pw
                        + "');";
                stmt = db.getConnection().createStatement();
                stmt.executeUpdate(sql);                
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                try{
                    if(stmt != null)
                        stmt.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public ArrayList<Wish> getWishes() throws RemoteException{
        ResultSet res = null;
        Statement stmt = null;
        ArrayList<Wish> wishes= new ArrayList();
        if(db.isConnected()){
            try{
                String sql = "SELECT * FROM wish;";
                stmt = db.getConnection().createStatement();
                res = stmt.executeQuery(sql);
                while(res.next()){
                    int itemid = res.getInt("itemid");
                    wishes.add(new WishImpl(getItem(itemid), res.getString("wisher")));
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                try{
                    if(res != null)
                        res.close();
                    if(stmt != null)
                        stmt.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return wishes;
    }
    public void newListedItem(String itemname,String descr,float price, String user) throws RemoteException{
        newItem(descr,itemname,price);
        ResultSet res = null;
        Statement stmt = null;
        if(db.isConnected()){
            try{
                String sql = "SELECT itemID FROM item WHERE itemname ='"
                        + itemname + "' AND price  =" + price
                        + "AND description = '" + descr + "';";
                stmt = db.getConnection().createStatement();
                res = stmt.executeQuery(sql);
                int itemid = -1;
                while(res.next()){
                    itemid = res.getInt("itemId");
                }
                sql = "INSERT INTO listeditem(itemid, seller)"
                        + "VALUES(" + itemid + ",'" + user+"');";
                stmt = db.getConnection().createStatement();
                stmt.executeUpdate(sql);
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                try{
                    if(res != null)
                        res.close();
                    if(stmt != null)
                        stmt.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void removeWish(String itemname, float price, String user) throws RemoteException{
        Statement stmt = null;
        if(db.isConnected()){
            try{
                String sql = "DELETE FROM wish WHERE itemID IN("
                        + "SELECT * FROM item WHERE itemname ='" + itemname +
                        "' AND price = " + price + ")" + "AND wisher "
                        + "='" + user + "';";
                stmt = db.getConnection().createStatement();
                stmt.executeUpdate(sql);
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                try{
                    if(stmt != null)
                        stmt.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void removeListedItem(String itemname,String descr,float price, String user) throws RemoteException{
        Statement stmt = null;
        if(db.isConnected()){
            try{
                String sql = "DELETE FROM listeditem WHERE itemID IN("
                        + "SELECT * FROM item WHERE itemname ='" + itemname +
                        "' AND price = " + price + "AND description = '"
                        + descr + "')" + "AND seller "
                        + "='" + user + "';";
                stmt = db.getConnection().createStatement();
                stmt.executeUpdate(sql);
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                try{
                    if(stmt != null)
                        stmt.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    
    public ArrayList<SoldItem> getSold() throws RemoteException{
        ResultSet res = null;
        Statement stmt = null;
        ArrayList<SoldItem> sold= new ArrayList();
        if(db.isConnected()){
            try{
                String sql = "SELECT * FROM solditem;";
                stmt = db.getConnection().createStatement();
                res = stmt.executeQuery(sql);
                while(res.next()){
                    int itemid = res.getInt("itemid");
                    sold.add(new SoldItemImpl(res.getString("seller"),
                            res.getString("buyer"),getItem(itemid)));
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                try{
                    if(res != null)
                        res.close();
                    if(stmt != null)
                        stmt.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return sold;
    }
    public String getPassword(String user) throws RemoteException{
        ResultSet res = null;
        Statement stmt = null;
        String pw = null;
        if(db.isConnected()){
            try{
                String sql = "SELECT password FROM member"
                        + " WHERE username ='" + user + "';";
                stmt = db.getConnection().createStatement();
                res = stmt.executeQuery(sql);
                while(res.next()){
                    pw = res.getString("password");
                }
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            finally{
                try{
                    if(res != null)
                        res.close();
                    if(stmt != null)
                        stmt.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return pw;
    }
    
}

