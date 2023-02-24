package de.tomgrill.gdxtesting.examples;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Map;
import com.mygdx.game.Player;
import com.mygdx.game.Tank;
import de.tomgrill.gdxtesting.GdxTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)

public class TankTest {
    public SpriteBatch batch;
    public Map map = new Map(1);

    @Test
    public void testTank() {

        Player player = new Player("Player");

        Tank testTank = new Tank(new Sprite(new Texture("../core/assets/Blume.png")), batch, map);
        player.setTank(testTank);
        testTank.setSchusstyp(10);
        Assert.assertEquals(testTank.schusstyp, 6);
    }
}
