package notwowe.combodpsmaxxer;

import notwowe.combodpsmaxxer.algo.DPSolver;
import notwowe.combodpsmaxxer.model.Combo;
import notwowe.combodpsmaxxer.model.Move;
import notwowe.combodpsmaxxer.model.Moveset;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static notwowe.combodpsmaxxer.model.Move.IDLE;

public class Main {
    public static void main(String[] args) {

        Solver solver = new DPSolver();

        Moveset moveset = getTestMoveset();

        System.out.println(moveset.shortestPaths);

        Combo optimalDpsRotation = solver.getMaxDpsComboSequenceFromMovesetInTime(moveset,600);

        System.out.println("Optimal rotation motion value is " + optimalDpsRotation.totalDamage);
        System.out.println("Optimal rotation sequence is " + optimalDpsRotation.moveSequence);
    }

    private static Moveset getTestMoveset() {
        Move chop1 = new Move("Chop 1", 5);
        Move chop2 = new Move("Chop 2", 8);
        Move chop3 = new Move("Chop 3", 12);

        Move slash1 = new Move("Slash 1", 9);
        Move slash2 = new Move("Slash 2", 11);
        Move slash3 = new Move("Slash 3", 18);

        Set<Move> allMoves = Set.of(IDLE, chop1, chop2, chop3, slash1, slash2, slash3);
        
        Map<Move, Map<Move, Integer>> forwardEdges = new HashMap<>();
        forwardEdges.put(IDLE, new HashMap<>(Map.of(chop1, 5, slash1, 8, IDLE, 1)));
        forwardEdges.put(chop1, new HashMap<>(Map.of(chop2, 7, slash1, 8, IDLE, 1)));
        forwardEdges.put(chop2, new HashMap<>(Map.of(chop3, 10, slash1, 8, IDLE, 1)));
        forwardEdges.put(chop3, new HashMap(Map.of(IDLE, 1)));
        forwardEdges.put(slash1, new HashMap<>(Map.of(slash2, 10, chop1, 5, IDLE, 1)));
        forwardEdges.put(slash2, new HashMap<>(Map.of(slash3, 14, chop1, 5, IDLE, 1)));
        forwardEdges.put(slash3, new HashMap<>(Map.of(IDLE, 1)));

        return new Moveset(allMoves, forwardEdges);
    }
}