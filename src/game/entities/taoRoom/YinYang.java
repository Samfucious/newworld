/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.entities.taoRoom;

import helpers.Materials;
import helpers.Textures.TexturePaths;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import com.jme3.util.TangentBinormalGenerator;
import extensions.GeometryExtensions;
import extensions.NodeExtensions;
import game.entities.UpdatingNode;
import net.wcomohundro.jme3.csg.CSGGeonode;
import net.wcomohundro.jme3.csg.CSGShape;

/**
 *
 * @author Sam Iredale (gyrepin@gmail.com)
 */
public class YinYang extends UpdatingNode {
    private static final float ROTATION_RATE = (2.0f * FastMath.PI) / 60.0f;
    private static final float THICKNESS = 1.0f;
    private static final int RADIAL_SAMPLES = 36;
    
    Node yinNode = new Node();
    Node yangNode = new Node();
    
    Spatial yin;
    Spatial yang;
    
    Quaternion rotationX = new Quaternion().fromAngleAxis(FastMath.PI * 0.5f, Vector3f.UNIT_X);
    
    private float rotation = 0.0f;
    
    public YinYang() {
        this(10.0f);
    }
    
    public YinYang(float radius) {
        initModel(radius);
    }
    
    private void initModel(float radius) {
        Material yinMat = Materials.newMaterial(TexturePaths.Yin.getTexturePath(), TexturePaths.YinNorm.getTexturePath());
        Material yangMat = Materials.newMaterial(TexturePaths.Yang.getTexturePath(), TexturePaths.YangNorm.getTexturePath());
        
        Vector3f dotTranslation = new Vector3f(0.0f, radius * 0.5f, 0.0f);
        
        Mesh mesh = createYinYangCSGGeonode(RADIAL_SAMPLES, radius, THICKNESS);
        Mesh dotMesh = createDotMesh(RADIAL_SAMPLES, radius, THICKNESS * 0.75f);
        
        yinNode.attachChild(createGeometry("yin", mesh, yinMat));
        yinNode.attachChild(newDotGeometry("yinDot", dotMesh, dotTranslation, yinMat));
        yangNode.attachChild(createGeometry("yang", mesh, yangMat));
        yangNode.attachChild(newDotGeometry("yangDot", dotMesh, dotTranslation, yangMat));
        
        Quaternion yangRotation = new Quaternion();
        yangRotation.fromAngleAxis(FastMath.PI, Vector3f.UNIT_Z);
        yangNode.rotate(yangRotation);
        
        this.attachChild(yinNode);
        this.attachChild(yangNode);
        NodeExtensions.setRotation(this, rotationX);
    }
    
    private static Geometry createGeometry(String name, Mesh mesh, Material material) {        
        Geometry geometry = new Geometry(name, mesh);
        geometry.setMaterial(material);
        TangentBinormalGenerator.generate(geometry);
        return geometry;
    }
    
    private static Mesh createDotMesh(int radialSamples, float radius, float thickness) {
        return new Cylinder(2, radialSamples, radius * 0.125f, thickness, true);
    }
    
    private static Geometry newDotGeometry(String name, Mesh dotMesh, Vector3f translation, Material material) {        
        Geometry geometry = new Geometry(name, dotMesh);
        geometry.setMaterial(material);
        geometry.setLocalTranslation(translation);
        TangentBinormalGenerator.generate(geometry);
        GeometryExtensions.setUVPlanaerProject(geometry, Vector3f.UNIT_Z);
        return geometry;
    }
    
    private static Mesh createYinYangCSGGeonode(int radialSamples, float radius, float thickness) {
        CSGGeonode cutaway = new CSGGeonode();
        {
            CSGShape cutawayBox = new CSGShape("box", new Box(radius, radius , thickness));
            cutawayBox.setLocalTranslation(radius, 0.0f, 0.0f);
            cutaway.addShape(cutawayBox);

            CSGShape polarityDisk1 = new CSGShape("semidisk", new Cylinder(2, radialSamples, radius * 0.5f, thickness, true));
            polarityDisk1.setLocalTranslation(0.0f, radius * 0.5f, 0.0f);
            cutaway.addShape(polarityDisk1);

            CSGShape polarityDisk2 = new CSGShape("semidisk", new Cylinder(2, radialSamples, radius * 0.5f, thickness, true));
            polarityDisk2.setLocalTranslation(0.0f, -radius * 0.5f, 0.0f);
            cutaway.subtractShape(polarityDisk2);
            
            CSGShape spot = new CSGShape("semidisk", new Cylinder(2, radialSamples, radius * 0.125f, thickness, true));
            spot.setLocalTranslation(0.0f, -radius * 0.5f, 0.0f);
            cutaway.addShape(spot);
        }
        CSGShape cutawayFinal = cutaway.regenerate();
        
        CSGGeonode geometry = new CSGGeonode();
        CSGShape semiDisk = new CSGShape("semidisk", newTaoMesh(radialSamples, radius, thickness));
        geometry.addShape(semiDisk);
        geometry.subtractShape(cutawayFinal);
        
        geometry.regenerate();
        return geometry.getMasterGeometry().getMesh();
    }
    
    private static Mesh newTaoMesh(int radialSamples, float radius, float thickness) {
        Cylinder cylinder = new Cylinder(2, radialSamples, radius, thickness, true);
        Geometry geometry = new Geometry("remapuv", cylinder);
        GeometryExtensions.setUVPlanaerProject(geometry, Vector3f.UNIT_Z);
        return geometry.getMesh();
    }
    
    @Override
    public void update(float t) {
        rotation = (rotation + (ROTATION_RATE * t)) % (FastMath.PI * 2);
        Quaternion rotationZ = new Quaternion().fromAngleAxis(rotation, Vector3f.UNIT_Z);
        NodeExtensions.setRotation(this, rotationX.mult(rotationZ));
    }
}
