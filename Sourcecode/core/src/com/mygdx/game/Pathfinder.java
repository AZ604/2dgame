package com.mygdx.game;

import com.badlogic.gdx.ai.pfa.*;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Pathfinder {
    private final TiledMap map;
    private final boolean[][] blocked;
    private IndexedAStarPathFinder pathFinder;
    private IndexedGraph graph;
    private Heuristic<Node> heuristic;
    private Node[][] allNodes;

    public Pathfinder(TiledMap map, boolean[][] blocked) {
        this.map = map;
        this.blocked = blocked;
        init();
    }

    public List<Node> findPath(int x, int y, int x1, int y1) {
        return findPath(allNodes[x][y], allNodes[x1][y1]);
    }

    public List<Node> findPath(CustomPoint start, CustomPoint end) {
        return findPath(allNodes[start.x][start.y], allNodes[end.x][end.y]);
    }

    private List<Node> findPath(Node startNode, Node endNode) {
        //System.out.println("Finding path between: " + startNode + ", " + endNode);
        GraphPath resultPath = new DefaultGraphPath();
        PathFinderRequest request = new PathFinderRequest(startNode, endNode, heuristic, resultPath);
        request.statusChanged = true;

        boolean success = pathFinder.search(request, 1000 * 1000 * 1000);

        //System.out.println("Success: " + success);

        List<Node> result = new ArrayList<>();

        Iterator iter = resultPath.iterator();
        while (iter.hasNext()) {

            Node node = (Node) iter.next();
            result.add(node);
        }
        return result;
    }

    private void init() {
        initAllNodes();
        initGraph();
        initHeutristic();
        pathFinder = new IndexedAStarPathFinder(graph);
    }

    private void initAllNodes() {

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        int width = layer.getWidth();
        int height = layer.getHeight();
        int index = 0;
        allNodes = new Node[width][height];

        // create nodes
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Node node = new Node(i, j, index++);
                allNodes[i][j] = node;
            }
        }
        //create node connections
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //set up corners
                // top left corner
                if (i == 0 && j == height - 1) {
                    Node source = allNodes[i][j];
                    Array<CustomConnection> connections = new Array<>();
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i + 1, j));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i, j - 1));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i + 1, j - 1));
                    source.setConnections(connections);
                    continue;
                }
                // top right corner
                if (i == width - 1 && j == height - 1) {
                    Node source = allNodes[i][j];
                    Array<CustomConnection> connections = new Array<>();
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i - 1, j));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i, j - 1));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i - 1, j - 1));
                    source.setConnections(connections);
                    continue;
                }
                //bottom left corner
                if (i == 0 && j == 0) {
                    Node source = allNodes[i][j];
                    Array<CustomConnection> connections = new Array<>();
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i + 1, j));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i, j + 1));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i + 1, j + 1));
                    source.setConnections(connections);
                    continue;
                }
                //bottom right corner
                if (i == width - 1 && j == 0) {
                    Node source = allNodes[i][j];
                    Array<CustomConnection> connections = new Array<>();
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i - 1, j));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i, j + 1));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i - 1, j + 1));
                    source.setConnections(connections);
                    continue;
                }
                // set up border lines
                // top border
                if (j == height - 1) {
                    Node source = allNodes[i][j];
                    Array<CustomConnection> connections = new Array<>();
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i - 1, j));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i - 1, j - 1));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i + 1, j));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i + 1, j - 1));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i, j - 1));
                    source.setConnections(connections);
                    continue;
                }
                // bottom border
                if (j == 0) {
                    Node source = allNodes[i][j];
                    Array<CustomConnection> connections = new Array<>();
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i - 1, j));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i - 1, j + 1));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i + 1, j));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i + 1, j + 1));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i, j + 1));
                    source.setConnections(connections);
                    continue;
                }
                // left border
                if (i == 0) {
                    Node source = allNodes[i][j];
                    Array<CustomConnection> connections = new Array<>();
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i, j - 1));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i + 1, j - 1));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i, j + 1));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i + 1, j + 1));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i + 1, j + 1));
                    source.setConnections(connections);
                    continue;
                }
                // right border
                if (i == width - 1) {
                    Node source = allNodes[i][j];
                    Array<CustomConnection> connections = new Array<>();
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i, j - 1));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i - 1, j - 1));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i, j + 1));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i - 1, j + 1));
                    addConnection(connections, new CustomPoint(i, j), new CustomPoint(i - 1, j));
                    source.setConnections(connections);
                    continue;
                }
                // set up middle nodes
                Node source = allNodes[i][j];
                Array<CustomConnection> connections = new Array<>();
                addConnection(connections, new CustomPoint(i, j), new CustomPoint(i - 1, j - 1));
                addConnection(connections, new CustomPoint(i, j), new CustomPoint(i - 1, j));
                addConnection(connections, new CustomPoint(i, j), new CustomPoint(i - 1, j + 1));
                addConnection(connections, new CustomPoint(i, j), new CustomPoint(i, j + 1));
                addConnection(connections, new CustomPoint(i, j), new CustomPoint(i, j - 1));
                addConnection(connections, new CustomPoint(i, j), new CustomPoint(i + 1, j + 1));
                addConnection(connections, new CustomPoint(i, j), new CustomPoint(i + 1, j - 1));
                addConnection(connections, new CustomPoint(i, j), new CustomPoint(i + 1, j));
                source.setConnections(connections);
            }


        }

    }

    private void initGraph() {
        graph = new IndexedGraph() {
            @Override
            public int getIndex(Object node) {
                return ((Node) node).getIndex();
            }

            @Override
            public int getNodeCount() {
                TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
                return layer.getWidth() * layer.getHeight();
            }

            @Override
            public Array getConnections(Object n) {
                if (n.getClass().isAssignableFrom(Node.class)) {
                    return ((Node) n).getConnections();
                }
                return null;
            }
        };
    }

    private void initHeutristic() {
        heuristic = new Heuristic<Node>() {
            @Override
            public float estimate(Node startNode, Node endNode) {
                return Math.max(Math.abs(startNode.tileX - endNode.tileX), Math.abs(startNode.tileY - endNode.tileY));
            }
        };
    }

    private void addConnection(Array<CustomConnection> connections, CustomPoint from, CustomPoint to) {
        float cost = 80;
        if ((from.x == to.x && from.y != to.y) || (from.x != to.x && from.y == to.y)) {
            cost = 1;
        }
        Node fromNode = allNodes[from.x][from.y];
        Node toNode = allNodes[to.x][to.y];
        if (!blocked[to.x][to.y]) {
            connections.add(new CustomConnection(fromNode, toNode, cost));
        }
    }


    public void removeConnection(int fromx, int fromy, int tox, int toy) {

        Array<CustomConnection> allConnections = allNodes[fromx][fromy].getConnections();

        for (int i = 0; i < allConnections.size; i++) {
            if (allConnections.get(i).getToNode().tileX == tox && allConnections.get(i).getToNode().tileY == toy) {
                if (allConnections.get(i) != null) allConnections.removeIndex(i);
                break;
            }

        }
    }

    public void addConnection(int fromx, int fromy, int tox, int toy) {


        for (int i = 0; i < allNodes[fromx][fromy].getConnections().size; i++) {
            allNodes[fromx][fromy].getConnections().add(new CustomConnection(new Node(tox, toy, allNodes[tox][toy].getIndex()), new Node(tox, toy, allNodes[tox][toy].getIndex())));

        }


    }



}
