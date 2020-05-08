/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
