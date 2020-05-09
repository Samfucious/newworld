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
import game.application.Application;
import game.entities.IRadianRotator;
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
public class RadianRotatorStateMessage extends BaseMessage implements ITargetClient {
    String name;
    float rotation;
    
    public RadianRotatorStateMessage(int sourceId, int clientId, String name, float rotation) {
        this.name = name;
        this.rotation = rotation;
    }

    @Override
    public void processMessage() {
        IRadianRotator radianRotator = (IRadianRotator) Application.getApplication().getStatefulObject(name);
        if(null != radianRotator) {
            radianRotator.setRotation(rotation);
        }
    }

    @Override
    public BaseMessage serverCloneMessage() {
        return null;
    }
}
