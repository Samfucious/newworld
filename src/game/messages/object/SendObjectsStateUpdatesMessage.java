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
package game.messages.object;

import com.jme3.scene.Spatial;
import game.application.Application;
import game.application.ServerApp;
import game.entities.IRadianRotator;
import game.messages.BaseMessage;
import game.messages.ITargetAny;
import game.networking.ServerNetworkManager;
import java.util.Queue;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
@Getter
@Setter
public class SendObjectsStateUpdatesMessage extends BaseMessage implements ITargetAny {
    Queue<Spatial> spatials;
    
    public SendObjectsStateUpdatesMessage(int sourceId, int clientId, Queue<Spatial> spatials) {
        super(sourceId, clientId);
        this.spatials = spatials;
    }

    @Override
    public void processMessage() {
        if (spatials.isEmpty()) return;
        
        for (int i = 0; i < 10 && !spatials.isEmpty(); i++) {
            Spatial spatial = spatials.remove();
            BaseMessage message;
            
            if(spatial instanceof IRadianRotator) {
                message = new RadianRotatorStateMessage(ServerNetworkManager.SERVER_ID, getClientId(),
                        spatial.getName(), ((IRadianRotator)spatial).getRotation());
            } else {
                message = new ObjectStateMessage(ServerNetworkManager.SERVER_ID, getClientId(),
                        spatial.getName(), spatial.getLocalTranslation(), spatial.getLocalRotation());
            }
            
            ((ServerApp) Application.getApplication()).send(message, getClientId());
        }
        
        Application.getApplication().postMessage(this);
    }

    @Override
    public BaseMessage createResponse() {
        return null;
    }
}
