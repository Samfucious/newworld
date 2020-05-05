/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking.messages.Avatar;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import game.App.Application;
import game.entities.Avatar;
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
public class AvatarCreatedMessage extends BaseMessage {
    private int sourceId;
    private int clientId;
    private Vector3f position;
    private Quaternion rotation;

    @Override
    public void processMessage() {
        Application.getApplication().addAvatar(new Avatar(clientId, position, rotation));
    }
    
    @Override
    public BaseMessage serverCloneMessage() {
        return this;
    }
}
