/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking.messages.chat;

import game.networking.BaseMessage;

/**
 *
 * @author gyrep
 */
public class NameChangeChatMessage extends BaseMessage {
    String newName;
    
    public NameChangeChatMessage(int sourceId, int clientId, String newName) {
        super(sourceId, clientId);
        this.newName = newName;
    }

    @Override
    public void processMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BaseMessage serverCloneMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}