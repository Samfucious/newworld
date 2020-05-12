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

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

/**
 *
 * @author Sam Iredale "Samfucious" (gyrepin@gmail.com)
 */
public class Application {
    private static BaseApp application;
    
    public static BaseApp getApplication() {
        return Application.application;
    }
    
    public static BaseApp initApplication(BaseApp application) {
        if(null == Application.application) {
            Application.application = application;
        }
        return Application.application;
    }
    
    public static AssetManager getAssetManager() {
        return application.getAssetManager();
    }
    
    public static Node getRootNode() {
        return application.getRootNode();
    }
}
