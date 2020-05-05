/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.taoRoom;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import extensions.NodeExtensions;
import lombok.experimental.ExtensionMethod;
import game.entities.UpdatingNode;

/**
 *
 * @author gyrep
 */
@ExtensionMethod({ NodeExtensions.class })
public class Room extends UpdatingNode {
    private static final float OUTRADIUS = 20.0f;
    
    private static final float WALL_HEIGHT = OUTRADIUS * (float)Math.sqrt(3.0f) / 8.0f;
    
    private final Floor floor = new Floor(OUTRADIUS);
    private final Walls walls = new Walls(OUTRADIUS, WALL_HEIGHT);
    private final YinYang yinYang = new YinYang();
    private final TwoPolarities polarities = new TwoPolarities();
    private final FiveElements elements = new FiveElements(7.0f);
    private final Sky sky = new Sky();
    
    public Room() {
        initObjectHeirarchy();
        positionElements();
    }
    
    private void initObjectHeirarchy() {
        this.attachChild(floor);
        this.attachChild(yinYang);
        this.attachChild(polarities);
        this.attachChild(elements);
        this.attachChild(walls);
        this.attachChild(sky);
    }
    
    private void positionElements() {
        Quaternion xrot = new Quaternion();
        xrot.fromAngleAxis(FastMath.PI * 0.5f, Vector3f.UNIT_X);
        Quaternion zrot = new Quaternion();
        zrot.fromAngleAxis(FastMath.PI / 8.0f, Vector3f.UNIT_Z);
        
        Quaternion trot = xrot.mult(zrot);
        
        floor.setPosition(new Vector3f(0.0f, -2.0f, 0.0f)).setRotation(trot);
        polarities.setPosition(new Vector3f(0.0f, -1.5f, 0.0f));
        yinYang.setPosition(new Vector3f(0.0f, -2.0f, 0.0f));
        
        Vector3f wallsTranslation = Vector3f.UNIT_Y.mult(WALL_HEIGHT * 0.5f);
        wallsTranslation.y -= 2.0f;
        walls.setPosition(wallsTranslation).setRotation(trot);
    }
    
    @Override
    public void update(float t) {
        elements.update(t);
        polarities.update(t);
        yinYang.update(t);
    }
}
