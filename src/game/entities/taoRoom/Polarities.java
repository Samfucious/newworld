/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entities.taoRoom;

/**
 *
 * @author samfucious
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
        Yin (Helpers.Textures.TexturePaths.Yin),
        Yang (Helpers.Textures.TexturePaths.Yang);
        
        private final Helpers.Textures.TexturePaths texture;
        
        Textures(Helpers.Textures.TexturePaths texture) {
            this.texture = texture;
        }
        
        public Helpers.Textures.TexturePaths getTexture() {
            return this.texture;
        }
    }
    
    public enum NormalTextures {
        Yin (Helpers.Textures.TexturePaths.YinNorm),
        Yang (Helpers.Textures.TexturePaths.YangNorm);
        
        private final Helpers.Textures.TexturePaths texture;
        
        NormalTextures(Helpers.Textures.TexturePaths texture) {
            this.texture = texture;
        }
        
        public Helpers.Textures.TexturePaths getTexture() {
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
