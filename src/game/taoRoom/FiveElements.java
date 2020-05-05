/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.taoRoom;

import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import game.application.Application;
import game.entities.UpdatingNode;

/**
 *
 * @author gyrep
 */
public class FiveElements extends UpdatingNode {
    private static final float POSITIONING_DELTA = 2.0f * FastMath.PI / 5.0f;
    private static final float DEFAULT_RADIUS = 3.0f;
    private static final float ROTATION_RATE = (2.0f * FastMath.PI) / 300.0f;
    
    private float rotation = 0.0f;
    
    private final Element[] elements = {
        new Element(Elements.Element.WATER),
        new Element(Elements.Element.WOOD),
        new Element(Elements.Element.FIRE),
        new Element(Elements.Element.EARTH),
        new Element(Elements.Element.METAL)
    };
    
    public FiveElements() {
        this(DEFAULT_RADIUS);
    }
    
    public FiveElements(float radius) {
        initObjectHeirarchy();
        positionElements(radius);
    }
    
    private void initObjectHeirarchy() {
        for (Element element : elements) {
            this.attachChild(element);
        }
    }
    
    private void positionElements(float radius) {
        float phi = 0.0f;
        
        for (Element element : elements) {
            float x = FastMath.cos(phi) * radius;
            float z = FastMath.sin(phi) * radius;
            element.setLocalTranslation(new Vector3f(x, 0.0f, z));
            phi += POSITIONING_DELTA;
        }
    }
    
    @Override
    public void update(float t) {
        rotation = (rotation + (ROTATION_RATE * t)) % (FastMath.PI * 2);
        Quaternion quaternion = new Quaternion();
        quaternion.fromAngleAxis(-rotation, Vector3f.UNIT_Y);
        this.setLocalRotation(quaternion);
    }
}
