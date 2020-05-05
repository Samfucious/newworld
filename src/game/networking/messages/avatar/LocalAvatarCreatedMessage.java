/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking.messages.avatar;

import game.application.Application;
import game.entities.Avatar;
import game.networking.BaseMessage;

/**
 *
 * @author gyrep
 */
public class LocalAvatarCreatedMessage extends BaseMessage {
    Avatar avatar;
    
    public LocalAvatarCreatedMessage(Avatar avatar) {
        this.avatar = avatar;
    }

    @Override
    public void processMessage() {
        Application.getApplication().addAvatar(avatar);
    }

    @Override
    public BaseMessage serverCloneMessage() {
        throw new UnsupportedOperationException("Not supported on this message type.");
    }
}
