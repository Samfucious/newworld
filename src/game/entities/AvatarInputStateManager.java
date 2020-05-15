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

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import game.application.InputBindings.Binding;
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
    static final HashMap<Binding, Vector3f> movementVectors = new HashMap();
    static {
        movementVectors.put(Binding.Left, Vector3f.UNIT_X.negate());
        movementVectors.put(Binding.Right, Vector3f.UNIT_X);
        movementVectors.put(Binding.Forward, Vector3f.UNIT_Z);
        movementVectors.put(Binding.Backward, Vector3f.UNIT_Z.negate());
    }
    
    private final Vector3f direction = new Vector3f();
    private long lastJump;
    private final Camera camera;
    private final Set<Binding> movements = new HashSet();
    
    public AvatarInputStateManager(Camera camera) {
        this.camera = camera;
    }
    
    public Vector3f getLookAt() {
        return camera.getDirection();
    }
    
    public boolean hasMovements() {
        return movements.size() > 0;
    }
    
    public void updateMovement(Binding binding, boolean activate) {
        if (activate) { 
            addMovement(binding);
        }
        else { 
            removeMovement(binding);
        }
    }
    
    private void addMovement(Binding binding) {
        if (Binding.Jump == binding) {
            updateJumpTimestamp();
        }
        else {
            movements.add(binding);
            updateMovementVector();
        }
    }
    
    private void removeMovement(Binding binding) {
        movements.remove(binding);
        updateMovementVector();
    }
    
    private void updateJumpTimestamp() {
        this.lastJump = System.currentTimeMillis();
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
