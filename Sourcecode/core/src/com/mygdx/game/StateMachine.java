package com.mygdx.game;

public class StateMachine {
    private State currentState;
    private float timeCount;
    private float worldTimer = 300;

    public StateMachine() {
        this.currentState = State.IDLE;
    }

    public void updateTime(float dt) {
        this.timeCount += dt;
        if (this.worldTimer == 0) this.worldTimer = 300;
        if (this.timeCount >= dt) {
            this.worldTimer--;
            this.timeCount = 0;

        }

    }

    public void updateState() {
        if (this.worldTimer % 300 == 0) {
            this.currentState = State.SHOOTING;
        } else if (this.worldTimer % 50 == 0) {
            this.currentState = State.MOVING;
        } else this.currentState = State.IDLE;
    }

    public State getState() {
        return currentState;
    }
}
