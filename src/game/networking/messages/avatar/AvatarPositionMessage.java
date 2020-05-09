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
import game.networking.ITargetClient;
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
public class AvatarPositionMessage extends BaseMessage implements ITargetClient {
    private Vector3f position;
    private Quaternion rotation;
    private Vector3f direction;
    private Vector3f left;
    private Avatar.Movements[] movements;
    
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
        return null;
    }
}
