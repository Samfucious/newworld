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

/**
 *
 * @author Sam Iredale "Samfucious" (gyrepin@gmail.com)
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
