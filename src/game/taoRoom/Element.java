/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.taoRoom;

import helpers.Materials;
import com.jme3.audio.AudioNode;
import com.jme3.light.SpotLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.control.LightControl;
import com.jme3.scene.shape.Sphere;
import game.application.Application;

/**
 *
 * @author gyrep
 */
public class Element extends Node {
    private static Mesh mesh;
    
    private Mesh getMesh() {
        return null != mesh ? mesh : (mesh = createMesh());
    }
    
    private static Mesh createMesh() {
        return new Sphere(20, 20, 0.15f);
    }
    
    public Element(Elements.Element element) {
        ColorRGBA color = Elements.getColor(element);
        initSpotLight(color);
        initGeometry(color);
        initAudio(element);
    }
    
    private void initGeometry(ColorRGBA color) {
        Geometry geometry = new Geometry("sphere", getMesh());
        Material material = Materials.newMaterial(color, color.mult(5.0f));
        geometry.setMaterial(material);
        this.attachChild(geometry);
    }
    
    private void initSpotLight(ColorRGBA color) {
        SpotLight light = new SpotLight();
        light.setSpotRange(20f);
        light.setSpotInnerAngle(45f * FastMath.DEG_TO_RAD);
        light.setSpotOuterAngle(80f * FastMath.DEG_TO_RAD);
        light.setColor(color.mult(2.0f));

        light.setDirection(new Vector3f(0.0f, -1.0f, 0.0f));
        Application.getApplication().getRootNode().addLight(light);
        
        LightControl control = new LightControl(light);
        this.addControl(control);
    }

    private void initAudio(Elements.Element element) {
        AudioNode audioNode = Elements.getAudioNode(element);
        this.attachChild(audioNode);
        audioNode.play();
    }
}
