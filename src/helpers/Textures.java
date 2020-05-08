/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 *
 * @author gyrep
 */
public class Textures {
    public enum TexturePaths {
        Yin("Textures/Tao/Yin/CrackedAsphaltRoad_basecolor.png"),
        YinNorm("Textures/Tao/Yin/CrackedAsphaltRoad_normal.png"),
        Yang("Textures/Tao/Yang/SubwayPlasterCeiling_basecolor.png"),
        YangNorm("Textures/Tao/Yang/SubwayPlasterCeiling_normal.png"),
        Floor("Textures/Stone/BrokenLimestoneBrickPath_basecolor.png"),
        FloorNorm("Textures/Stone/BrokenLimestoneBrickPath_normal.png"),
        Wall("Textures/Wood/wood02.png"),
        WallNorm("Textures/Wood/wood02_normal.png"),
        Sky("Textures/Sky/Bright/FullskiesBlueClear03.dds"),
        Emitter("Textures/Emitter/emitter_grey.png");
        
        private final String texturePath;
        
        TexturePaths(String texturePath) {
            this.texturePath = texturePath;
        }
        
        public String getTexturePath() {
            return this.texturePath;
        }
    }
}
