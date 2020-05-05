/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extensions;

import com.jme3.audio.AudioNode;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author gyrep
 */
public class NodeExtensions {
    public static Node setRotation(Node node, float radians, Vector3f axis) {
        Quaternion rotation = new Quaternion();
        rotation.fromAngleAxis(radians, axis);
        node.setLocalRotation(rotation);
        return node;
    }
    
    public static Node setRotation(Node node, Quaternion quaternion) {
        node.setLocalRotation(quaternion);
        return node;
    }
    
    public static Node setPosition(Node node, Vector3f position) {
        node.setLocalTranslation(position);
        return node;
    }
    
    public static Node attachAudioNode(Node node, AudioNode audioNode) {
        node.attachChild(audioNode);
        return node;
    }
}
