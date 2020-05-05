/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.App;

import com.jme3.app.state.ScreenshotAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.system.JmeContext;
import com.jme3.util.SkyFactory;
import game.Configuration;
import game.networking.Connection;
import game.entities.Avatar;
import game.networking.messages.Avatar.AvatarJumpMessage;
import game.networking.messages.Avatar.AvatarWalkMessage;
import game.networking.messages.Avatar.AvatarStrafeMessage;
import game.networking.BaseMessage;

/**
 *
 * @author gyrep
 */
public class ClientApp extends BaseApp implements ActionListener {
    protected Avatar getLocalAvatar() {
        if (Connection.getNetworkInitiaized())
        {
            return getAvatar(Connection.getClient().getId());
        }
        return null;
    }
    
    @Override
    public void run() {
        this.start(JmeContext.Type.Display);
        Connection.connectToServer(
            Configuration.getConfigurationValue(Configuration.Configurations.CONNECT, "localhost"),
            Integer.parseInt(Configuration.getConfigurationValue(Configuration.Configurations.PORT, "6550"))
        );
    }
    
    @Override
    public void simpleInitApp() {    
        super.simpleInitApp();
        initBloomFilter();
        // initSky();
        setUpKeys();
        
        ScreenshotAppState screenShotState = new ScreenshotAppState();
        this.stateManager.attach(screenShotState);
    }
    
    private void initBloomFilter() {
        FilterPostProcessor fpp=new FilterPostProcessor(assetManager);
        BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.Objects);
        bloom.setBloomIntensity(2.0f);
        bloom.setExposurePower(5.0f);
        bloom.setExposureCutOff(0.0f);
        bloom.setBlurScale(1.5f);
        fpp.addFilter(bloom);
        viewPort.addProcessor(fpp);
    }
    
    private void initSky() {
        getRootNode().attachChild(SkyFactory.createSky(getAssetManager(), "Textures/Sky/Bright/BrightSky.dds", SkyFactory.EnvMapType.CubeMap));
    }
    
    /** We over-write some navigational key mappings here, so we can
     * add physics-controlled walking and jumping:
     */
    private void setUpKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Jump");
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        Avatar avatar = getLocalAvatar();
        
        if (avatar != null) {
            avatar.setLocalRotation(cam.getRotation());
            avatar.setMovementVectors(cam.getDirection(), cam.getLeft());
            super.simpleUpdate(tpf);
            cam.setLocation(avatar.getCharacterControl().getPhysicsLocation());
        }
        else {
            super.simpleUpdate(tpf);
        }
    }
    
    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
        Avatar avatar = getLocalAvatar();
        int clientId = getClientId();
        int sourceId = clientId;
        
        BaseMessage message = null;
        switch (binding) {
            case "Left":
                message = new AvatarStrafeMessage(sourceId, clientId, avatar.getLocalTranslation(), avatar.getLocalRotation(), true, isPressed);
                break;
            case "Right":
                message = new AvatarStrafeMessage(sourceId, clientId, avatar.getLocalTranslation(), avatar.getLocalRotation(), false, isPressed);
                break;
            case "Up":
                message = new AvatarWalkMessage(sourceId, clientId, avatar.getLocalTranslation(), avatar.getLocalRotation(), true, isPressed);
                break;
            case "Down":
                message = new AvatarWalkMessage(sourceId, clientId, avatar.getLocalTranslation(), avatar.getLocalRotation(), false, isPressed);
                break;
            case "Jump":
                message = new AvatarJumpMessage(sourceId, clientId);
                break;
            default:
                break;
        }
        
        if (message != null)
        {
            postMessage(message);
            if(Connection.getNetworkInitiaized()) {
                Connection.getClient().send(message);
            }
        }
    }
    
    @Override
    public int getClientId() {
        if (Connection.getNetworkInitiaized()) {
            return Connection.getClient().getId();
        }
        return -1;
    }
}
