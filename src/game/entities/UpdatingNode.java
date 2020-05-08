/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entities;

import com.jme3.scene.Node;
import game.application.Application;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public abstract class UpdatingNode extends Node implements IUpdateable {
    
    public UpdatingNode() {
        super();
        registerAsStateful();
    }    

    @Override
    public void registerAsStateful() {
        Application.getApplication().addStatefulObject(this);
    }

    @Override
    public void deregisterAsStateful() {
        Application.getApplication().removeStatefulObject(this.getName());
    }
}