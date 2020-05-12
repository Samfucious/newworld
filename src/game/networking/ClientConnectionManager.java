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

import game.messages.PingMessage;
import game.messages.IMessenger;
import game.messages.BaseMessage;
import game.application.Application;
import com.jme3.network.Client;
import com.jme3.network.ClientStateListener;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import java.io.IOException;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sam Iredale "Samfucious" (gyrepin@gmail.com)
 */
public class ClientConnectionManager implements IMessenger {
    private static final int PING_FREQUENCY = 5000; // 5 seconds (1/2 of ServerNetworkManager.KEEPALIVE_THRESHOLD)
    
    Client client;
    private final Timer pingTimer = new Timer();
    String address;
    int port;
    
    @Override
    public int getClientId() {
        return client.getId();
    }
    
    public ClientConnectionManager(String address, int port) {
        this.address = address;
        this.port = port;
    }
    
    private void connectToServer(String address, int port) {
        try {
            Logger.getLogger(ClientConnectionManager.class.getName()).log(Level.INFO, String.format("Connecting to on port %s:%d.", address, port));
            Client connection = Network.connectToServer(address, port);
            connection.addClientStateListener(new ClientSideStateListener());
            connection.addMessageListener(new ClientSideMessageListener());
            connection.start();
            client = connection;
        } catch (IOException ex) {
            Logger.getLogger(ClientConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    @Override
    public void start() {
        connectToServer(address, port);
        pingTimer.schedule(new PingTask(client), PING_FREQUENCY, PING_FREQUENCY);
    }

    @Override
    public void stop() {
        pingTimer.cancel();
    }
    
    private static class PingTask extends TimerTask {
        Client client;
        
        public PingTask(Client client) {
            this.client = client;
        }
        
        @Override
        public void run() {
            PingMessage message = new PingMessage(client.getId(), client.getId(), System.currentTimeMillis());
            client.send(message);
        }
    }
    
    private class ClientSideStateListener implements ClientStateListener {
        @Override
        public void clientConnected(Client c) {
            Logger.getLogger(ClientConnectionManager.class.getName()).log(Level.INFO, String.format("Connected with id: %s", c.getId()));
        }

        @Override
        public void clientDisconnected(Client c, DisconnectInfo info) {
            Logger.getLogger(ClientConnectionManager.class.getName()).log(Level.INFO, String.format("Disconnected from server."));
        }
    }
    
    private class ClientSideMessageListener implements MessageListener<Client> {
        @Override
        public void messageReceived(Client source, Message message) {
            if (message instanceof BaseMessage) {
                BaseMessage baseMessage = (BaseMessage) message;
                if (baseMessage.hasPing()) {
                    long ping = baseMessage.getPing();
                    // TODO: Add post(new AddPingValueMessage(ping));
                }
                Application.getApplication().postMessage(baseMessage);
            }
        }
    }
    
    @Override
    public void send(BaseMessage message) {
        message.setTimestamp(System.currentTimeMillis());
        client.send(message);
    }
    
    @Override
    public void send(Collection<BaseMessage> messages) {
        messages.forEach(message -> { send(message); });
    }
}
