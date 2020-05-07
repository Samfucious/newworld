/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author gyrep
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
