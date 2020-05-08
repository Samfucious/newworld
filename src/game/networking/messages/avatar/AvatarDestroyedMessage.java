/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking.messages.avatar;

import com.jme3.network.serializing.Serializable;
import game.application.Application;
import game.networking.BaseMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author gyrep
 */
@Serializable
@NoArgsConstructor
@Getter
@Setter
public class AvatarDestroyedMessage extends BaseMessage {

    public AvatarDestroyedMessage(int sourceId, int clientId) {
        super(sourceId, clientId);
    }
    
    @Override
    public void processMessage() {
        Application.getApplication().removeAvatar(getClientId());
    }

    @Override
    public BaseMessage serverCloneMessage() {
        throw new UnsupportedOperationException("This message originates from the server only.");
    }
}
