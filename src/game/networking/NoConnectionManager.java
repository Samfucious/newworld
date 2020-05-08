/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking;

import game.application.Application;

/**
 *
 * @author samfucious
 */
public class NoConnectionManager implements IMessenger {

    @Override
    public int getClientId() {
        return 0;
    }

    @Override
    public void send(BaseMessage message) {
        Application.getApplication().postMessage(message);
    }
    
}
