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

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Cylinder;
import game.application.Application;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class Avatar extends UpdatingNode {
    
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
    
    private static Mesh mesh;
    
    int clientId;
    CharacterControl characterControl = new CharacterControl();
    CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(0.5f, 2f, 1);
    
    Vector3f lookat = Vector3f.UNIT_Z;
    Vector3f left = Vector3f.UNIT_X.negate();
    
    Vector3f movementDirection = new Vector3f();
    float movementSpeed = 0.1f;
    
    Set<Movements> movements = new HashSet();
    static final HashMap<Movements, Vector3f> movementVectors = new HashMap();
    static {
        movementVectors.put(Movements.LEFT, Vector3f.UNIT_X.negate());
        movementVectors.put(Movements.RIGHT, Vector3f.UNIT_X);
        movementVectors.put(Movements.FORWARD, Vector3f.UNIT_Z.negate());
        movementVectors.put(Movements.BACKWARD, Vector3f.UNIT_Z);
    }
    
    public Avatar(int clientId, Vector3f location, Quaternion rotation) {
        this.clientId = clientId;
        initGeometry();
        initCharacterControl(location);
        setLocalRotation(rotation);
    }
    
    private void initGeometry() {
        Geometry geometry = new Geometry("avatar", new Cylinder(2, 8, 0.5f, 2.0f, true));
        Material material = new Material(Application.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Pink);
        geometry.setMaterial(material);
        attachChild(geometry);
    }
    
    public Integer getAvatarId() {
        return clientId;
    }
    
    @Override
    public String getName() {
        return "Avatar_" + clientId;
    }
    
    public void setMovementVectors(Vector3f lookat, Vector3f left) {
        this.lookat = lookat;
        this.left = left;
    }
    
    public boolean isMoving() {
        return !movements.isEmpty();
    }
    
    public CharacterControl getCharacterControl() {
        return characterControl;
    }
    
    private void initCharacterControl(Vector3f location) {
        characterControl = new CharacterControl(capsuleShape, 0.5f);
        
        characterControl.setJumpSpeed(10);
        characterControl.setFallSpeed(30);
        characterControl.setGravity(30f);
        characterControl.setPhysicsLocation(location);
        
        addControl(characterControl);
    }
    
    @Override
    public void update(float t) {
        doMovementUpdate(t);
    }
    
    private void doMovementUpdate(float t) {
        if (isMoving()) {
            Vector3f movement = lookat.mult(-movementDirection.z)
                    .add(left.mult(-movementDirection.x)).normalize();
            characterControl.setWalkDirection(movement.mult(movementSpeed));
            characterControl.update(t);
        }
        else {
            characterControl.setWalkDirection(Vector3f.ZERO);
        }
    }
    
    public void addMovement(Movements movement) {
        movements.add(movement);
        updateMovementVector();
    }
    
    public void setMovements(Movements[] m) {
        movements.clear();
        movements.addAll(Arrays.asList(m));
        updateMovementVector();
    }
    
    public void removeMovement(Movements movement) {
        movements.remove(movement);
        updateMovementVector();
    }
    
    private void updateMovementVector() {
        movementDirection.set(0,0,0);
        movements.forEach((movement) -> {
            movementDirection.addLocal(movementVectors.get(movement));
        });
        movementDirection.normalizeLocal();
    }
    
    public void jump() {
        characterControl.jump();
    }
}
