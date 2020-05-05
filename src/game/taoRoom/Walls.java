/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.taoRoom;

import Helpers.Materials;
import Helpers.Textures.TexturePaths;
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
 * @author gyrep
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
