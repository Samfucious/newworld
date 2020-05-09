/*
 * Copyright (C) 2020 samfucious
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package helpers;

import com.jme3.audio.AudioNode;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class Nodes {
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
