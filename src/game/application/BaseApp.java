package game.application;

import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import game.entities.Avatar;
import game.entities.IUpdateable;
import game.networking.BaseMessage;
import java.util.ArrayList;
import java.util.List;
import game.entities.taoRoom.Room;
import java.util.Collection;
import java.util.HashMap;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public abstract class BaseApp extends SimpleApplication {
    
    private final BulletAppState bulletAppState = new BulletAppState();
    private final List<IUpdateable> updateables = new ArrayList();
    private final HashMap<Integer, Avatar> avatars = new HashMap();
    private final HashMap<String, Spatial> statefulObjects = new HashMap();
    
    @Override
    public void simpleInitApp() {
        stateManager.attach(bulletAppState);
        
        Room room = new Room();
        addAsRigidBody(room);
        updateables.add(room);
    }
    
    public abstract void run();
    
    private void addAsRigidBody(Node node) {
        CollisionShape collisionShape = CollisionShapeFactory.createMeshShape(node);
        bulletAppState.getPhysicsSpace().add(new RigidBodyControl(collisionShape, 0));
        rootNode.attachChild(node);
    }

    @Override
    public void simpleUpdate(float tpf) {  
        updateables.forEach((updatingNode) -> { updatingNode.update(tpf); });
        avatars.values().forEach((avatar) -> { avatar.update(tpf); });
    }
    
    public void postMessage(BaseMessage message) {
        this.enqueue(() -> {
            message.processMessage();
        });
    }
    
    public void addAvatar(Avatar avatar) {
        bulletAppState.getPhysicsSpace().add(avatar.getCharacterControl());
        avatars.put(avatar.getAvatarId(), avatar);
    }
    
    public void removeAvatar(int avatarId) {
        avatars.remove(avatarId);
    }
    
    public Avatar getAvatar(int avatarId) {
        return avatars.get(avatarId);
    }
    
    public Spatial getStatefulObject(String name) {
        Spatial spatial = statefulObjects.get(name);
        if (null == spatial) {
            spatial = getRootNode().getChild(name);
        }
        return spatial;
    }
    
    public void addStatefulObject(Spatial spatial) {
        statefulObjects.put(spatial.getName(), spatial);
    }
    
    public void removeStatefulObject(String name) {
        statefulObjects.remove(name);
    }
    
    public Collection<Spatial> getStatefulObjects() {
        return statefulObjects.values();
    }
    
    public abstract int getClientId();
    
    public abstract void playAudio(AudioNode audioNode);
}
