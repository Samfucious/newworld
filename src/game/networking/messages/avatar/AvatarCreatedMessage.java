/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking.messages.avatar;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import game.application.Application;
import game.entities.Avatar;
import game.networking.BaseMessage;
import lombok.Getter;

/**
 *
 * @author gyrep
 */
@Serializable
@Getter
public class AvatarCreatedMessage extends BaseMessage {
    private final Vector3f position;
    private final Quaternion rotation;
    
    public AvatarCreatedMessage(int sourceId, int clientId, Vector3f position, Quaternion rotation) {
        super(sourceId, clientId);
        this.position = position;
        this.rotation = rotation;
    }

    @Override
    public void processMessage() {
        Application.getApplication().addAvatar(new Avatar(this.getClientId(), position, rotation));
    }
    
    @Override
    public BaseMessage serverCloneMessage() {
        return this;
    }
}
