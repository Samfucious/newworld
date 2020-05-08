/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entities.taoRoom;

import helpers.Materials;
import helpers.Textures;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import extensions.GeometryExtensions;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class Sky extends Node {
    public Sky() {
        initGeometry();
        this.setQueueBucket(Bucket.Sky);
        this.setCullHint(Spatial.CullHint.Never);
    }

    private void initGeometry() {
        Mesh sphere = new Sphere(20, 20, 1.0f);
        Geometry geometry = new Geometry("sky", sphere);
        geometry.setMaterial(Materials.newMaterial(Textures.TexturePaths.Sky.getTexturePath()));
        GeometryExtensions.setUVPlanaerProject(geometry, Vector3f.UNIT_Y.negate());
    }
}
