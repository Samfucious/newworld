/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking.messages.object;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import com.jme3.scene.Spatial;
import game.application.Application;
import game.networking.BaseMessage;
import game.networking.ITargetClient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
@Serializable
@NoArgsConstructor
@Getter
@Setter
public class ObjectStateMessage extends BaseMessage implements ITargetClient {
    String name;
    Vector3f position;
    Quaternion rotation;
    
    public ObjectStateMessage(int sourceId, int clientId, String name, Vector3f position, Quaternion rotation) {
        super(sourceId, clientId);
        this.name = name;
        this.position = position;
        this.rotation = rotation;
    }

    @Override
    public void processMessage() {
        Spatial spatial = Application.getApplication().getStatefulObject(name);
        if(null != spatial) {
            spatial.setLocalTranslation(position);
            spatial.setLocalRotation(rotation);
        }
    }

    @Override
    public BaseMessage serverCloneMessage() {
        return null;
    }
}
