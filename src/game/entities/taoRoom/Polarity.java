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
package game.entities.taoRoom;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import helpers.Materials;


/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class Polarity extends Node {
        
    Material material;
    
    private static Mesh mesh;
    
    private Mesh getMesh() {
        return null != mesh ? mesh : (mesh = createMesh());
    }
    
    private static Mesh createMesh() {
        return new Sphere(40, 40, 0.5f);
    }
    
    public Polarity(Polarities.Polarity polarity) {
        this.material = initMaterial(polarity);
        initGeometry();
    }
    
    private void initGeometry() {
        Geometry geometry = new Geometry("sphere", getMesh());
        geometry.setMaterial(material);
        this.attachChild(geometry);
    }
    
    private Material initMaterial(Polarities.Polarity polarity) {
        return Materials.newMaterial(
                Polarities.getTexturePath(polarity), 
                Polarities.getNormalTexturePath(polarity)
        );
    }
}
