package de.tomgrill.gdxtesting.examples;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.DamageZone;
import com.mygdx.game.Map;
import com.mygdx.game.Tank;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(GdxTestRunner.class)

public class damageZoneTest {

    public SpriteBatch batch;
    public Map map = new Map(1);
    public DamageZone dz = new DamageZone();

    @Test
    public void testDZ() {

        Tank testTank = new Tank(new Sprite(new Texture("../core/assets/Blume.png")), batch, map);
        testTank.setXY(5, 23, map);
        dz.checkPickup(testTank);
        Assert.assertEquals(testTank.getDamageZoneMultiplier(), 2);

    }
}
