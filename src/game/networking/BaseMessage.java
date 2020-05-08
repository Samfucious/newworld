/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking;

import com.jme3.network.AbstractMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author gyrep
 */
@NoArgsConstructor
@Getter
@Setter
public abstract class BaseMessage extends AbstractMessage {
    private int sourceId;
    private int clientId;
    
    public BaseMessage(int sourceId, int clientId) {
        this.sourceId = sourceId;
        this.clientId = clientId;
    }
        
    public abstract void processMessage();
    
    /**
     * @return A copy of the message, with any server values, such as object positioning, replacing the original values. 
     */
    public abstract BaseMessage serverCloneMessage();
}
