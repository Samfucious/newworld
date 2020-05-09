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

import game.messages.PongMessage;
import game.messages.PingMessage;
import game.messages.ITargetServer;
import game.messages.BaseMessage;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.ConnectionListener;
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import game.application.Application;
import game.entities.Avatar;
import game.messages.avatar.AvatarCreatedMessage;
import game.messages.avatar.AvatarDestroyedMessage;
import game.messages.avatar.AvatarJumpMessage;
import game.messages.avatar.AvatarPositionMessage;
import game.messages.avatar.AvatarStrafeMessage;
import game.messages.avatar.AvatarWalkMessage;
import game.messages.avatar.LocalAvatarCreatedMessage;
import game.messages.avatar.SendAllAvatarsMessage;
import game.messages.object.ObjectStateMessage;
import game.messages.object.ObjectStateRequestMessage;
import game.messages.object.SendObjectsStateUpdatesMessage;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class ServerNetworkManager {
    public static final int SERVER_ID = Integer.MIN_VALUE;
    public static final long KEEPALIVE_THRESHOLD = 10000; // 10 seconds
    public static final long KEEPALIVE_FREQUENCY = 5000; // 5 seconds
    public static final String KEEPALIVE_ATTRIBUTE = "lastHeartbeat";
    
    private Server server = null;
    private final Timer keepAliveTimer = new Timer();
    
    public ServerNetworkManager(int port) {
        startServer(port);
        keepAliveTimer.schedule(new KeepAliveTimerTask(this), KEEPALIVE_FREQUENCY, KEEPALIVE_FREQUENCY);
    }
        
    private void startServer(int port) {
        try {
            Logger.getLogger(ClientConnectionManager.class.getName()).log(Level.INFO, String.format("Listening on port %d.", port));
            server = Network.createServer(port);
            server.addConnectionListener(new ServerSideConnectionListener());
            server.addMessageListener(new ServerSideMessageListener());
            initializeSerializables();
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(ClientConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void send(BaseMessage message) {
        server.broadcast(message);
    }
    
    public void send(BaseMessage message, int clientId) {
        HostedConnection connection = server.getConnection(clientId);
        if(null != connection) {
            connection.send(message);
        }
    }
    
    public void checkKeepAlives() {
        Collection<HostedConnection> connections = server.getConnections();
        long currentTimeMillis = System.currentTimeMillis();
        connections.forEach(client -> {
            long lastHeartbeat = (long) client.getAttribute(KEEPALIVE_ATTRIBUTE);
            if (currentTimeMillis - lastHeartbeat > KEEPALIVE_THRESHOLD)
            {
                client.close("stale heartbeat");
            }
        });
    }
    
    private void initializeSerializables() {
        Serializer.registerClass(AvatarCreatedMessage.class);
        Serializer.registerClass(AvatarJumpMessage.class);
        Serializer.registerClass(AvatarPositionMessage.class);
        Serializer.registerClass(AvatarStrafeMessage.class);
        Serializer.registerClass(AvatarWalkMessage.class);
        Serializer.registerClass(AvatarDestroyedMessage.class);
        Serializer.registerClass(PingMessage.class);
        Serializer.registerClass(PongMessage.class);
        Serializer.registerClass(ObjectStateMessage.class);
        Serializer.registerClass(ObjectStateRequestMessage.class);
    }
    
    private static class KeepAliveTimerTask extends TimerTask {
        ServerNetworkManager networkManager;
        
        public KeepAliveTimerTask(ServerNetworkManager networkManager) {
            this.networkManager = networkManager;
        }
        
        @Override
        public void run() {
            Application.getApplication().postMessage(new KeepAliveMessage(networkManager));
        }
    }
    
    private static class KeepAliveMessage extends BaseMessage implements ITargetServer {
        ServerNetworkManager networkManager;
        
        public KeepAliveMessage(ServerNetworkManager networkManager) {
            this.networkManager = networkManager;
        }
        
        @Override
        public void processMessage() {
            networkManager.checkKeepAlives();
        }

        @Override
        public BaseMessage serverCloneMessage() {
            return null;
        }
    }
    
    private static class ServerSideConnectionListener implements ConnectionListener {
        @Override
        public void connectionAdded(Server server, HostedConnection connection) {
            Logger.getLogger(ClientConnectionManager.class.getName()).log(Level.INFO, String.format("Client connected: %s", connection.getId()));
            sendAvatarCreatedMessage(server, connection.getId());
            sendObjectsStateUpdatesMessage(server, connection.getId());
            sendAllAvatarsMessage(server, connection.getId());
        }
        
        private void sendAvatarCreatedMessage(Server server, int clientId) {
            // TODO: Consider spawn position variabilities.
            Vector3f spawnPoint = Vector3f.UNIT_Y.mult(10.0f);
            
            Application.getApplication().postMessage(
                    new LocalAvatarCreatedMessage(new Avatar(clientId, spawnPoint, Quaternion.IDENTITY))
            );
            
            AvatarCreatedMessage message = new AvatarCreatedMessage(SERVER_ID, clientId, clientId, spawnPoint, Quaternion.IDENTITY);
            server.broadcast(message);
        }
        
        private void sendObjectsStateUpdatesMessage(Server server, int clientId) {
            SendObjectsStateUpdatesMessage message = new SendObjectsStateUpdatesMessage(SERVER_ID, clientId,
                    new LinkedList(Application.getApplication().getStatefulObjects()));
            Application.getApplication().postMessage(message);
        }
        
        private void sendAllAvatarsMessage(Server server, int clientId) {
            SendAllAvatarsMessage message = new SendAllAvatarsMessage(SERVER_ID, clientId,
                    new LinkedList(Application.getApplication().getAvatars()));
            Application.getApplication().postMessage(message);
        }

        @Override
        public void connectionRemoved(Server server, HostedConnection connection) {
            Logger.getLogger(ClientConnectionManager.class.getName()).log(Level.INFO,  String.format("Client disconnected: %s", connection.getId()));
            AvatarDestroyedMessage message = new AvatarDestroyedMessage(SERVER_ID, connection.getId());
            Application.getApplication().postMessage(message);
            server.broadcast(message);
        }
    }
    
    private class ServerSideMessageListener implements MessageListener<HostedConnection> {
        @Override
        public void messageReceived(HostedConnection source, Message message) {
            Logger.getLogger(ClientConnectionManager.class.getName()).log(Level.INFO,  String.format("Client message from %d", source.getId()));
            
            if(message instanceof BaseMessage) {
                source.setAttribute(KEEPALIVE_ATTRIBUTE, System.currentTimeMillis());
                
                BaseMessage baseMessage = (BaseMessage) message;
                BaseMessage clone = ((BaseMessage) baseMessage).serverCloneMessage();
                Application.getApplication().postMessage(clone);
                if(null != clone) {
                    server.broadcast(Filters.notEqualTo(source.getId()), clone);
                }
            }
        }
    }
}
