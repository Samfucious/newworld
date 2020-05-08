/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking.messages.chat;

import game.networking.BaseMessage;
import lombok.Getter;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
@Getter
public class ChatMessage extends BaseMessage{
    String message;
    
    public ChatMessage(int sourceId, int clientId, String message) {
        super(sourceId, clientId);
        this.message = message;
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
