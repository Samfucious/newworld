/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking;

import game.App.Application;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.ClientStateListener;
import com.jme3.network.ConnectionListener;
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import game.networking.messages.Avatar.AvatarCreatedMessage;
import game.networking.messages.Avatar.AvatarJumpMessage;
import game.networking.messages.Avatar.AvatarPositionMessage;
import game.networking.messages.Avatar.AvatarStrafeMessage;
import game.networking.messages.Avatar.AvatarWalkMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gyrep
 */
public class Connection {    
    public static final int SERVER_ID = Integer.MIN_VALUE;
    
    static Server server = null;
    static Client client = null;
    
    public static Server getServer() {
        return server;
    }
    
    public static Client getClient() {
        return client;
    }
    
    public static boolean getNetworkInitiaized() {
        return null != server || null != client;
    }
    
    public static Server startServer(int port) {
        if(!getNetworkInitiaized()) {
            try {
                Logger.getLogger(Connection.class.getName()).log(Level.INFO, String.format("Listening on port %d.", port));
                server = Network.createServer(port);
                server.addConnectionListener(new ServerSideConnectionListener());
                initializeSerializables();
                server.start();
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return server;
    }
    
    public static Client connectToServer(String address, int port) {
        if(!getNetworkInitiaized()) {
            try {
                Logger.getLogger(Connection.class.getName()).log(Level.INFO, String.format("Connecting to on port %s:%d.", address, port));
                Client connection = Network.connectToServer(address, port);
                connection.addClientStateListener(new ClientSideStateListener());
                connection.addMessageListener(new ClientSideMessageListener());
                connection.start();
                client = connection;
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(1);
            }
        }
        return client;
    }
    
    private static void initializeSerializables() {
        Serializer.registerClass(AvatarCreatedMessage.class);
        Serializer.registerClass(AvatarJumpMessage.class);
        Serializer.registerClass(AvatarPositionMessage.class);
        Serializer.registerClass(AvatarStrafeMessage.class);
        Serializer.registerClass(AvatarWalkMessage.class);
    }
    
    public static class ServerSideConnectionListener implements ConnectionListener {
        @Override
        public void connectionAdded(Server server, HostedConnection conn) {
            Logger.getLogger(Connection.class.getName()).log(Level.INFO, String.format("Client connected: %s", conn.getId()));
            AvatarCreatedMessage message = new AvatarCreatedMessage(
                    SERVER_ID,
                    conn.getId(),
                    Vector3f.UNIT_Y.mult(10.0f),
                    Quaternion.IDENTITY
            );
            Application.getApplication().postMessage(message);
            Connection.server.broadcast(message);
        }

        @Override
        public void connectionRemoved(Server server, HostedConnection conn) {
            Logger.getLogger(Connection.class.getName()).log(Level.INFO,  String.format("Client disconnected: %s", conn.getId()));
        }
    }
    
    public static class ServerSideMessageListener implements MessageListener<HostedConnection> {
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
    
    public static class ClientSideStateListener implements ClientStateListener {
        @Override
        public void clientConnected(Client c) {
            Logger.getLogger(Connection.class.getName()).log(Level.INFO, String.format("Connected with id: %s", c.getId()));
        }

        @Override
        public void clientDisconnected(Client c, DisconnectInfo info) {
            Logger.getLogger(Connection.class.getName()).log(Level.INFO, String.format("Disconnected from server."));
        }
    }
    
    public static class ClientSideMessageListener implements MessageListener<Client> {
        @Override
        public void messageReceived(Client source, Message message) {
            if (message instanceof BaseMessage) {
                BaseMessage baseMessage = (BaseMessage) message;
                Application.getApplication().postMessage(baseMessage);
            }
        }
    }
}
