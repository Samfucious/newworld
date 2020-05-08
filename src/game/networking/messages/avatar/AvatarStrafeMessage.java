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
public class AvatarStrafeMessage extends BaseMessage implements ITargetAny {
    private Vector3f position;
    private Quaternion rotation;
    private boolean isLeft;
    private boolean startMovement;
    
    public AvatarStrafeMessage(int sourceId, int clientId, 
            Vector3f position, Quaternion rotation, boolean isLeft, boolean startMovement) {
        super(sourceId, clientId);
        this.position = position;
        this.rotation = rotation;
        this.isLeft = isLeft;
        this.startMovement = startMovement;
    }

    @Override
    public void processMessage() {
        Avatar avatar = Application.getApplication().getAvatar(this.getClientId());
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
        Avatar avatar = Application.getApplication().getAvatar(this.getClientId());
        return new AvatarStrafeMessage(this.getSourceId(), this.getClientId(), avatar.getLocalTranslation(), rotation, isLeft, startMovement);
    }
}