package org.example.resolver;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.Labyrinth;
import org.example.exceptions.IncorrectPositionException;

@RequiredArgsConstructor
@Getter
public class OneWayLabyrinthEscaper implements LabyrinthEscaper<String> {

    private static final int[] row = {-1, 0, 0, 1};
    private static final int[] col = {0, -1, 1, 0};
    private final Queue<Node> nodes = new ArrayDeque<>();
    private final Labyrinth labyrinth;

    private final char wall;
    private final char floor;
    private final char escapePath;
    private Node smallestNode;

    @Override
    public String getExit() {
        int[][] convertedLabyrinth = convertTheLabyrinth();
        if (theMinimumRange(convertedLabyrinth, labyrinth.getStartY(), labyrinth.getStartX(),
            labyrinth.getExitY(), labyrinth.getExitX()) == -1) {
            throw new IncorrectPositionException("There is no way to the exit");
        }
        return concatLabyrinthLayers(createLabyrinthLayers());
    }

    private String concatLabyrinthLayers(String[] layers) {
        StringBuilder mappedLabyrinth = new StringBuilder();
        for (String layout : layers) {
            mappedLabyrinth.append(layout);
        }
        return mappedLabyrinth.toString();
    }

    private String[] createLabyrinthLayers() {
        String[] waysInLabyrinth = labyrinth.getLabyrinthMap()
            .toArray(new String[0]);
        while (smallestNode.prev != null) {
            char[] old = waysInLabyrinth[smallestNode.posX].toCharArray();
            old[smallestNode.posY] = escapePath;
            String pointed = String.valueOf(old);
            waysInLabyrinth[smallestNode.posX] = pointed;
            smallestNode = smallestNode.prev;
            if (smallestNode.prev == null) {
                old = waysInLabyrinth[smallestNode.posX].toCharArray();
                old[smallestNode.posY] = escapePath;
                pointed = String.valueOf(old);
                waysInLabyrinth[smallestNode.posX] = pointed;
            }
        }
        return waysInLabyrinth;
    }

    private int[][] convertTheLabyrinth() {
        int[][] convertedLabyrinth = new int[labyrinth.getLabyrinthMap()
            .size()][labyrinth.getLabyrinthMap().get(0).length()];

        for (int i = 0; i < labyrinth.getLabyrinthMap().size(); i++) {
            for (int j = 0; j < labyrinth.getLabyrinthMap().get(0).length(); j++) {
                if (labyrinth.getLabyrinthMap().get(i).charAt(j) == floor) {
                    convertedLabyrinth[i][j] = 1;
                } else {
                    convertedLabyrinth[i][j] = 0;
                }
            }
        }
        return convertedLabyrinth;
    }

    private int theMinimumRange(int[][] matrix, int startX, int startY, int exitX,
        int exitY) {
        nodes.add(new Node(null, startX, startY, 0));
        Optional<Integer> minDist = visitNodes(matrix, startX, startY, exitX, exitY);
        return minDist.orElse(-1);
    }

    private Optional<Integer> visitNodes(int[][] matrix, int startX, int startY, int exitX,
        int exitY) {
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        visited[startX][startY] = true;
        Optional<Integer> minDist = Optional.empty();
        while (!nodes.isEmpty()) {
            Node node = nodes.poll();
            startX = node.posX;
            startY = node.posY;
            int dist = node.weight;
            if (startX == exitX && startY == exitY) {
                minDist = Optional.of(dist);
                smallestNode = node;
                break;
            }
            for (int k = 0; k < 4; k++) {
                if (correctMatrix(matrix, visited, startX + row[k], startY + col[k])) {
                    visited[startX + row[k]][startY + col[k]] = true;
                    nodes.add(new Node(node, startX + row[k], startY + col[k], dist + 1));
                }
            }
        }
        return minDist;
    }

    private boolean correctMatrix(int[][] matrix, boolean[][] visited, int row, int col) {
        return (row >= 0) && (row < matrix.length) && (col >= 0) && (col < matrix[0].length)
            && matrix[row][col] == 1 && !visited[row][col];
    }


    @AllArgsConstructor
    protected static class Node {

        Node prev;
        int posX;
        int posY;
        int weight;
    }
}
