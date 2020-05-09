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
