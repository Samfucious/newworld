/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.taoRoom;

import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.math.ColorRGBA;
import game.application.Application;

/**
 *
 * @author gyrep
 */
public class Elements {
    public enum Element {
        WATER (0),
        WOOD (1),
        FIRE (2),
        EARTH (3),
        METAL (4);
        
        private final int element;
        
        Element(int element) {
            this.element = element;
        }
        
        public int getElement() {
            return this.element;
        }
    }
    
    public enum Colors {
        WATER (ColorRGBA.Blue),
        WOOD (ColorRGBA.Green),
        FIRE (ColorRGBA.Red),
        EARTH (ColorRGBA.Yellow),
        METAL (ColorRGBA.LightGray);
        
        private final ColorRGBA color;
        
        Colors(ColorRGBA color) {
            this.color = color;
        }
        
        public ColorRGBA getColor() {
            return color;
        }
    }
    
    public enum Sounds {
        WATER ("Sounds/Sound/Environment/water.ogg"),
        WOOD ("Sounds/Sound/Environment/wood.ogg"),
        FIRE ("Sounds/Sound/Environment/fire.ogg"),
        EARTH ("Sounds/Sound/Environment/earth.ogg"),
        METAL ("Sounds/Sound/Environment/metal.ogg");
        
        private final String sound;
        
        Sounds(String sound) {
            this.sound = sound;
        }
        
        public String getPath() {
            return sound;
        }
    }
    
    public static ColorRGBA getColor(Elements.Element element) {
        return Elements.Colors.valueOf(element.toString()).getColor();
    }
    
    public static String getSoundFilePath(Elements.Element element) {
        return (Elements.Sounds.valueOf(element.toString())).getPath();
    }
    
    public static AudioNode getAudioNode(Elements.Element element) {
        String path = getSoundFilePath(element);
        AudioNode audioNode = new AudioNode(Application.getAssetManager(), path, AudioData.DataType.Stream);
        audioNode.setLooping(true);
        audioNode.setPositional(true);
        audioNode.setVolume(0.5f);
        audioNode.setRefDistance(0.5f);
        return audioNode;
    }
}
