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
package game.networking.messages.object;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Spatial;
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
public class ObjectStateMessage extends BaseMessage implements ITargetClient {
    String name;
    Vector3f position;
    Quaternion rotation;
    
    public ObjectStateMessage(int sourceId, int clientId, String name, Vector3f position, Quaternion rotation) {
        super(sourceId, clientId);
        this.name = name;
        this.position = position;
        this.rotation = rotation;
    }

    @Override
    public void processMessage() {
        Spatial spatial = Application.getApplication().getStatefulObject(name);
        if(null != spatial) {
            spatial.setLocalTranslation(position);
            spatial.setLocalRotation(rotation);
        }
    }

    @Override
    public BaseMessage serverCloneMessage() {
        return null;
    }
}
