/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking;

import com.jme3.network.AbstractMessage;
import lombok.Getter;

/**
 *
 * @author gyrep
 */
@Getter
public abstract class BaseMessage extends AbstractMessage {
    private final int sourceId;
    private final int clientId;
    
    public BaseMessage(int sourceId, int clientId) {
        this.sourceId = sourceId;
        this.clientId = clientId;
    }
        
    public abstract void processMessage();
    public abstract BaseMessage serverCloneMessage();
}
