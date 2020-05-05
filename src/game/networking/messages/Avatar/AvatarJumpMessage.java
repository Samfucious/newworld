/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking.messages.Avatar;

import com.jme3.network.serializing.Serializable;
import game.App.Application;
import game.networking.BaseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author gyrep
 */
@Serializable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AvatarJumpMessage extends BaseMessage {
    private int sourceId;
    private int clientId;
    
    @Override
    public void processMessage() {
        Application.getApplication().getAvatar(clientId).jump();
    }

    @Override
    public BaseMessage serverCloneMessage() {
        return this;
    }
}