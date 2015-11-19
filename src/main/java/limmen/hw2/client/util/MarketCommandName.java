/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.client.util;

/**
 * The communication-protocol with the marketplace
 * @author kim
 */
public enum MarketCommandName {
    buy,
    sell,
    wish,
    register,
    deRegister,
    listItems,
    getWishes,
    getForSale,
    removeWish,
    removeSell,
    getBought,
    getSold;
}
