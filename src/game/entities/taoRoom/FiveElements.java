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

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import game.entities.UpdatingNode;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
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
