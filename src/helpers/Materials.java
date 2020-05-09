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
package helpers;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;
import game.application.Application;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class Materials {
    public enum MatDefs {
        Lighting("MatDefs/Light/Lighting.j3md"),
        Emitter("Common/MatDefs/Misc/Particle.j3md"),
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
    
    public static Material newEmitterMaterial() {
        Material material = new Material(Application.getAssetManager(), MatDefs.Emitter.getMatDef());
        material.setTexture("Texture", Application.getAssetManager().loadTexture(
            Textures.TexturePaths.Emitter.getTexturePath()));
        return material;
    }
}
