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
package game.entities;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

/**
 *
 * @author Sam Iredale "Samfucious" (gyrepin@gmail.com)
 */
@Getter
public class AvatarInputStateManager {
    
    public enum Movements {
        FORWARD("forward"),
        BACKWARD("backward"),
        LEFT("left"),
        RIGHT("right");
        
        private final String movement;
        
        Movements(String movement) {
            this.movement = movement;
        }
        
        public String getMovement() {
            return this.movement;
        }
    }
    
    static final HashMap<Movements, Vector3f> movementVectors = new HashMap();
    static {
        movementVectors.put(Movements.LEFT, Vector3f.UNIT_X.negate());
        movementVectors.put(Movements.RIGHT, Vector3f.UNIT_X);
        movementVectors.put(Movements.FORWARD, Vector3f.UNIT_Z);
        movementVectors.put(Movements.BACKWARD, Vector3f.UNIT_Z.negate());
    }
    
    private final Vector3f direction = new Vector3f();
    private long lastJump;
    private final Camera camera;
    private final Set<Movements> movements = new HashSet();
    
    public AvatarInputStateManager(Camera camera) {
        this.camera = camera;
    }
    
    public Vector3f getLookAt() {
        return camera.getDirection();
    }
    
    public boolean hasMovements() {
        return movements.size() > 0;
    }
    
    public void updateMovement(Movements movement, boolean activate) {
        if (activate) { 
            addMovement(movement);
        }
        else { 
            removeMovement(movement);
        }
    }
    
    public void addMovement(Movements movement) {
        movements.add(movement);
        updateMovementVector();
    }
    
    public void removeMovement(Movements movement) {
        movements.remove(movement);
        updateMovementVector();
    }
    
    public void setLastJump(long timestamp) {
        this.lastJump = timestamp;
    }
    
    private void updateMovementVector() {
        Vector3f newDirection = new Vector3f(0.0f, 0.0f, 0.0f); // Vector.ZERO appears to be a problematically modifiable singleton.
        movements.forEach((movement) -> {
            newDirection.addLocal(movementVectors.get(movement));
        });
        newDirection.normalizeLocal();
        this.direction.set(newDirection);
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("hasMovements() => ").append(hasMovements()).append("\n");
        builder.append("Set size = ").append(movements.size()).append("\n");
        movements.forEach(movement -> {
            builder.append(movement).append(", ");
        });
        return builder.toString();
    }
}
