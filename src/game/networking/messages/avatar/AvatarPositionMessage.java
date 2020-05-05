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
public class AvatarPositionMessage extends BaseMessage {
    private int sourceId;
    private int clientId;
    private Vector3f position;
    private Quaternion rotation;
    private Vector3f direction;
    private Vector3f left;
    private Avatar.Movements[] movements;

    @Override
    public void processMessage() {
        Avatar avatar = Application.getApplication().getAvatar(clientId);
        if(avatar != null) {
            avatar.setLocalTranslation(position);
            avatar.setLocalRotation(rotation);
            avatar.setMovementVectors(direction, left);
            avatar.setMovements(movements);
        }
    }

    @Override
    public BaseMessage serverCloneMessage() {
        Avatar avatar = Application.getApplication().getAvatar(clientId);
        if(avatar != null) {
            return new AvatarPositionMessage(
                    sourceId,
                    clientId,
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
