/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking.messages.avatar;

import com.jme3.network.serializing.Serializable;
import game.application.Application;
import game.networking.BaseMessage;
import game.networking.ITargetAny;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
@Serializable
@NoArgsConstructor
@Getter
@Setter
public class AvatarJumpMessage extends BaseMessage implements ITargetAny {

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