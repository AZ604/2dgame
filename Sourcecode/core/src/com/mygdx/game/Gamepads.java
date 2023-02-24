package com.mygdx.game;

import com.badlogic.gdx.Input;

public class Gamepads
{
    //XBOX A = 0; B = 1; X = 2; Y = 3; LB = 4; RB = 5; Back = 6; Start = 7
    //------------------------------------------------------------------------------------------
    //                                  Player 1
    //------------------------------------------------------------------------------------------
    public static int p1Forward = Input.Keys.W;
    public static int p1Backward = Input.Keys.S;
    public static int p1TurnLeft = Input.Keys.A;
    public static int p1TurnRight = Input.Keys.D;
    public static int p1Shoot = Input.Keys.SPACE;
    public static int p1RotateTurretLeft = Input.Keys.Q;
    public static int p1RotateTurretRight = Input.Keys.E;
    public static int p1Mine = Input.Keys.F;
    public static int p1ShotType = Input.Keys.R;
    public static int p1UpgradeHealth = Input.Keys.J;
    public static int p1UpgradeDamage = Input.Keys.U;

    public static int p1CForward = 2;
    public static int p1CBackward = 0;
    public static int p1CShoot = 1;
    public static int p1CRotateTurretLeft = 4;
    public static int p1CRotateTurretRight = 5;

    //------------------------------------------------------------------------------------------
    //                                  Player 2
    //------------------------------------------------------------------------------------------
    public static int p2Forward = Input.Keys.UP;
    public static int p2Backward = Input.Keys.DOWN;
    public static int p2TurnLeft = Input.Keys.LEFT;
    public static int p2TurnRight = Input.Keys.RIGHT;
    public static int p2Shoot = Input.Keys.ENTER;
    public static int p2RotateTurretLeft = Input.Keys.M;
    public static int p2RotateTurretRight = Input.Keys.N;
    public static int p2Mine = Input.Keys.P;
    public static int p2ShotType = Input.Keys.L;
    public static int p2UpgradeHealth = Input.Keys.O;
    public static int p2UpgradeDamage = Input.Keys.I;

    public static int p2CForward = 2;
    public static int p2CBackward = 0;
    public static int p2CShoot = 1;
    public static int p2CRotateTurretLeft = 4;
    public static int p2CRotateTurretRight = 5;
}
