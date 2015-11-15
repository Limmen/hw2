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
public class Command {
        private final float amount;
        private final CommandName command;
        
        public Command(CommandName command, float amount) {
            this.command = command;
            this.amount = amount;
        }
        public Command(CommandName command) {
            this.command = command;
            amount = 0;
        }
        public float getAmount() {
            return amount;
        }

        public CommandName getCommandName() {
            return command;
        }
    }