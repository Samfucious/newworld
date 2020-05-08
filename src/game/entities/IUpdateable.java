/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entities;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public interface IUpdateable {
    public void registerAsStateful();
    public void deregisterAsStateful();
    public void update(float tpm);
}
