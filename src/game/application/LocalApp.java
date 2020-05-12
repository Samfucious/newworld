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

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import game.entities.Avatar;
import game.messages.IMessenger;
import game.networking.NoConnectionManager;
import game.messages.avatar.LocalAvatarCreateMessage;

/**
 *
 * @author Sam Iredale "Samfucious" (gyrepin@gmail.com)
 */
public class LocalApp extends ClientApp {
    @Override
    public void run() {
        this.start();
    }
    
    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
        initLocalAvatar();
    }
    
    private void initLocalAvatar() {
        postMessage(new LocalAvatarCreateMessage(new Avatar(0, Vector3f.UNIT_Y.mult(10.0f), Quaternion.IDENTITY)));
    }
    
    @Override
    public int getClientId() {
        return 0;
    }
    
    @Override
    protected Avatar getLocalAvatar() {
        return getAvatar(0);
    }
    
    @Override
    protected IMessenger initMessageManager() {
        return new NoConnectionManager();
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);
    }
}
