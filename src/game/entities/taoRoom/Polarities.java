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
