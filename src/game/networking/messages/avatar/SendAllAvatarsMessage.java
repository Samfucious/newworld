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

import game.application.Application;
import game.application.ServerApp;
import game.entities.Avatar;
import game.networking.BaseMessage;
import game.networking.ITargetServer;
import game.networking.ServerNetworkManager;
import java.util.Queue;

/**
 *
 * @author samfucious
 */
public class SendAllAvatarsMessage extends BaseMessage implements ITargetServer {
    Queue<Avatar> avatars;
    
    public SendAllAvatarsMessage(int sourceId, int clientId, Queue<Avatar> avatars) {
        super(sourceId, clientId);
        this.avatars = avatars;
    }

    @Override
    public void processMessage() {
        if (avatars.isEmpty()) return;
        
        for (int i = 0; i < 10 && !avatars.isEmpty(); i++) {
            Avatar avatar = avatars.remove();
            if (avatar.getAvatarId() != getClientId()) {
                AvatarCreatedMessage message = new AvatarCreatedMessage(ServerNetworkManager.SERVER_ID, getClientId(),
                        avatar.getAvatarId(), avatar.getLocalTranslation(), avatar.getLocalRotation());
                ((ServerApp) Application.getApplication()).send(message, getClientId());
            }
        }
        Application.getApplication().postMessage(this);
    }

    @Override
    public BaseMessage serverCloneMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
