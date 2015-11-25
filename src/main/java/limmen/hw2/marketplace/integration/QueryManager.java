/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hw2.marketplace.integration;

import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private PreparedStatement getItemsStatement;
    private PreparedStatement getItemIDStatement;
    private PreparedStatement getItemDataStatement;
    private PreparedStatement newItemStatement;
    private PreparedStatement removeItemStatement;   
    private PreparedStatement getListedItemsStatement;
    private PreparedStatement newListedItemStatement;
    private PreparedStatement removeListedItemStatement;
    private PreparedStatement newSoldItemStatement;
    private PreparedStatement getSoldStatement;
    private PreparedStatement getWishesStatement;  
    private PreparedStatement newWishStatement;
    private PreparedStatement removeWishStatement;
    private PreparedStatement getUsersStatement;
    private PreparedStatement newUserStatement;  
    private PreparedStatement getUserPasswordStatement;
    public QueryManager(DBhandler db){
        this.db = db;
        prepareStatements();
    }
    private void prepareStatements(){
        try{
            getItemsStatement = db.getConnection().prepareStatement("SELECT * FROM item;");
            getItemDataStatement = db.getConnection().prepareStatement("SELECT * FROM item"
                    + " WHERE itemId = ?;");
            getItemIDStatement = db.getConnection().prepareStatement("SELECT * FROM item"
                    + " WHERE itemname = ?"
                    + " AND description = ?"
                    + " AND price = ?;");
            newItemStatement = db.getConnection().prepareStatement("INSERT INTO "
                    + "item(description,itemname,price)"
                    + " VALUES(?,?,?);");
            removeItemStatement = db.getConnection().prepareStatement("DELETE FROM"
                    + " item WHERE itemname = ?"
                    + " AND description = ?"
                    + " AND price = ?;");
            getListedItemsStatement = db.getConnection().prepareStatement("SELECT * FROM listeditem;");
            newListedItemStatement = db.getConnection().prepareStatement("INSERT INTO "
                    + "listeditem(itemid, seller)"
                    + "VALUES(?,?);");
            removeListedItemStatement = db.getConnection().prepareStatement("DELETE FROM "
                    + "listeditem WHERE itemID IN("
                    + "SELECT itemID FROM item WHERE itemname =?"
                    +" AND price = ? AND description = ?)"
                    + "AND seller = ?; ");
            newSoldItemStatement = db.getConnection().prepareStatement("INSERT INTO"
                    + " solditem(buyer,seller,itemID)"
                    + " VALUES(?,?,?);");
            getSoldStatement = db.getConnection().prepareStatement("SELECT * FROM solditem;");
            getWishesStatement = db.getConnection().prepareStatement("SELECT * FROM wish;");
            newWishStatement = db.getConnection().prepareStatement("INSERT INTO "
                    + "wish(itemid, wisher) "
                    + "VALUES(?,?);");
            removeWishStatement = db.getConnection().prepareStatement("DELETE FROM "
                    + "wish WHERE itemID IN("
                    + "SELECT itemID FROM item WHERE itemname = ?"
                    + " AND price = ? AND description = ?) AND wisher = ?;");
            getUsersStatement = db.getConnection().prepareStatement("SELECT * FROM member;");
            newUserStatement = db.getConnection().prepareStatement("INSERT INTO "
                    + "member(username,password)"
                    + " VALUES(?,?);");
            getUserPasswordStatement = db.getConnection().prepareStatement("SELECT password "
                    + "FROM member"
                    + " WHERE username = ?;");
        }
        catch(SQLException e){
            
        }
    }
    public ArrayList<Item> getItems() throws RemoteException{
        ArrayList<Item> items = new ArrayList();
        ResultSet res = null;
        if(db.isConnected()){
            try{
                res = getItemsStatement.executeQuery();
                while(res.next()){
                    items.add(new ItemImpl(res.getInt("itemId"),res.getString("itemname"),
                            res.getString("description"), res.getFloat("price")));
                }
            }
            catch(SQLException e){
                e.printStackTrace();
                return items;
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
        return items;
    }
        public Item getItemByID(int id) throws RemoteException{
        Item item = null;
        ResultSet res = null;
        if(db.isConnected()){
            try{
                getItemDataStatement.setInt(1, id);
                res = getItemDataStatement.executeQuery();
                while(res.next()){
                    item = new ItemImpl(res.getInt("itemId"), res.getString("itemname"),
                            res.getString("description"), res.getFloat("price"));
                }
            }
            catch(SQLException e){
                e.printStackTrace();
                return item;
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
        return item;
    }
        public void newItem(String descr, String name, float price) throws RemoteException{
        if(db.isConnected()){
            try{
                newItemStatement.setString(1, descr);
                newItemStatement.setString(2, name);
                newItemStatement.setFloat(3, price);
                newItemStatement.executeUpdate();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    public ArrayList<ListedItem> getListedItems() throws RemoteException{
        ArrayList<ListedItem> listedItems = new ArrayList();
        ResultSet res = null;
        if(db.isConnected()){
            try{
                res = getListedItemsStatement.executeQuery();
                while(res.next()){
                    listedItems.add(new ListedItemImpl(getItemByID(res.getInt("itemId")),
                            res.getString("seller")));
                }
            }
            catch(SQLException e){
                e.printStackTrace();
                return listedItems;
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
        return listedItems;
    }
    public void newListedItem(String itemname,String descr,float price, String user) throws RemoteException{
        newItem(descr,itemname,price);
        ResultSet res = null;
        if(db.isConnected()){
            try{
                getItemIDStatement.setString(1, itemname);
                getItemIDStatement.setString(2, descr);
                getItemIDStatement.setFloat(3, price);
                res = getItemIDStatement.executeQuery();
                int itemid = -1;
                while(res.next()){
                    itemid = res.getInt("itemId");
                }
                newListedItemStatement.setInt(1, itemid);
                newListedItemStatement.setString(2, user);
                newListedItemStatement.executeUpdate();
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
    public void removeListedItem(String itemname,String descr,float price, String user) throws RemoteException{
        if(db.isConnected()){
            try{
                removeListedItemStatement.setString(1, itemname);
                removeListedItemStatement.setFloat(2, price);
                removeListedItemStatement.setString(3, descr);
                removeListedItemStatement.setString(4, user);
                removeListedItemStatement.executeUpdate();                
            }
            catch(SQLException e){
                e.printStackTrace();
            }
            try{
                removeItemStatement.setString(1, itemname);
                removeItemStatement.setString(2, descr);
                removeItemStatement.setFloat(3, price);
                removeItemStatement.executeUpdate();
            }
            catch(SQLException e2){
                System.out.println("Tried to delete a item that's referenced"
                        + "by a row in another table");
            }
        }
    }
    public void newSoldItem(String buyer, String seller, int itemId) throws RemoteException{
        if(db.isConnected()){
            try{
                newSoldItemStatement.setString(1, buyer);
                newSoldItemStatement.setString(2, seller);
                newSoldItemStatement.setInt(3, itemId);
                newSoldItemStatement.executeUpdate();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
public ArrayList<SoldItem> getSold() throws RemoteException{
        ResultSet res = null;
        ArrayList<SoldItem> sold= new ArrayList();
        if(db.isConnected()){
            try{
                res = getSoldStatement.executeQuery();
                while(res.next()){
                    int itemid = res.getInt("itemid");
                    sold.add(new SoldItemImpl(res.getString("seller"),
                            res.getString("buyer"),getItemByID(itemid)));
                }
            }
            catch(SQLException e){
                e.printStackTrace();
                return sold;
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
        return sold;
    }
 public ArrayList<Wish> getWishes() throws RemoteException{
        ResultSet res = null;
        ArrayList<Wish> wishes= new ArrayList();
        if(db.isConnected()){
            try{
                res = getWishesStatement.executeQuery();
                while(res.next()){
                    int itemid = res.getInt("itemid");
                    wishes.add(new WishImpl(getItemByID(itemid), res.getString("wisher")));
                }
            }
            catch(SQLException e){
                e.printStackTrace();
                return wishes;
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
        return wishes;
    }
    public void newWish(String itemname, float price, String user) throws RemoteException{
        String descr = "wish";
        newItem(descr,itemname,price);
        ResultSet res = null;
        if(db.isConnected()){
            try{
                getItemIDStatement.setString(1, itemname);
                getItemIDStatement.setString(2, descr);
                getItemIDStatement.setFloat(3, price);
                res = getItemIDStatement.executeQuery();
                int itemid = -1;
                while(res.next()){
                    itemid = res.getInt("itemId");
                }
                newWishStatement.setInt(1,itemid);
                newWishStatement.setString(2, user);
                newWishStatement.executeUpdate();
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
        public void removeWish(String itemname, float price, String user) throws RemoteException{
        if(db.isConnected()){
            try{
                removeWishStatement.setString(1, itemname);
                removeWishStatement.setFloat(2, price);
                removeWishStatement.setString(3, "wish");
                removeWishStatement.setString(4, user);
                removeWishStatement.executeUpdate();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
                        try{
                removeItemStatement.setString(1, itemname);
                removeItemStatement.setString(2, "wish");
                removeItemStatement.setFloat(3, price);
                removeItemStatement.executeUpdate();
            }
            catch(SQLException e2){
                System.out.println("Tried to delete a item that's referenced"
                        + "by a row in another table");
            }
        }
    } 
    public ArrayList<String> getUsers() throws RemoteException{
        ResultSet res = null;
        ArrayList<String> users = new ArrayList();
        if(db.isConnected()){
            try{
                res = getUsersStatement.executeQuery();
                while(res.next()){
                    users.add (res.getString("username"));
                }
            }
            catch(SQLException e){
                e.printStackTrace();
                return users;
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
        return users;
    }
    public void newUser(String user, String pw) throws RemoteException{
        if(db.isConnected()){
            try{
                newUserStatement.setString(1, user);
                newUserStatement.setString(2, pw);
                newUserStatement.executeUpdate();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }                      
    
    public String getUserPassword(String user) throws RemoteException{
        ResultSet res = null;
        String pw = null;
        if(db.isConnected()){
            try{
                getUserPasswordStatement.setString(1, user);
                res = getUserPasswordStatement.executeQuery();
                while(res.next()){
                    pw = res.getString("password");
                }
            }
            catch(SQLException e){
                e.printStackTrace();
                return pw;
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
        return pw;
    }
    
}

