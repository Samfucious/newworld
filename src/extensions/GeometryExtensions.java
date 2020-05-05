/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extensions;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.VertexBuffer;

/**
 *
 * @author gyrep
 */
public class GeometryExtensions {
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
    