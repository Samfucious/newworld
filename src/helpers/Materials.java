/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;
import game.application.Application;

/**
 *
 * @author gyrep
 */
public class Materials {
    public enum MatDefs {
        Lighting("MatDefs/Light/Lighting.j3md"),
        Unshaded("Common/MatDefs/Misc/Unshaded.j3md");
        
        private final String matDef;
        
        MatDefs(String matdef) {
            this.matDef = matdef;
        }
        
        public String getMatDef() {
            return matDef;
        }
    }
    
    public static Material newMaterial(Texture diffuse) {
        Material material = new Material(Application.getAssetManager(), MatDefs.Lighting.getMatDef());
        material.setTexture("DiffuseMap", diffuse);
        material.setBoolean("UseMaterialColors",true);
        material.setColor("Diffuse", ColorRGBA.White);
        material.setColor("Specular", ColorRGBA.White);
        material.setColor("Ambient", ColorRGBA.White);
        material.setFloat("Shininess", 64f);
        return material;
    }
    
    public static Material newMaterial(String diffusePath) {
        Texture diffuse = Application.getAssetManager().loadTexture(diffusePath);
        diffuse.setWrap(Texture.WrapMode.Repeat);
        Material material = Materials.newMaterial(diffuse);
        return material;
    }
        
    public static Material newMaterial(Texture diffuse, Texture normal) {
        Material material = new Material(Application.getAssetManager(), MatDefs.Lighting.getMatDef());
        material.setTexture("DiffuseMap", diffuse);
        material.setTexture("NormalMap", normal);
        material.setBoolean("UseMaterialColors",true);
        material.setColor("Diffuse", ColorRGBA.White);
        material.setColor("Specular", ColorRGBA.White);
        material.setColor("Ambient", ColorRGBA.White);
        material.setFloat("Shininess", 64f);
        return material;
    }
    
    public static Material newMaterial(String diffusePath, String normalPath) {
        Texture diffuse = Application.getAssetManager().loadTexture(diffusePath);
        diffuse.setWrap(Texture.WrapMode.Repeat);
        Texture normal = Application.getAssetManager().loadTexture(normalPath);
        normal.setWrap(Texture.WrapMode.Repeat);
        Material material = Materials.newMaterial(diffuse, normal);
        return material;
    }
    
    public static Material newMaterial(ColorRGBA color, ColorRGBA glowColor) {
        Material material = new Material(Application.getAssetManager(), MatDefs.Unshaded.getMatDef());
        material.setColor("Color", color);
        material.setColor("GlowColor", color.mult(5.0f));
        return material;
    }
}
