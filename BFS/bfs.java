package BFS;

public class bfs {
    public static void main(String[] args) {
        int count = 0;
        GraphNode node = new GraphNode(100);
        GraphNode node2 = new GraphNode(40);
        GraphNode node3 = new GraphNode(10);
        node.Left = node2;
        node2.Bottom = node3;
        GraphNode nodePoint = node;
        int toFind = 10;
        while (true) {
            nodePoint = nodePoint.bestPath(toFind);
            count++;
            if (nodePoint.value == toFind) {
                break;
            }
        }
        System.out.printf("Count is %s\n", count);
    }
}

public class GraphNode {
    public GraphNode Top;
    public GraphNode Bottom;
    public GraphNode Left;
    public GraphNode Right;
    public int value;

    GraphNode(int _value) {
        value = _value;
    }

    public GraphNode bestPath(int toFind) {
        if (Top != null && Top.value == toFind) {
            return Top;
        }
        if (Bottom != null && Bottom.value == toFind) {
            return Bottom;
        }
        if (Left != null && Left.value == toFind) {
            return Left;
        }
        if (Right != null && Right.value == toFind) {
            return Right;
        }

        int min = -1000;
        GraphNode minNode = null;
        if (Top != null && Top.value < min) {
            min = Top.value;
            minNode = Top;
        }
        if (Bottom != null && Bottom.value < min) {
            min = Bottom.value;
            minNode = Bottom;
        }
        if (Left != null && Left.value < min) {
            min = Left.value;
            minNode = Left;
        }
        if (Right != null && Right.value < min) {
            min = Right.value;
            minNode = Right;
        }
        return minNode;
    }
}
