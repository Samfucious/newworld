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
public class AvatarPositionMessage extends BaseMessage {
    private final Vector3f position;
    private final Quaternion rotation;
    private final Vector3f direction;
    private final Vector3f left;
    private final Avatar.Movements[] movements;
    
    public AvatarPositionMessage(int sourceId, int clientId,
            Vector3f position, Quaternion rotation,
            Vector3f direction, Vector3f left,
            Avatar.Movements[] movements
            ) {
        super(sourceId, clientId);
        this.position = position;
        this.rotation = rotation;
        this.direction = direction;
        this.left = left;
        this.movements = movements;
    }

    @Override
    public void processMessage() {
        Avatar avatar = Application.getApplication().getAvatar(this.getClientId());
        if(avatar != null) {
            avatar.setLocalTranslation(position);
            avatar.setLocalRotation(rotation);
            avatar.setMovementVectors(direction, left);
            avatar.setMovements(movements);
        }
    }

    @Override
    public BaseMessage serverCloneMessage() {
        Avatar avatar = Application.getApplication().getAvatar(this.getClientId());
        if(avatar != null) {
            return new AvatarPositionMessage(
                    this.getSourceId(),
                    this.getClientId(),
                    avatar.getLocalTranslation(),
                    rotation,
                    direction,
                    left,
                    movements
            );
        }
        return null;
    }
}
