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
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Cylinder;
import game.application.Application;
import helpers.Materials;

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
    
    private final AvatarActionsState clientActionsState = new AvatarActionsState(); // Represents the intentions of the client.
    private final AvatarActionsState serverActionsState = new AvatarActionsState(); // Represents the understand of the server.
    
    public Avatar(int clientId, Vector3f location, Vector3f viewDirection) {
        this.clientId = clientId;
        initGeometry();
        initCharacterControl(location);
        setLocalTranslation(location);
        characterControl.setViewDirection(viewDirection);
    }
    
    private void initGeometry() {
        Geometry geometry = new Geometry("avatar_geo" + this.clientId, new Cylinder(2, 8, 0.5f, 2.0f, true));
        Quaternion georot = new Quaternion();
        geometry.setLocalRotation(georot.fromAngleAxis(FastMath.PI * 0.5f, Vector3f.UNIT_Z));
        Material material = new Material(Application.getAssetManager(), Materials.MatDefs.Unshaded.getMatDef());
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
        characterControl.setUseViewDirection(true);
        characterControl.setSpatial(this);
        addControl(characterControl);
    }
    
    @Override
    public void setLocalTranslation(Vector3f translation) {
        characterControl.setPhysicsLocation(translation);
    }
    
    @Override
    public void update(float t) {
        float speed = serverActionsState.isMoving() ? MOVEMENT_SPEED : 0.0f;
        Vector3f viewDiectionNormal = serverActionsState.getViewDirection().normalize();
        Vector3f movementz = viewDiectionNormal
                .mult(serverActionsState.getMovementDirection().z);
        Vector3f movementx = viewDiectionNormal
                .cross(Vector3f.UNIT_Y)
                .normalize()
                .mult(serverActionsState.getMovementDirection().x);
        Vector3f movement = movementz.add(movementx);
        characterControl.setWalkDirection(movement.mult(speed));
        characterControl.update(t);
    }
    
    public void setViewDirection(Vector3f viewDirection) {
        characterControl.setViewDirection(viewDirection);

        Quaternion rotation = new Quaternion();
        Vector3f xaxis = viewDirection.cross(Vector3f.UNIT_Y);
        Vector3f yaxis = viewDirection.cross(xaxis);
        this.setLocalRotation(rotation.fromAxes(xaxis, yaxis, viewDirection));
    }
    
    public Vector3f getViewDirection() {
        return characterControl.getViewDirection();
    }
    
    public void jump() {
        characterControl.jump();
    }
}
