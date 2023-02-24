package com.mygdx.game;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.utils.Array;

public class Node {
    public int tileX;
    public int tileY;
    private int index;
    Array<CustomConnection> connections;

    public Node(int i, int j, int index) {
        this.tileX = i;
        this.tileY = j;
        this.index = index;
    }


    public void setConnections(Array<CustomConnection> connections) {
        this.connections = connections;
    }

    public Array<CustomConnection> getConnections() {
        return connections;
    }

    public int getIndex() {
        return index;
    }
}
