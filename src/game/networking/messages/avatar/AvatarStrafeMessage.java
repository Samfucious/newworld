/*
 * Copyright (C) 2020 samfucious
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
        if (null == avatar) return null;
        return new AvatarStrafeMessage(this.getSourceId(), this.getClientId(), avatar.getLocalTranslation(), rotation, isLeft, startMovement);
    }
}