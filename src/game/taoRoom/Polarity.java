/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.taoRoom;

import Helpers.Materials;
import Helpers.Textures;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import game.App.Application;

/**
 *
 * @author gyrep
 */
public class Polarity extends Node {
    
    public enum Polarities {
        Yin (0),
        Yang (1);
        
        private final int polarity;
        
        Polarities(int polarity) {
            this.polarity = polarity;
        }
        
        public int getPolarity() {
            return this.polarity;
        }
    }
    
    Material material;
    
    private static Mesh mesh;
    
    private Mesh getMesh() {
        return null != mesh ? mesh : (mesh = createMesh());
    }
    
    private static Mesh createMesh() {
        return new Sphere(40, 40, 0.5f);
    }
    
    public Polarity(Polarities polarity) {
        this.material = initMaterial(polarity);
        initGeometry();
    }
    
    private void initGeometry() {
        Geometry geometry = new Geometry("sphere", getMesh());
        geometry.setMaterial(material);
        this.attachChild(geometry);
    }
    
    private Material initMaterial(Polarities polarity) {
        if (Polarities.Yin.equals(polarity)) {
            return Materials.newMaterial(
                    Textures.TexturePaths.Yin.getTexturePath(), 
                    Textures.TexturePaths.YinNorm.getTexturePath()
            );
        }
        
        return Materials.newMaterial(
                Textures.TexturePaths.Yang.getTexturePath(), 
                Textures.TexturePaths.YangNorm.getTexturePath()
        );
    }
}
