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
package game.messages.avatar.actions;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import game.application.Application;
import game.entities.Avatar;
import game.messages.BaseMessage;
import game.messages.ITargetServer;
import game.networking.ServerNetworkManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author samfucious
 */
@Serializable
@NoArgsConstructor
@Getter
@Setter
public class UpdateMovementDirectionMessage extends BaseMessage implements ITargetServer {
    Vector3f direction;

    public UpdateMovementDirectionMessage(int sourceId, int clientId, Vector3f direction) {
        super(sourceId, clientId);
        this.direction = direction;
    }

    @Override
    public void processMessage() {
        Avatar avatar = Application.getApplication().getAvatar(getClientId());
        avatar.getClientActionsState().setMovementDirection(direction);
    }

    @Override
    public BaseMessage createResponse() {
        Avatar avatar = Application.getApplication().getAvatar(getClientId());
        Vector3f position = avatar.getLocalTranslation();
        return new MovementDirectionUpdatedMessage(ServerNetworkManager.SERVER_ID, getClientId(), position, direction);
    }
}
