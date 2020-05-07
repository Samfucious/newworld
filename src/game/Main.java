/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import game.application.LocalApp;
import game.application.ClientApp;
import game.application.Application;
import game.application.ServerApp;

/**
 *
 * @author gyrep
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
