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

import com.jme3.network.serializing.Serializable;
import game.application.Application;
import game.entities.Avatar;
import game.entities.AvatarActionsState;
import game.messages.BaseMessage;
import game.messages.ITargetServer;
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
public class JumpMessage extends BaseMessage implements ITargetServer {
    
    long timestamp;
    
    public JumpMessage(int clientId, long timestamp) {
        super(clientId);
        this.timestamp = timestamp;
    }
    
    @Override
    public void processMessage() {
        if (getSourceId() == getClientId()) {
            Avatar avatar = Application.getApplication().getAvatar(this.getClientId());
            AvatarActionsState clientState = avatar.getClientActionsState();
            AvatarActionsState serverState = avatar.getServerActionsState();
            
            if (serverState.getLastJump() != clientState.getLastJump()) {
                serverState.setLastJump(clientState.getLastJump());
                avatar.jump();
            }
        }
    }

    @Override
    public BaseMessage createResponse() {
        return new JumpedMessage(getClientId(), timestamp);
    }
}