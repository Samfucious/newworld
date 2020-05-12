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
package game.messages;

import com.jme3.network.serializing.Serializable;
import game.application.Application;
import game.application.ServerApp;
import game.networking.ServerNetworkManager;
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
public class PingMessage extends BaseMessage implements ITargetServer {
    long mark;
    
    public PingMessage(int sourceId, int clientId, long mark) {
        super(sourceId, clientId);
        this.mark = mark;
    }
    
    @Override
    public void processMessage() {
        PongMessage pongMessage = new PongMessage(ServerNetworkManager.SERVER_ID, getClientId(), getMark());
        ((ServerApp) Application.getApplication()).send(pongMessage, getClientId());
    }

    @Override
    public BaseMessage createResponse() {
        return null;
    }
}
