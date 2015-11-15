/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.client.util;

/**
 *
 * @author kim
 */
public class BankCommand {
    private final float amount;
    private final BankCommandName command;
    
    public BankCommand(BankCommandName command, float amount) {
        this.command = command;
        this.amount = amount;
    }
    public BankCommand(BankCommandName command) {
        this.command = command;
        amount = 0;
    }
    public float getAmount() {
        return amount;
    }
    
    public BankCommandName getCommandName() {
        return command;
    }
}