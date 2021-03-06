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
package game.application;

import helpers.Textures;
import com.jme3.app.state.ScreenshotAppState;
import com.jme3.audio.AudioNode;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.util.SkyFactory;
import game.Configuration;
import game.application.InputBindings.Binding;
import game.application.InputBindings.Trigger;
import game.networking.ClientConnectionManager;
import game.entities.Avatar;
import game.entities.AvatarActionsState;
import game.entities.AvatarInputStateManager;
import game.messages.BaseMessage;
import game.messages.IMessenger;
import game.messages.ITargetClient;
import game.messages.PongMessage;

/**
 *
 * @author Sam Iredale "Samfucious" (gyrepin@gmail.com)
 */
public class ClientApp extends BaseApp implements ActionListener {
    
    protected IMessenger messenger;
    private AvatarInputStateManager inputState;
    
    protected Avatar getLocalAvatar() {
        return getAvatar(messenger.getClientId());
    }
    
    @Override
    public void run() {
        this.start();
    }
    
    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        initBloomFilter();
        initSky();
        setUpKeys();
        inputState = new AvatarInputStateManager(this.cam);
        
        messenger = initMessageManager();
        messenger.start();
        
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
        getRootNode().attachChild(SkyFactory.createSky(getAssetManager(),
                Textures.TexturePaths.Sky.getTexturePath(), SkyFactory.EnvMapType.CubeMap));
    }
    
    private void setUpKeys() {        
        for(Binding binding : InputBindings.Binding.values()) {
            int key = Trigger.valueOf(binding.name()).getKey();
            inputManager.addMapping(binding.getCommand(), new KeyTrigger(key));
            inputManager.addListener(this, binding.name());
        }
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
            // Get the diffs between the AvatarActionsStates, and send messages.
            AvatarActionsState serverState = avatar.getServerActionsState();
            AvatarActionsState clientState = avatar.getClientActionsState();
            clientState.applyInputState(inputState); // Translate user input to actions states.
            messenger.send(AvatarActionsState.createMessagesForDifferences(serverState, clientState));
            
            // Do base simulation update and position camera.
            super.simpleUpdate(tpf);
            cam.setLocation(avatar.getCharacterControl().getPhysicsLocation());
        }
        else {
            super.simpleUpdate(tpf);
        }
    }
    
    @Override
    public void onAction(String binding, boolean isPressed, float tpf) {
        inputState.updateMovement(Binding.valueOf(binding), isPressed);
    }
        
    @Override
    public int getClientId() {
        return messenger.getClientId();
    }

    @Override
    public void playAudio(AudioNode audioNode) {
        audioNode.play();
    }
    
    public long getPing() {
        return PongMessage.getAverageLag();
    }
    
    @Override
    public void stop() {
        messenger.stop();
        super.stop();
    }
}
