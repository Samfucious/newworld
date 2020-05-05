/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.App;

import com.jme3.system.JmeContext;
import game.Configuration;
import game.networking.Connection;

/**
 *
 * @author gyrep
 */
public class ServerApp extends BaseApp {
    private static final String DEFAULT_PORT = "6143";
    
    @Override
    public void run() {
        this.start(JmeContext.Type.Headless);
    }
    
    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        int port = Integer.parseInt(Configuration.getConfigurationValue(Configuration.Configurations.PORT, DEFAULT_PORT));
        Connection.startServer(port);
    }
    
    @Override
    public int getClientId() {
        return Connection.SERVER_ID;
    }
}
