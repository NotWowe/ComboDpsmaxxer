package notwowe.combodpsmaxxer.algo;

import notwowe.combodpsmaxxer.model.Move;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class FloydWarshall {

    public static Map<Move, Map<Move, Integer>> run(Set<Move> moves, Map<Move, Map<Move, Integer>> adjacencyMatrix) {
        Map<Move, Map<Move, Integer>> workingMatrix = makeCopyOf(adjacencyMatrix);

        inMemoryFloydWarshall(moves, workingMatrix);
        return workingMatrix;
    }

    static void inMemoryFloydWarshall(Set<Move> moves, Map<Move, Map<Move, Integer>> adjacencyMatrix) {
        for(Move k : moves) {
            for(Move i : moves) {
                for(Move j : moves) {
                    if(adjacencyMatrix.get(i).get(k) != null && adjacencyMatrix.get(k).get(j) != null) {
                        adjacencyMatrix.get(i).put(j, Math.min(getValAtCoordOrVeryLargeInt(adjacencyMatrix, i, j), getValAtCoordOrVeryLargeInt(adjacencyMatrix, i, k) + getValAtCoordOrVeryLargeInt(adjacencyMatrix, k, j)));
                    }
                }
            }
        }
    }

    private static int getValAtCoordOrVeryLargeInt(Map<Move, Map<Move, Integer>> adjacencyMatrix, Move i, Move j) {
        int INF = 100000;
        return Optional.ofNullable(adjacencyMatrix.get(i))
            .map(row -> row.get(j))
            .orElse(INF);
    }

    private static Map<Move, Map<Move, Integer>> makeCopyOf(Map<Move, Map<Move, Integer>> matrix) {
        Map<Move, Map<Move, Integer>> newParentMap = new HashMap<>();

        for(Map.Entry<Move, Map<Move, Integer>> entry : matrix.entrySet()) {

            Move parentKey = entry.getKey();
            Map<Move, Integer> adjacencyList = entry.getValue();

            Map<Move, Integer> newAdjacencyList = new HashMap<>();

            for (Map.Entry<Move, Integer> innerEntry : adjacencyList.entrySet()) {
                newAdjacencyList.put(innerEntry.getKey(), Integer.valueOf(innerEntry.getValue()));
            }

            newParentMap.put(parentKey, newAdjacencyList);
        }

        return newParentMap;
    }
}
