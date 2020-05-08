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
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author gyrep
 */
@Serializable
@NoArgsConstructor
@Getter
@Setter
public class AvatarWalkMessage extends BaseMessage {
    private Vector3f position;
    private Quaternion rotation;
    private boolean isForward;
    private boolean startMovement;

    public AvatarWalkMessage(int sourceId, int clientId,
            Vector3f position, Quaternion rotation,
            boolean isForward, boolean startMovement) {
        super(sourceId, clientId);
        this.position = position;
        this.rotation = rotation;
        this.isForward = isForward;
        this.startMovement = startMovement;
    }
    
    @Override
    public void processMessage() {
        Avatar avatar = Application.getApplication().getAvatar(this.getClientId());
        avatar.setLocalTranslation(position);
        avatar.setLocalRotation(rotation);
        
        if(startMovement) {
            avatar.addMovement(isForward ? Avatar.Movements.FORWARD : Avatar.Movements.BACKWARD);
        } else {
            avatar.removeMovement(isForward ? Avatar.Movements.FORWARD : Avatar.Movements.BACKWARD);
        }
    }

    @Override
    public BaseMessage serverCloneMessage() {
        Avatar avatar = Application.getApplication().getAvatar(this.getClientId());
        return new AvatarWalkMessage(this.getSourceId(), this.getClientId(), avatar.getLocalTranslation(), avatar.getLocalRotation(), isForward, startMovement);
    }
}