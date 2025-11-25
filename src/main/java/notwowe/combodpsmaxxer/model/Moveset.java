package notwowe.combodpsmaxxer.model;

import notwowe.combodpsmaxxer.algo.FloydWarshall;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Moveset {
    public final Set<Move> moves;
    public final Map<Move, Map<Move, Integer>> forwardTransitions;
    public final Map<Move, Map<Move, Integer>> backTransitions;
    public final Map<Move, Map<Move, Integer>> shortestPaths;

    public Moveset(Set<Move> moves, Map<Move, Map<Move, Integer>> forwardTransitions) {
        this.moves = moves;
        this.forwardTransitions = forwardTransitions;

        this.backTransitions = generateReverseEdges(forwardTransitions);
        this.shortestPaths = FloydWarshall.run(moves, forwardTransitions);
    }

    private static Map<Move, Map<Move, Integer>> generateReverseEdges(Map<Move, Map<Move, Integer>> forwardEdges) {

        Map<Move, Map<Move, Integer>> backEdges = new HashMap<>();

        for(Map.Entry<Move, Map<Move, Integer>> forwardEntry : forwardEdges.entrySet()) {
            Move from = forwardEntry.getKey();

            for (Map.Entry<Move, Integer> forwardEdge : forwardEntry.getValue().entrySet()) {
                Move to = forwardEdge.getKey();

                if (backEdges.containsKey(to)) {
                    backEdges.get(to).put(from, forwardEdge.getValue());
                } else {
                    Map<Move, Integer> newMap = new HashMap<>();
                    newMap.put(from, forwardEdge.getValue());
                    backEdges.put(to, newMap);
                }
            }
        }

        return backEdges;
    }
}
