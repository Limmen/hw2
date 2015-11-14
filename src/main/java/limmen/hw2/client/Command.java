/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hw2.client;

/**
 *
 * @author kim
 */
public class Command {
        private final String userName;
        private final float amount;
        private final CommandName command;
        
        Command(CommandName command, String userName, float amount) {
            this.command = command;
            this.userName = userName;
            this.amount = amount;
        }
        String getUserName() {
            return userName;
        }

        float getAmount() {
            return amount;
        }

        CommandName getCommandName() {
            return command;
        }
    }