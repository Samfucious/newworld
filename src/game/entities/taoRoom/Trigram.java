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

import com.jme3.scene.Node;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class Trigram extends Node {
    
    public enum Trigrams {
        QIAN (0),
        XUN (1),
        KAN (2),
        GEN (3),
        KUN (4),
        ZHEN (5),
        LI (6),
        DUI (7);
        
        private final int trigram;
        
        Trigrams(int trigram) {
            this.trigram = trigram;
        }
        
        public int getTrigram() {
            return this.trigram;
        }
    }
    
    private static final int TOP = 0;
    private static final int MIDDLE = 1;
    private static final int BOTTOM = 2;
    
    private final TrigramLine[] lines;
    
    public Trigram(boolean top, boolean middle, boolean bottom) {
        this.lines = new TrigramLine[] {
            new TrigramLine(top),
            new TrigramLine(middle),
            new TrigramLine(bottom)
        };
    }
    
    public Trigram[] trigrams() {
        return new Trigram[] {
            new Trigram(true, true, true),
            new Trigram(true, true, false),
            new Trigram(false, true, false),
            new Trigram(true, false, false),
            new Trigram(false, false, false),
            new Trigram(false, false, true),
            new Trigram(true, false, true),
            new Trigram(false, true, true),
        };
    }
}
