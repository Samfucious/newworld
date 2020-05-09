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

import helpers.Materials;
import helpers.Textures.TexturePaths;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import com.jme3.util.TangentBinormalGenerator;
import helpers.Geometries;
import lombok.experimental.ExtensionMethod;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
@ExtensionMethod({ Geometries.class })
public class Floor extends Node { 
    private final float outradius;
    
    public Floor(float outradius) {
        this.outradius = outradius;
        Material mat = Materials.newMaterial(
                TexturePaths.Floor.getTexturePath(),
                TexturePaths.FloorNorm.getTexturePath()
        );
        initGeometry(mat);
    }
    
    private void initGeometry(Material material) {
        Mesh mesh = new Cylinder(2, 8, this.outradius, 0.5f, true);
        Geometry geometry = new Geometry("floor", mesh);
        geometry.setMaterial(material);
        TangentBinormalGenerator.generate(geometry);
        
        geometry.setUVPlanaerProject(Vector3f.UNIT_Z);
        mesh.scaleTextureCoordinates(Vector2f.UNIT_XY.mult(4.0f));
        
        this.attachChild(geometry);
    }
}
