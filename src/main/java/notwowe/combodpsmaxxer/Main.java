package notwowe.combodpsmaxxer;

import notwowe.combodpsmaxxer.algo.DPSolver;
import notwowe.combodpsmaxxer.model.Combo;
import notwowe.combodpsmaxxer.model.Move;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Solver solver = new DPSolver();

        List<Move> moveset = getTestMoveset();
        Combo optimalDpsRotation = solver.getMaxDpsComboSequenceFromMovesetInTime(moveset,32);

        System.out.println("Optimal rotation motion value is " + optimalDpsRotation.totalMotionValue);
        System.out.println("Optimal rotation sequence is " + optimalDpsRotation.moveSequence);
    }

    private static List<Move> getTestMoveset() {
        Move neutral = new Move("Neutral", 0, 0);

        Move chop1 = new Move("Chop 1", 5, 5);
        Move chop2 = new Move("Chop 2", 8, 7);
        Move chop3 = new Move("Chop 3", 12, 10);

        Move slash1 = new Move("Slash 1", 9, 8);
        Move slash2 = new Move("Slash 2", 11, 10);
        Move slash3 = new Move("Slash 3", 18, 14);

        List<Move> allMoves = List.of(neutral, chop1, chop2, chop3, slash1, slash2, slash3);

        neutral.validFollowUpOptions = List.of(chop1, slash1);

        chop1.validFollowUpOptions = List.of(chop2, slash1, neutral);
        chop2.validFollowUpOptions = List.of(chop3, slash1, neutral);
        chop3.validFollowUpOptions = List.of(neutral);

        slash1.validFollowUpOptions = List.of(slash2, chop1, neutral);
        slash2.validFollowUpOptions = List.of(slash3, chop1, neutral);
        slash3.validFollowUpOptions = List.of(neutral);

        neutral.validPrecursorMoves = allMoves;

        chop1.validPrecursorMoves = List.of(neutral, slash1, slash2);
        chop2.validPrecursorMoves = List.of(chop1);
        chop3.validPrecursorMoves = List.of(chop2);

        slash1.validPrecursorMoves = List.of(neutral, chop1, chop2);
        slash2.validPrecursorMoves = List.of(slash1);
        slash3.validPrecursorMoves = List.of(slash2);

        return allMoves;
    }
}