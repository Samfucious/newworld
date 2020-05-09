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
package game.application;

import com.jme3.audio.AudioNode;
import com.jme3.system.JmeContext;
import game.Configuration;
import game.networking.BaseMessage;
import game.networking.ITargetServer;
import game.networking.ServerNetworkManager;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class ServerApp extends BaseApp {
    ServerNetworkManager network;
    
    @Override
    public void run() {
        this.start(JmeContext.Type.Headless);
    }
    
    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        int port = Integer.parseInt(Configuration.getConfigurationValue(Configuration.Configurations.PORT, Configuration.DEFAULT_PORT));
        network = new ServerNetworkManager(port);
    }
    
    @Override
    public int getClientId() {
        return ServerNetworkManager.SERVER_ID;
    }

    @Override
    public void playAudio(AudioNode audioNode) {
        // Do nothing
    }
    
    @Override
    public void postMessage(BaseMessage message) {
        if(message instanceof ITargetServer) {
            super.postMessage(message);
        }
    }
    
    public void send(BaseMessage message) {
        network.send(message);
    }
    
    public void send(BaseMessage message, int clientId) {
        network.send(message, clientId);
    }
}
