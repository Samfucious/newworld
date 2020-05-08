/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking;

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
import game.networking.messages.avatar.AvatarCreatedMessage;
import game.networking.messages.avatar.AvatarDestroyedMessage;
import game.networking.messages.avatar.AvatarJumpMessage;
import game.networking.messages.avatar.AvatarPositionMessage;
import game.networking.messages.avatar.AvatarStrafeMessage;
import game.networking.messages.avatar.AvatarWalkMessage;
import game.networking.messages.object.SendObjectsStateUpdatesMessage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samfucious
 */
public class ServerNetworkManager {
    public static final int SERVER_ID = Integer.MIN_VALUE;
    
    private Server server = null;
    
    public ServerNetworkManager(int port) {
        startServer(port);
    }
        
    private void startServer(int port) {
        try {
            Logger.getLogger(ClientConnectionManager.class.getName()).log(Level.INFO, String.format("Listening on port %d.", port));
            server = Network.createServer(port);
            server.addConnectionListener(new ServerSideConnectionListener());
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
        connection.send(message);
    }
    
    private void initializeSerializables() {
        Serializer.registerClass(AvatarCreatedMessage.class);
        Serializer.registerClass(AvatarJumpMessage.class);
        Serializer.registerClass(AvatarPositionMessage.class);
        Serializer.registerClass(AvatarStrafeMessage.class);
        Serializer.registerClass(AvatarWalkMessage.class);
        Serializer.registerClass(AvatarDestroyedMessage.class);
    }
    
    private static class ServerSideConnectionListener implements ConnectionListener {
        @Override
        public void connectionAdded(Server server, HostedConnection connection) {
            Logger.getLogger(ClientConnectionManager.class.getName()).log(Level.INFO, String.format("Client connected: %s", connection.getId()));
            
            sendAvatarCreatedMessage(server, connection.getId());
            sendObjectsStateUpdatesMessage(server, connection.getId());
        }
        
        private void sendAvatarCreatedMessage(Server server, int clientId) {
            AvatarCreatedMessage message = new AvatarCreatedMessage(
                    SERVER_ID,
                    clientId,
                    Vector3f.UNIT_Y.mult(10.0f),
                    Quaternion.IDENTITY
            );
            Application.getApplication().postMessage(message);
            server.broadcast(message);
        }
        
        private void sendObjectsStateUpdatesMessage(Server server, int clientId) {
            SendObjectsStateUpdatesMessage message = new SendObjectsStateUpdatesMessage(SERVER_ID, clientId,
                    new LinkedList(Application.getApplication().getStatefulObjects()));
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
            if(message instanceof BaseMessage) {
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
