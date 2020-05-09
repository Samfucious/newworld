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
package game.networking;

import com.jme3.network.serializing.Serializable;
import java.util.LinkedList;
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
public class PongMessage extends BaseMessage implements ITargetClient {
    private static final int MAX_DATAPOINTS = 20;
    private static final LinkedList<PongMessage> pongs = new LinkedList();
    private static long averageLag = 0;
    
    long mark;
    long lag = 0;

    PongMessage(int sourceId, int clientId, long mark) {
        super(sourceId, clientId);        
        this.mark = mark;
    }
    
    @Override
    public void processMessage() {
        long now = System.currentTimeMillis();
        setLag(now - mark);
        pongs.add(this);
        
        while(pongs.size() > MAX_DATAPOINTS) {
            pongs.removeLast();
        }
        
        long totalRoundTrip = 0;
        for(PongMessage pong : pongs) {
            totalRoundTrip += pong.getLag();
        }
        
        if (pongs.size() > 0) {
            averageLag = totalRoundTrip / pongs.size();
        }
    }

    @Override
    public BaseMessage serverCloneMessage() {
        return null;
    }
    
    public static long getAverageLag() {
        return averageLag;
    }
}
