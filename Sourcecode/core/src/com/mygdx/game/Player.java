package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Color;

/**
 * Player implements the keys to control the players tank.
 *
 * @author tatiana takoudjou
 */
public class Player {
    private String name;
    private Tank tank;
    int KEY_TURN_LEFT = Input.Keys.A;
    int KEY_TURN_RIGHT = Input.Keys.D;
    int Key_FORWARD = Input.Keys.W;
    int Key_BACKWARD = Input.Keys.S;
    int Key_Turret_Left = Input.Keys.Q;
    int Key_Turret_Right = Input.Keys.E;
    int Key_SHOOT = Input.Keys.SPACE;
    int Key_MINE = Input.Keys.F;
    int Key_Shottype = Input.Keys.R;
    int Key_UpgradeHealth = Input.Keys.J;
    int Key_UpgradeDamage = Input.Keys.U;

    int buttonForward = 2;
    int buttonBackward = 0;
    int buttonRotateTurretLeft = 4;
    int buttonRotateTurretRight = 5;
    int buttonShoot = 1;
    int axisLeftX = 1;
    int buttonMine = 3;
    PovDirection povUp = PovDirection.west;
    PovDirection povLe = PovDirection.north;
    PovDirection povRi = PovDirection.south;

    String[] colors = {"Green", "Blue", "Brown", "Cyan", "Yellow", "Pink", "Orange", "Red"};
    int currentColor = 0;

    public Player(String name) {
        this.name = name;

    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public Tank getTank() {
        return tank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Prüfe ob der Taste für diesen keycode von diesem Player gedrückt wird.
     * Wenn ja, wird sein Tank entsprechend gesteuert .
     * @param     keycode
     *
     *
     */
    public void checkKeyCode(int keycode, Map map) {

        //***MOVE TANK WITH KEYBOARD***//
        if (keycode == KEY_TURN_LEFT) {
            tank.rotateLeft();
            tank.getTurret().rotateLeft();
            tank.getTurret().rotateLeft(); //absichtlich doppelt
        }
        if (keycode == KEY_TURN_RIGHT) {
            tank.rotateRight();
            tank.getTurret().rotateRight();
            tank.getTurret().rotateRight(); //absichtlich doppelt
        }
        if (keycode == Key_FORWARD) {
            tank.forward(map);
            SpreadingPeace.changeStatAt(6, 1);
        }
        if (keycode == Key_BACKWARD) {
            tank.backward(map);
            SpreadingPeace.changeStatAt(7, 1);
        }

        //***MOVE TURRET WITH Q AND E***//
        if (keycode == Key_Turret_Left) {
            tank.getTurret().rotateLeft();
        }
        if (keycode == Key_Turret_Right) {
            tank.getTurret().rotateRight();
        }

        //***FIRE FLOWERS WITH SPACEBAR***//
        if (keycode == Key_SHOOT) {
            System.out.println("FIREEEEEE");
            tank.addRounds();

            if(StatScreen.shottypeUsed == 0)
            {
                SpreadingPeace.changeStatAt(9, 1);
            }
            if(StatScreen.shottypeUsed == 1)
            {
                SpreadingPeace.changeStatAt(12, 1);
            }
            if(StatScreen.shottypeUsed == 2)
            {
                SpreadingPeace.changeStatAt(10, 1);
            }
            if(StatScreen.shottypeUsed == 3)
            {
                SpreadingPeace.changeStatAt(11, 1);
            }
            if(StatScreen.shottypeUsed == 4)
            {
                SpreadingPeace.changeStatAt(13, 1);
            }
            if(StatScreen.shottypeUsed == 5)
            {
                SpreadingPeace.changeStatAt(8, 1);
            }
        }

        //***PLACE MINE WITH F***//
        if (keycode == Key_MINE) {
            System.out.println("MINE MINE MINE");
            tank.getMine().placeMine(tank, map);
        }
        //***Change Shottype
        if(keycode == Key_Shottype)
        {
            getTank().setSchusstypNext();
        }

        if(keycode == Key_UpgradeDamage)
        {
            getTank().upgradeFirePower();
        }

        if(keycode == Key_UpgradeHealth)
        {
            getTank().upgradeHealth();
        }

    }

    public void checkButtonCode (Controller controller, int buttonCode, Map map)
    {
        if(buttonCode == buttonForward)
        {
            tank.forward(map);
        }
        if(buttonCode == buttonBackward)
        {
            tank.backward(map);
        }
        if(buttonCode == buttonShoot)
        {
            tank.addRounds();
        }
        if(buttonCode == buttonRotateTurretLeft)
        {
            tank.getTurret().rotateLeft();
        }
        if(buttonCode == buttonRotateTurretRight)
        {
            tank.getTurret().rotateRight();
        }
        if(buttonCode == buttonMine)
        {
            tank.getMine().placeMine(tank, map);
        }
    }

    public void checkPovCode(Controller controller, int povCode, PovDirection value)
    {
        if(value == povUp)
        {
            getTank().setSchusstypNext();
        }

        if(value == povLe)
        {
            getTank().upgradeHealth();
        }

        if(value == povRi)
        {
            getTank().upgradeFirePower();
        }
    }

    public void checkAxisMoved(Controller controller, int axisCode, float value)
    {
        if(axisCode == axisLeftX && value >= 1)
        {
            tank.rotateRight();
            tank.getTurret().rotateRight();
            tank.getTurret().rotateRight();
        }
        if(axisCode == axisLeftX && value <= -1)
        {
            tank.rotateLeft();
            tank.getTurret().rotateLeft();
            tank.getTurret().rotateLeft();
        }
    }

    public void changeColor() {
        if (this.currentColor == 7) {
            this.currentColor = 0;
        } else currentColor++;
    }
}
