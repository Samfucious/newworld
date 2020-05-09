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
