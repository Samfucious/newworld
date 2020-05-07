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
public class AvatarStrafeMessage extends BaseMessage {
    private int sourceId;
    private int clientId;
    private Vector3f position;
    private Quaternion rotation;
    private boolean isLeft;
    private boolean startMovement;

    @Override
    public void processMessage() {
        Avatar avatar = Application.getApplication().getAvatar(clientId);
        avatar.setLocalTranslation(position);
        avatar.setLocalRotation(rotation);
        
        if(startMovement) {
            avatar.addMovement(isLeft ? Avatar.Movements.LEFT : Avatar.Movements.RIGHT);
        } else {
            avatar.removeMovement(isLeft ? Avatar.Movements.LEFT : Avatar.Movements.RIGHT);
        }
    }

    @Override
    public BaseMessage serverCloneMessage() {
        Avatar avatar = Application.getApplication().getAvatar(clientId);
        return new AvatarStrafeMessage(sourceId, clientId, avatar.getLocalTranslation(), rotation, isLeft, startMovement);
    }
}