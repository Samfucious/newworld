/*
 * Copyright (C) 2020 gyrep
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
package game.application;

import com.jme3.input.KeyInput;

/**
 *
 * @author gyrep
 */
public class InputBindings {
    public enum Binding {
        Left("Left"),
        Right("Right"),
        Forward("Forward"),
        Backward("Backward"),
        Jump("Jump");
        
        private final String command;
        Binding(String command) {
            this.command = command;
        }
        
        public String getCommand() {
            return this.command;
        }
    }
    
    public enum Trigger {
        Left(KeyInput.KEY_A),
        Right(KeyInput.KEY_D),
        Forward(KeyInput.KEY_W),
        Backward(KeyInput.KEY_S),
        Jump(KeyInput.KEY_SPACE);
        
        private final int key;
        Trigger(int key) {
            this.key = key;
        }
        
        public int getKey() {
            return this.key;
        }
    }
}
