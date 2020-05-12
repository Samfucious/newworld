/*
 * Copyright (C) 2020 Sam Iredale "Samfucious" (gyrepin@gmail.com)
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
package game.messages.avatar.actions;

import game.entities.AvatarActionsState;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import game.application.Application;
import game.entities.Avatar;
import game.messages.BaseMessage;
import game.messages.ITargetClient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Sam Iredale "Samfucious" (gyrepin@gmail.com)
 */
@Serializable
@NoArgsConstructor
@Getter
@Setter
public class IsMovingUpdatedMessage extends BaseMessage implements ITargetClient {
    
    Vector3f position;
    Vector3f direction;
    boolean isMoving;

    IsMovingUpdatedMessage(int sourceId, int clientId, Vector3f position, Vector3f direction, boolean isMoving) {
        super(sourceId, clientId);
        this.position = position;
        this.isMoving = isMoving;
        this.direction = direction;
    }

    @Override
    public void processMessage() {
        Avatar avatar = Application.getApplication().getAvatar(getClientId());
        avatar.setLocalTranslation(position);
        
        AvatarActionsState actionsState = avatar.getServerActionsState();
        actionsState.setMoving(isMoving);
        actionsState.setMovementDirection(isMoving ? direction : Vector3f.ZERO);
    }

    @Override
    public BaseMessage createResponse() {
        return null;
    }
}
