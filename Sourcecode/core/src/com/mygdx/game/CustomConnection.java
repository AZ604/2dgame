package com.mygdx.game;

import com.badlogic.gdx.ai.pfa.Connection;

public class CustomConnection implements Connection {
    private final Node fromNode;
    private final Node toNode;
    private final float cost;

    public CustomConnection(Node fromNode, Node toNode) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.cost = 1;
    }

    public CustomConnection(Node fromNode, Node toNode, float cost) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.cost = cost;
    }


    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public Node getFromNode() {
        return fromNode;
    }

    @Override
    public Node getToNode() {
        return toNode;
    }

}
