/*
 * Copyright (C) 2020 Sam Iredale "Samfucious" (gyrepin@gmail.com)
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
package game.entities.taoRoom;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import helpers.Nodes;
import lombok.experimental.ExtensionMethod;
import game.application.Application;
import game.entities.UpdatingNode;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
@ExtensionMethod({ Nodes.class })
public class TwoPolarities extends UpdatingNode {
    
    private static final float ROTATION_RATE = (2.0f * FastMath.PI) / 60.0f;
    
    AmbientLight yangLight;
    DirectionalLight yinLight;
        
    private final Polarity[] polarities = {
        new Polarity(Polarities.Polarity.Yin),
        new Polarity(Polarities.Polarity.Yang)
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
