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

/**
 *
 * @author Sam Iredale "Samfucious" (gyrepin@gmail.com)
 */
public class Avatar extends UpdatingNode {
    private static final float MOVEMENT_SPEED = 0.1f;
    private static Mesh mesh;
    
    private final int clientId;
    private CharacterControl characterControl = new CharacterControl();
    private final CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(0.5f, 2f, 1);
    
    private Vector3f lookat = Vector3f.UNIT_Z;
    private Vector3f left = Vector3f.UNIT_X.negate();
    
    private final AvatarActionsState clientActionsState = new AvatarActionsState(); // Represents the intentions of the client.
    private final AvatarActionsState serverActionsState = new AvatarActionsState(); // Represents the understand of the server.
    
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
    
    public AvatarActionsState getClientActionsState() {
        return clientActionsState;
    }
    
    public AvatarActionsState getServerActionsState() {
        return serverActionsState;
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
        float speed = serverActionsState.isMoving() ? MOVEMENT_SPEED : 0.0f;
        Vector3f movement = serverActionsState.getRotation()
                .mult(serverActionsState.getMovementDirection().normalize());
        characterControl.setWalkDirection(movement.mult(speed));
        characterControl.update(t);
    }
    
    public void jump() {
        characterControl.jump();
    }
}
