/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import extensions.GeometryExtensions;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
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
        
        GeometryExtensions.setUVPlanaerProject(geometry, Vector3f.UNIT_Z);
        mesh.scaleTextureCoordinates(Vector2f.UNIT_XY.mult(4.0f));
        
        this.attachChild(geometry);
    }
}
