/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entities.taoRoom;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class Polarities {
    public enum Polarity {
        Yin (0),
        Yang (1);
        
        private final int polarity;
        
        Polarity(int polarity) {
            this.polarity = polarity;
        }
        
        public int getPolarity() {
            return this.polarity;
        }
    }
    
    public enum Textures {
        Yin (helpers.Textures.TexturePaths.Yin),
        Yang (helpers.Textures.TexturePaths.Yang);
        
        private final helpers.Textures.TexturePaths texture;
        
        Textures(helpers.Textures.TexturePaths texture) {
            this.texture = texture;
        }
        
        public helpers.Textures.TexturePaths getTexture() {
            return this.texture;
        }
    }
    
    public enum NormalTextures {
        Yin (helpers.Textures.TexturePaths.YinNorm),
        Yang (helpers.Textures.TexturePaths.YangNorm);
        
        private final helpers.Textures.TexturePaths texture;
        
        NormalTextures(helpers.Textures.TexturePaths texture) {
            this.texture = texture;
        }
        
        public helpers.Textures.TexturePaths getTexture() {
            return this.texture;
        }
    }
    
    public static String getTexturePath(Polarities.Polarity polarity) {
        return Polarities.Textures.valueOf(polarity.toString()).getTexture().getTexturePath();
    }
    
    public static String getNormalTexturePath(Polarities.Polarity polarity) {
        return Polarities.NormalTextures.valueOf(polarity.toString()).getTexture().getTexturePath();
    }
}
