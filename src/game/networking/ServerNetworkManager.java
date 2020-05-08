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
import game.networking.messages.avatar.AvatarJumpMessage;
import game.networking.messages.avatar.AvatarPositionMessage;
import game.networking.messages.avatar.AvatarStrafeMessage;
import game.networking.messages.avatar.AvatarWalkMessage;
import java.io.IOException;
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
    
    private void initializeSerializables() {
        Serializer.registerClass(AvatarCreatedMessage.class);
        Serializer.registerClass(AvatarJumpMessage.class);
        Serializer.registerClass(AvatarPositionMessage.class);
        Serializer.registerClass(AvatarStrafeMessage.class);
        Serializer.registerClass(AvatarWalkMessage.class);
    }
    
    private static class ServerSideConnectionListener implements ConnectionListener {
        @Override
        public void connectionAdded(Server server, HostedConnection connection) {
            Logger.getLogger(ClientConnectionManager.class.getName()).log(Level.INFO, String.format("Client connected: %s", connection.getId()));
            AvatarCreatedMessage message = new AvatarCreatedMessage(
                    SERVER_ID,
                    connection.getId(),
                    Vector3f.UNIT_Y.mult(10.0f),
                    Quaternion.IDENTITY
            );
            Application.getApplication().postMessage(message);
            server.broadcast(message);
        }

        @Override
        public void connectionRemoved(Server server, HostedConnection connection) {
            Logger.getLogger(ClientConnectionManager.class.getName()).log(Level.INFO,  String.format("Client disconnected: %s", connection.getId()));
        }
    }
    
    private class ServerSideMessageListener implements MessageListener<HostedConnection> {
        @Override
        public void messageReceived(HostedConnection source, Message message) {
            if(message instanceof BaseMessage) {
                BaseMessage baseMessage = (BaseMessage) message;
                Application.getApplication().postMessage((BaseMessage) baseMessage);
                BaseMessage clone = ((BaseMessage) baseMessage).serverCloneMessage();
                if(null != clone) {
                    server.broadcast(Filters.notEqualTo(source.getId()), clone);
                }
            }
        }
    }
}
