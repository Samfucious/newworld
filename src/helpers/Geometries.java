/*
 * Copyright (C) 2020 Sam Iredale "Samfucious" (gyrepin@gmail.com)
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
package helpers;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.VertexBuffer;

/**
 *
 * @author Sam Iredale "Samfucious" (gyrepin@gmail.com)
 */
public class Geometries {
    public static Geometry setUVPlanaerProject(Geometry geometry, Vector3f projectionNormal) {
        VertexBuffer position = geometry.getMesh().getBuffer(VertexBuffer.Type.Position);
        VertexBuffer texcoord = geometry.getMesh().getBuffer(VertexBuffer.Type.TexCoord);
        
        // Calculate the transform vectors
        projectionNormal = projectionNormal.normalize();
        float b = 0.0f != projectionNormal.x ? projectionNormal.x : projectionNormal.z;
        float a = 0.0f != projectionNormal.y ? projectionNormal.y : projectionNormal.z;
        Vector3f uVect = new Vector3f(a, -b, 0).normalize();
        Vector3f vVect = projectionNormal.cross(uVect);
        
        for (int i = 0; i < position.getNumElements(); i++) {
            // Read from vertex buffer
            Vector3f vector = new Vector3f();
            vector.x = ((Float) position.getElementComponent(i, 0));
            vector.y = ((Float) position.getElementComponent(i, 1));
            vector.z = ((Float) position.getElementComponent(i, 2));
            vector = vector.normalize();
            
            // Calculate uv coordinates
            float u = uVect.dot(vector);
            float v = vVect.dot(vector);
            
            // Write the uv coords the the Texcoord buffer.
            texcoord.setElementComponent(i, 0, u);
            texcoord.setElementComponent(i, 1, v);
        }
        
        return geometry;
    }
}
    