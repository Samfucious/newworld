/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking;

import com.jme3.network.AbstractMessage;

/**
 *
 * @author gyrep
 */
public abstract class BaseMessage extends AbstractMessage {
    public abstract void processMessage();
    public abstract BaseMessage serverCloneMessage();
}
