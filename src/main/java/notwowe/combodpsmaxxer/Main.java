package notwowe.combodpsmaxxer;

import notwowe.combodpsmaxxer.algo.DPSolver;
import notwowe.combodpsmaxxer.model.Combo;
import notwowe.combodpsmaxxer.model.Move;
import notwowe.combodpsmaxxer.model.Moveset;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static notwowe.combodpsmaxxer.model.Move.NEUTRAL;

public class Main {
    public static void main(String[] args) {

        Solver solver = new DPSolver();

        Moveset moveset = getTestMoveset();
        Combo optimalDpsRotation = solver.getMaxDpsComboSequenceFromMovesetInTime(moveset,32);

        System.out.println("Optimal rotation motion value is " + optimalDpsRotation.totalDamage);
        System.out.println("Optimal rotation sequence is " + optimalDpsRotation.moveSequence);
    }

    private static Moveset getTestMoveset() {
        Move chop1 = new Move("Chop 1", 5, 5);
        Move chop2 = new Move("Chop 2", 8, 7);
        Move chop3 = new Move("Chop 3", 12, 10);

        Move slash1 = new Move("Slash 1", 9, 8);
        Move slash2 = new Move("Slash 2", 11, 10);
        Move slash3 = new Move("Slash 3", 18, 14);

        Set<Move> allMoves = Set.of(NEUTRAL, chop1, chop2, chop3, slash1, slash2, slash3);
        
        Map<Move, Map<Move, Integer>> forwardEdges = new HashMap<>();
        forwardEdges.put(NEUTRAL, new HashMap<>(Map.of(chop1, 0, slash1, 0)));
        forwardEdges.put(chop1, new HashMap<>(Map.of(chop2, 0, slash1, 0)));
        forwardEdges.put(chop2, new HashMap<>(Map.of(chop3, 0, slash1, 0)));
        forwardEdges.put(slash1, new HashMap<>(Map.of(slash2, 0, chop1, 0)));
        forwardEdges.put(slash2, new HashMap<>(Map.of(slash3, 0, chop1, 0)));

        connectNeutralToAllNodesAsForwardEdges(forwardEdges, NEUTRAL, allMoves);
        Map<Move, Map<Move, Integer>> backEdges = generateReverseEdges(forwardEdges);


        return new Moveset(allMoves, forwardEdges, backEdges);
    }

    private static void connectNeutralToAllNodesAsForwardEdges(Map<Move, Map<Move, Integer>> forwardEdges, Move neutral, Set<Move> allMoves) {

        Map<Move, Integer> forwardEdgesToAllNodesFromNeutral = new HashMap<>();
        for(Move move : allMoves) {
            forwardEdgesToAllNodesFromNeutral.put(move, 0);
        }

        if (forwardEdges.containsKey(neutral)) {
            Map<Move, Integer> existingMap = forwardEdges.get(neutral);
            existingMap.putAll(forwardEdgesToAllNodesFromNeutral);
        } else {
            forwardEdges.put(neutral, forwardEdgesToAllNodesFromNeutral);
        }
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