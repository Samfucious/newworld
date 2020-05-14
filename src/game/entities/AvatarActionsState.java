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
import game.application.Application;
import game.messages.BaseMessage;
import game.messages.avatar.actions.JumpMessage;
import game.messages.avatar.actions.UpdateIsMovingMessage;
import game.messages.avatar.actions.UpdateMovementDirectionMessage;
import game.messages.avatar.actions.UpdateViewDirectionMessage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Sam Iredale "Samfucious" (gyrepin@gmail.com)
 */
@Getter
@Setter
public class AvatarActionsState {
    private Vector3f movementDirection = new Vector3f(); // in avatar vecotr space
    private Vector3f viewDirection = new Vector3f();
    private boolean isMoving = false;
    private long lastJump = 0;
    
    public static Collection<BaseMessage> createMessagesForDifferences(AvatarActionsState remoteState, AvatarActionsState localState) {
        List<BaseMessage> messages = new ArrayList();
        int clientId = Application.getApplication().getClientId();
        
        if (!remoteState.movementDirection.equals(localState.movementDirection)) {
            messages.add(new UpdateMovementDirectionMessage(clientId, localState.getMovementDirection()));
        }
        if (!remoteState.viewDirection.equals(localState.viewDirection)) {
            Camera camera = Application.getApplication().getCamera();
            messages.add(new UpdateViewDirectionMessage(clientId, camera.getDirection()));
        }
        if (remoteState.isMoving != localState.isMoving) {
            messages.add(new UpdateIsMovingMessage(clientId, localState.getMovementDirection(), localState.isMoving()));
        }
        if (remoteState.lastJump != localState.lastJump) {
            messages.add(new JumpMessage(clientId, localState.lastJump));
        }
        
        return messages;
    }
    
    public AvatarActionsState copyTo(AvatarActionsState state) {
        state.setMovementDirection(movementDirection);
        state.viewDirection = viewDirection;
        state.isMoving = isMoving;
        state.lastJump = lastJump;
        return state;
    }
    
    public AvatarActionsState copy(AvatarActionsState state) {
        return copyTo(new AvatarActionsState());
    }
    
    public void applyInputState(AvatarInputStateManager inputState) {
        this.lastJump = inputState.getLastJump();
        this.isMoving = inputState.hasMovements();
        this.setMovementDirection(inputState.getDirection());
        this.viewDirection = inputState.getLookAt();
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("getMovementDirection() => ").append(getMovementDirection()).append("\n");
        builder.append("getViewDirection() => ").append(getViewDirection()).append("\n");
        builder.append("getIsMoving() => ").append(isMoving()).append("\n");
        builder.append("getLastJump() => ").append(getLastJump()).append("\n");
        return builder.toString();
    }
}
