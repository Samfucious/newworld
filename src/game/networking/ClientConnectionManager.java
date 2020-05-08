/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking;

import game.application.Application;
import com.jme3.network.Client;
import com.jme3.network.ClientStateListener;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gyrep
 */
public class ClientConnectionManager implements IMessenger {
    
    Client client;
    boolean roundTripMessages;
    
    public int getClientId() {
        return client.getId();
    }
    
    public ClientConnectionManager(String address, int port) {
        this(address, port, true);
    }
    
    public ClientConnectionManager(String address, int port, boolean roundTripMessages) {
        this.roundTripMessages = roundTripMessages;
        connectToServer(address, port);
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
                
                /* 
                    Post if we're making all messages go round trip to the server and back before processing.
                    Otherwise, post the message only if it's from a remote source.
                */
                if(roundTripMessages || client.getId() != baseMessage.getSourceId()) {
                    Application.getApplication().postMessage(baseMessage);
                }
            }
        }
    }
    
    public void send(BaseMessage message) {
        client.send(message);
        if(!roundTripMessages) {
            Application.getApplication().postMessage(message);
        }
    }
}
