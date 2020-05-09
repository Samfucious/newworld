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

import com.jme3.network.serializing.Serializable;
import game.application.Application;
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
public class AvatarDestroyedMessage extends BaseMessage implements ITargetClient {

    public AvatarDestroyedMessage(int sourceId, int clientId) {
        super(sourceId, clientId);
    }
    
    @Override
    public void processMessage() {
        Application.getApplication().removeAvatar(getClientId());
    }

    @Override
    public BaseMessage serverCloneMessage() {
        return null;
    }
}
