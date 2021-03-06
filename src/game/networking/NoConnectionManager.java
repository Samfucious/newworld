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
package game.networking;

import game.messages.IMessenger;
import game.messages.BaseMessage;
import game.application.Application;
import game.messages.ITargetAny;
import java.util.Collection;

/**
 *
 * @author Sam Iredale "Samfucious" (gyrepin@gmail.com)
 */
public class NoConnectionManager implements IMessenger {

    @Override
    public int getClientId() {
        return 0;
    }

    @Override
    public void send(BaseMessage message) {        
        Application.getApplication().postMessage(new InternalMessageWrapper(message.getClientId(), message));
    }
    
    @Override
    public void send(Collection<BaseMessage> messages) {
        messages.forEach(message -> { send(message); });
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }
    
    private static class InternalMessageWrapper extends BaseMessage implements ITargetAny {
        
        BaseMessage message;
        
        public InternalMessageWrapper(int clientId, BaseMessage message) {
            super(clientId);
            this.message = message;
        }

        @Override
        public void processMessage() {
            if (null != message) {
                message.processMessage();
                Application.getApplication().postMessage(message.createResponse());
            }
        }

        @Override
        public BaseMessage createResponse() {
            return null != message ?
                message.createResponse() :
                null;
        }
    }
}
