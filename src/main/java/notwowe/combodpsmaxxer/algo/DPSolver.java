package notwowe.combodpsmaxxer.algo;

import notwowe.combodpsmaxxer.Solver;
import notwowe.combodpsmaxxer.model.Combo;
import notwowe.combodpsmaxxer.model.Move;
import notwowe.combodpsmaxxer.model.Moveset;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DPSolver implements Solver {

    public Combo getMaxDpsComboSequenceFromMovesetInTime(Moveset moveset, int timeDuration) {

        Map<Move, Combo[]> dpTable = initDpTable(moveset, Move.IDLE, timeDuration);

        System.out.println("init dp = " + printDpTable(dpTable));

        for (int i = 0; i <= timeDuration; ++i) {
            for (Move move : moveset.moves) {
                Map<Move, Integer> backEdges = moveset.backTransitions.get(move);
                solveRecurrenceRelation(dpTable, move, backEdges, i);
            }
        }

        Combo maxDamageCombo = getMaxDamageComboFromDpTable(dpTable, timeDuration);

        System.out.println("dp table:" + printDpTable(dpTable));

        return maxDamageCombo;
    }

    // Recurrence relation: Opt(m, t) = optimal damage done by completing move m at time t
    //  Opt(m, t) = max({Opt(n, t-m_t) | n in m.validPrecursorMoves})
    protected void solveRecurrenceRelation(Map<Move, Combo[]> dpTable, Move currentMove, Map<Move, Integer> backEdges, int timeIndex) {

        int maxDamage = Integer.MIN_VALUE;
        List<Move> bestMoves = null;

        for (Map.Entry<Move, Integer> backEdge : backEdges.entrySet()) {
            Move previousMove = backEdge.getKey();
            Integer time = backEdge.getValue();
            int previousMoveTimeIndex = timeIndex - time;
            if (previousMoveTimeIndex < 0 || dpTable.get(previousMove)[previousMoveTimeIndex].isInvalid()) {
                continue;
            }

            int damage = dpTable.get(previousMove)[previousMoveTimeIndex].totalDamage + currentMove.damage;
            if (damage > maxDamage) {
                maxDamage = damage;
                bestMoves = dpTable.get(previousMove)[previousMoveTimeIndex].moveSequence;
            }
        }

        dpTable.get(currentMove)[timeIndex].totalDamage = Math.max(dpTable.get(currentMove)[timeIndex].totalDamage, maxDamage);
        if (bestMoves != null) {
            dpTable.get(currentMove)[timeIndex].moveSequence = new ArrayList<>(bestMoves);
            dpTable.get(currentMove)[timeIndex].moveSequence.add(currentMove);
        }
    }

    protected Combo getMaxDamageComboFromDpTable(Map<Move, Combo[]> dpTable, int timeDuration) {

        Combo bestCombo = new Combo(Integer.MIN_VALUE);

        for (Combo[] comboArr : dpTable.values()) {

            Combo terminalCombo = comboArr[timeDuration];

            if (terminalCombo.totalDamage > bestCombo.totalDamage) {
                bestCombo = terminalCombo;
            }
        }

        return bestCombo;
    }

    private static String printDpTable(Map<Move, Combo[]> dpTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        for(Map.Entry<Move, Combo[]> entry : dpTable.entrySet()) {
            sb.append("{")
                .append(entry.getKey().name)
                .append(": ")
                .append(Arrays.toString(entry.getValue()))
                .append("},\n");
        }
        sb.append("}");

        return sb.toString();
    }

    private static Map<Move, Combo[]> initDpTable(Moveset moveset, Move initialMove, int timeDuration) {

        final int dpTimeDimensionSize = timeDuration + 1;

        Map<Move, Combo[]> dpTable = new HashMap<>();

        for (Move move : moveset.moves) {
            Combo[] comboArr = initDefaultInitalizedComboArr(dpTimeDimensionSize);

            if (move != initialMove) {
                int shortestPathFromInit = moveset.shortestPaths.get(initialMove).get(move);

                System.out.println("Shortest path from " + initialMove.name + " to " + move.name + " is " + shortestPathFromInit);
                flagInvalidComboStates(comboArr, dpTimeDimensionSize, shortestPathFromInit);
            }
            dpTable.put(move, comboArr);
        }

        return dpTable;
    }

    private static Combo[] initDefaultInitalizedComboArr(int size) {
        Combo[] comboArr = new Combo[size];
        for (int i = 0; i < size; ++i) {
            comboArr[i] = new Combo(0);
        }

        return comboArr;
    }

    // Marks position that are impossible based on frame data as invalid by setting the Combo's total damage to a
    // negative value
    private static void flagInvalidComboStates(Combo[] arr, int size, int shortestTimeFromSource) {
        for (int i = 0; i < shortestTimeFromSource && i < size; ++i) {
            arr[i].totalDamage = Integer.MIN_VALUE;
        }
    }
}
