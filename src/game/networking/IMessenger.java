/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.networking;

/**
 *
 * @author samfucious
 */
public interface IMessenger {
    int getClientId();
    void send(BaseMessage message);
}
