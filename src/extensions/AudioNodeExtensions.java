/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extensions;

import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import game.application.Application;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class AudioNodeExtensions {
    public static AudioNode newAudioNode() {
        AudioNode audioNode = new AudioNode(Application.getAssetManager(), "Sound/Environment/Ocean Waves.ogg", DataType.Stream);
        audioNode.setLooping(true);
        audioNode.setPositional(true);
        audioNode.setVolume(3);
        return audioNode;
    }
}
