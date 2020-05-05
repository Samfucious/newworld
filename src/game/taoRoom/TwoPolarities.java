/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.taoRoom;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import extensions.NodeExtensions;
import lombok.experimental.ExtensionMethod;
import game.application.Application;
import game.entities.UpdatingNode;

/**
 *
 * @author gyrep
 */
@ExtensionMethod({ NodeExtensions.class })
public class TwoPolarities extends UpdatingNode {
    private static final float ROTATION_RATE = (2.0f * FastMath.PI) / 60.0f;
    
    AmbientLight yangLight;
    DirectionalLight yinLight;
        
    private final Polarity[] polarities = {
        new Polarity(Polarity.Polarities.Yin),
        new Polarity(Polarity.Polarities.Yang)
    };
    private float rotation = 0.0f;
    
    public TwoPolarities() {
        this(5.0f);
    }
    
    public TwoPolarities(float radius) {
        initObjectHeirarchy();
        positionPolarities(radius);
        initYangLight();
        initYinLight();
    }
    
    private void initObjectHeirarchy() {
        this.attachChild(polarities[0]);
        this.attachChild(polarities[1]);
    }
    
    private void positionPolarities(float radius) {
        polarities[0].setPosition(Vector3f.UNIT_Z.mult(radius));
        polarities[1].setPosition(Vector3f.UNIT_Z.mult(-radius));
    }
    
    private void initYangLight() {
        yangLight = new AmbientLight();
        yangLight.setColor(ColorRGBA.White.mult(2.0f));
        Application.getRootNode().addLight(yangLight);
        updateLight();
    }
    
    private void initYinLight() {
        yinLight = new DirectionalLight();
        yinLight.setColor(ColorRGBA.Blue.mult(0.1f));
        yinLight.setDirection(Vector3f.UNIT_Y.negate());
        Application.getRootNode().addLight(yinLight);
    }
    
    @Override
    public void update(float t) {
        rotation = (rotation + (ROTATION_RATE * t)) % (FastMath.PI * 2);
        this.setRotation(-rotation, Vector3f.UNIT_X);
        updateLight();
    }
    
    private void updateLight() {
        Vector3f p = this.getWorldTranslation();
        Vector3f c = polarities[1].getWorldTranslation();
        Vector3f direction = c.subtract(p).normalize();
        
        float intensity = FastMath.sin(direction.normalize().y);
        
        yangLight.setColor(ColorRGBA.White.mult(Math.max(intensity, 0.0f)));
    }
}
