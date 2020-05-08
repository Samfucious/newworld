/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.application;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class Application {
    private static BaseApp application;
    
    public static BaseApp getApplication() {
        return Application.application;
    }
    
    public static BaseApp initApplication(BaseApp application) {
        if(null == Application.application) {
            Application.application = application;
        }
        return Application.application;
    }
    
    public static AssetManager getAssetManager() {
        return application.getAssetManager();
    }
    
    public static Node getRootNode() {
        return application.getRootNode();
    }
}
