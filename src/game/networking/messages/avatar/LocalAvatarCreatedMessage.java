/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking.messages.avatar;

import game.application.Application;
import game.entities.Avatar;
import game.networking.BaseMessage;
import game.networking.ITargetClient;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class LocalAvatarCreatedMessage extends BaseMessage implements ITargetClient {
    Avatar avatar;
    
    public LocalAvatarCreatedMessage(Avatar avatar) {
        super(0, 0);
        this.avatar = avatar;
    }

    @Override
    public void processMessage() {
        Application.getApplication().addAvatar(avatar);
    }

    @Override
    public BaseMessage serverCloneMessage() {
        return null;
    }
}
