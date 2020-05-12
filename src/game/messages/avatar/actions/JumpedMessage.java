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

import game.application.Application;
import game.entities.Avatar;
import game.messages.BaseMessage;
import game.messages.ITargetClient;

/**
 *
 * @author Sam Iredale "Samfucious" (gyrepin@gmail.com)
 */
public class JumpedMessage extends BaseMessage implements ITargetClient {

    long timestamp;
    
    public JumpedMessage(int clientId, long timestamp) {
        super(clientId, clientId);
        this.timestamp = timestamp;
    }

    @Override
    public void processMessage() {
        Avatar avatar = Application.getApplication().getAvatar(getClientId());
        avatar.getServerActionsState().setLastJump(timestamp);
        avatar.jump();
    }

    @Override
    public BaseMessage createResponse() {
        return null;
    }
}
