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
import java.util.Queue;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author samfucious
 */
@Getter
@Setter
public class SendObjectsStateUpdatesMessage extends BaseMessage implements ITargetServer {
    Queue<Spatial> spatials;
    
    public SendObjectsStateUpdatesMessage(int sourceId, int clientId, Queue<Spatial> spatials) {
        super(sourceId, clientId);
        this.spatials = spatials;
    }

    @Override
    public void processMessage() {
        if (spatials.isEmpty()) return;
        
        for (int i = 0; i < 10 && !spatials.isEmpty(); i++) {
            Spatial spatial = spatials.remove();
            ObjectStateMessage message = new ObjectStateMessage(ServerNetworkManager.SERVER_ID, getClientId(),
                    spatial.getName(), spatial.getLocalTranslation(), spatial.getLocalRotation());
            ((ServerApp) Application.getApplication()).send(message, getClientId());
        }
        
        Application.getApplication().postMessage(this);
    }

    @Override
    public BaseMessage serverCloneMessage() {
        return null;
    }
}
