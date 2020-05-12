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

import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Spatial;
import game.application.Application;
import game.application.ServerApp;
import game.messages.BaseMessage;
import game.messages.ITargetServer;
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
public class ObjectStateRequestMessage extends BaseMessage implements ITargetServer {
    
    String name;

    @Override
    public void processMessage() {
        Spatial spatial = Application.getApplication().getStatefulObject(name);
        if(null != spatial) {
            ObjectStateMessage message = new ObjectStateMessage(ServerNetworkManager.SERVER_ID, 
                    name, spatial.getLocalTranslation(), spatial.getLocalRotation());
            ((ServerApp) Application.getApplication()).send(message, getSourceId());
        }
    }

    @Override
    public BaseMessage createResponse() {
        return null;
    }
}
