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

/**
 *
 * @author gyrep
 */
@Serializable
@Getter
public class AvatarJumpMessage extends BaseMessage {

    public AvatarJumpMessage(int sourceId, int clientId) {
        super(sourceId, clientId);
    }
    
    @Override
    public void processMessage() {
        Application.getApplication().getAvatar(this.getClientId()).jump();
    }

    @Override
    public BaseMessage serverCloneMessage() {
        return this;
    }
}