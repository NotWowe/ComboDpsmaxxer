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
import java.util.Set;

public class DPSolver implements Solver {

    public Combo getMaxDpsComboSequenceFromMovesetInTime(Moveset moveset, int timeDuration) {

        Map<Move, Combo[]> dpTable = initDpTable(moveset.moves, timeDuration);

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
            int previousMoveTimeIndex = timeIndex - (currentMove.animationDuration + backEdge.getValue());
            if (previousMoveTimeIndex < 0 || dpTable.get(previousMove)[previousMoveTimeIndex].isInvalid()) {
                continue;
            }

            int damage = dpTable.get(previousMove)[previousMoveTimeIndex].totalDamage + currentMove.damage;
            if (damage > maxDamage) {
                maxDamage = damage;
                bestMoves = dpTable.get(previousMove)[previousMoveTimeIndex].moveSequence;
            }
        }

        dpTable.get(currentMove)[timeIndex].totalDamage = maxDamage;
        if (bestMoves != null) {
            dpTable.get(currentMove)[timeIndex].moveSequence = new ArrayList<>(bestMoves);
            dpTable.get(currentMove)[timeIndex].moveSequence.add(currentMove);
        }
    }

    protected Combo getMaxDamageComboFromDpTable(Map<Move, Combo[]> dpTable, int timeDuration) {

        int maxDamage = Integer.MIN_VALUE;
        Combo maxDamageCombo = null;

        for (Combo[] comboArr : dpTable.values()) {

            Combo finalCombo = comboArr[timeDuration];

            int damage = finalCombo.totalDamage;
            if (damage > maxDamage) {
                maxDamage = damage;
                maxDamageCombo = finalCombo;
            }
        }

        return maxDamageCombo;
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

    private static Map<Move, Combo[]> initDpTable(Set<Move> moveset, int timeDuration) {

        final int dpTimeDimensionSize = timeDuration + 1;

        Map<Move, Combo[]> dpTable = new HashMap<>();

        for (Move move : moveset) {
            Combo[] comboArr = initDefaultInitalizedComboArr(dpTimeDimensionSize);
            flagInvalidComboStates(comboArr, dpTimeDimensionSize, move.animationDuration);
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
    private static void flagInvalidComboStates(Combo[] arr, int size, int animationWindup) {
        for (int i = 0; i < animationWindup && i < size; ++i) {
            arr[i].totalDamage = Integer.MIN_VALUE;
        }
    }
}
