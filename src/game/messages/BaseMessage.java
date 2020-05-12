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

import com.jme3.network.AbstractMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
@NoArgsConstructor
@Getter
@Setter
public abstract class BaseMessage extends AbstractMessage {
    private int sourceId;
    private int clientId;
    private long timestamp = -1; // only measure ping if this is positive.
    
    public BaseMessage(int sourceId, int clientId) {
        this.sourceId = sourceId;
        this.clientId = clientId;
    }
        
    public abstract void processMessage();
    
    /**
     * @return A copy of the message, with any server values, such as object positioning, replacing the original values. 
     */
    public abstract BaseMessage createResponse();
    
    public boolean hasPing() {
        return timestamp > 0;
    }
    
    public long getPing() {
        return System.currentTimeMillis() - timestamp;
    }
}
