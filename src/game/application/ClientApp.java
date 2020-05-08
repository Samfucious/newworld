/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.application;

import helpers.Textures;
import com.jme3.app.state.ScreenshotAppState;
import com.jme3.audio.AudioNode;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.system.JmeContext;
import com.jme3.util.SkyFactory;
import game.Configuration;
import game.networking.ClientConnectionManager;
import game.entities.Avatar;
import game.networking.messages.avatar.AvatarJumpMessage;
import game.networking.messages.avatar.AvatarWalkMessage;
import game.networking.messages.avatar.AvatarStrafeMessage;
import game.networking.BaseMessage;
import game.networking.IMessenger;
import game.networking.ITargetClient;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class ClientApp extends BaseApp implements ActionListener {
    
    protected IMessenger messenger;
    
    protected Avatar getLocalAvatar() {
        return getAvatar(messenger.getClientId());
    }
    
    @Override
    public void run() {
        this.start(JmeContext.Type.Display);
    }
    
    @Override
    public void simpleInitApp() {    
        super.simpleInitApp();
        initBloomFilter();
        initSky();
        setUpKeys();
        messenger = initMessageManager();
        
        ScreenshotAppState screenShotState = new ScreenshotAppState();
        this.stateManager.attach(screenShotState);
    }
    
    @Override
    public void postMessage(BaseMessage message) {
        if(message instanceof ITargetClient) {
            super.postMessage(message);
        }
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
        getRootNode().attachChild(SkyFactory.createSky(getAssetManager(), Textures.TexturePaths.Sky.getTexturePath(), SkyFactory.EnvMapType.CubeMap));
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
    
    protected IMessenger initMessageManager() {
        String address = Configuration.getConfigurationValue(Configuration.Configurations.CONNECT, "localhost");
        int port = Integer.parseInt(Configuration.getConfigurationValue(Configuration.Configurations.PORT, "6550"));
        return new ClientConnectionManager(address, port);
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
            messenger.send(message);
        }
    }
    
    @Override
    public int getClientId() {
        return messenger.getClientId();
    }

    @Override
    public void playAudio(AudioNode audioNode) {
        audioNode.play();
    }
}
