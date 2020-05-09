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

import helpers.Materials;
import helpers.Textures.TexturePaths;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import com.jme3.util.TangentBinormalGenerator;
import net.wcomohundro.jme3.csg.CSGGeonode;
import net.wcomohundro.jme3.csg.CSGShape;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class Walls extends Node {
    
    public Walls(float radius, float height) {
        initGeometry(radius, height);
        setDefaultTransform(height * 0.5f);
    }
    
    private void setDefaultTransform(float y) {
        this.setLocalTranslation(Vector3f.UNIT_Y.mult(y));
        
        Quaternion rotation = new Quaternion();
        rotation.fromAngleAxis(FastMath.PI * 0.5f, Vector3f.UNIT_X);
        this.setLocalRotation(rotation);
    }
    
    private void initGeometry(float outradius, float height) {
        CSGGeonode walls = new CSGGeonode();
        CSGShape shape = new CSGShape("semidisk", new Cylinder(2, 8, outradius + 2.0f, height, true));
        CSGShape cutaway = new CSGShape("semidisk", new Cylinder(2, 8, outradius, height, true));
        
        walls.addShape(shape);
        walls.subtractShape(cutaway);
        walls.regenerate();
        
        Material mat = Materials.newMaterial(
                TexturePaths.Wall.getTexturePath(),
                TexturePaths.WallNorm.getTexturePath()
        );
        Geometry geometry = walls.getMasterGeometry();        
        geometry.setMaterial(mat);
        TangentBinormalGenerator.generate(geometry);
        
        float tscale = 10.0f;
        float theta = FastMath.PI * 0.25f;
        float wallWidth = 2.0f * outradius * FastMath.sin(theta);
        float tratio = (wallWidth * 0.5f) / height;
        geometry.getMesh().scaleTextureCoordinates(new Vector2f(tscale * tratio, tscale));
        
        this.attachChild(geometry);
    }
}
