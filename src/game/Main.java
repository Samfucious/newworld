/*
 * Copyright (C) 2020 samfucious
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
package game;

import game.application.LocalApp;
import game.application.ClientApp;
import game.application.Application;
import game.application.ServerApp;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class Main {
    public static void main(String[] args) {
        Configuration.initConfiguration(args);
        
        if (Configuration.hasConfiguration(Configuration.Configurations.HELP)) {
            Configuration.displayHelpMessage();
            return;
        }
        
        if (!Configuration.hasConfiguration(Configuration.Configurations.SKIPDIALOG)) {
            StartupSelector selector = new StartupSelector();
            selector.start();
            selector.dispose();
        }
        
        if (Configuration.isServer()) {
            Application.initApplication(new ServerApp()).run();
        }
        else if (Configuration.hasConfiguration(Configuration.Configurations.CONNECT)) {
            Application.initApplication(new ClientApp()).run();
        }
        else {
            Application.initApplication(new LocalApp()).run();
        }
    }
}
