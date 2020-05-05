/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.App;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import game.entities.Avatar;
import game.networking.messages.Avatar.LocalAvatarCreatedMessage;

/**
 *
 * @author gyrep
 */
public class LocalApp extends ConnectedApp {
    @Override
    public void run() {
        this.start();
    }
    
    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        initLocalAvatar();
    }
    
    private void initLocalAvatar() {
        postMessage(new LocalAvatarCreatedMessage(new Avatar(0, Vector3f.UNIT_Y.mult(10.0f), Quaternion.IDENTITY)));
    }
    
    @Override
    public int getClientId() {
        return 0;
    }
    
    @Override
    protected Avatar getLocalAvatar() {
        return getAvatar(0);
    }
}
