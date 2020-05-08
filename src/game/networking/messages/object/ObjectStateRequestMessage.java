/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking.messages.object;

import com.jme3.scene.Spatial;
import game.application.Application;
import game.application.ServerApp;
import game.networking.BaseMessage;
import game.networking.ITargetServer;
import game.networking.ServerNetworkManager;

/**
 *
 * @author samfucious
 */
public class ObjectStateRequestMessage extends BaseMessage implements ITargetServer {
    
    String name;

    @Override
    public void processMessage() {
        Spatial spatial = Application.getApplication().getStatefulObject(name);
        if(null != spatial) {
            ObjectStateMessage message = new ObjectStateMessage(ServerNetworkManager.SERVER_ID, ServerNetworkManager.SERVER_ID, 
                    name, spatial.getLocalTranslation(), spatial.getLocalRotation());
            ((ServerApp) Application.getApplication()).send(message, getSourceId());
        }
    }

    @Override
    public BaseMessage serverCloneMessage() {
        return null;
    }
}
