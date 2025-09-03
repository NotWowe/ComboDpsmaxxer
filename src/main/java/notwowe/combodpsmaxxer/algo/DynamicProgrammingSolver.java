package notwowe.combodpsmaxxer.algo;

import notwowe.combodpsmaxxer.Solver;
import notwowe.combodpsmaxxer.model.ComboSequence;
import notwowe.combodpsmaxxer.model.Move;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicProgrammingSolver implements Solver {

    @Override
    public ComboSequence getMaxDpsComboSequenceFromMovesetInTime(List<Move> moveset, int timeDuration) {

        Map<Move, int[]> dpTable = initDpTable(moveset, timeDuration);

        for (int i = 0; i <= timeDuration; ++i) {
            for (Move move : moveset) {
                solveRecurrenceRelation(dpTable, move, i);
            }
        }

        int maxDamage = getMaxDamageFromDpTable(dpTable, timeDuration);

        System.out.println("dp table:" + printDpTable(dpTable));

        List<Move> getOptimalCombo = backtrackDpTable(dpTable, timeDuration);

        return new ComboSequence(maxDamage, getOptimalCombo);
    }

    // Recurrence relation: Opt(m, t) = optimal damage done by completing move m at time t
    //  Opt(m, t) = max({Opt(n, t-m_t) | n in m.validPrecursorMoves})
    protected void solveRecurrenceRelation(Map<Move, int[]> dpTable, Move currentMove, int timeIndex) {

        int maxDamage = -1;

        for (Move previousMove : currentMove.validPrecursorMoves) {
            int previousMoveTimeIndex = timeIndex - currentMove.animationDuration;
            if (previousMoveTimeIndex < 0 || dpTable.get(previousMove)[previousMoveTimeIndex] == -1) {
                continue;
            }

            maxDamage = Math.max(maxDamage, dpTable.get(previousMove)[previousMoveTimeIndex] + currentMove.motionValue);
        }

        dpTable.get(currentMove)[timeIndex] = maxDamage;
    }

    protected int getMaxDamageFromDpTable(Map<Move, int[]> dpTable, int timeDuration) {

        int maxDamage = 0;

        for (Map.Entry<Move, int[]> entry : dpTable.entrySet()) {
            maxDamage = Math.max(maxDamage, entry.getValue()[timeDuration ]);
        }

        return maxDamage;
    }

    protected List<Move> backtrackDpTable(Map<Move, int[]> dpTable, int timeDuration) {
        // TODO
        return null;
    }

    private static String printDpTable(Map<Move, int[]> dpTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");

        for(Map.Entry<Move, int[]> entry : dpTable.entrySet()) {
            sb.append("{")
                .append(entry.getKey().name)
                .append(": ")
                .append(Arrays.toString(entry.getValue()))
                .append("},\n");
        }
        sb.append("}");

        return sb.toString();
    }

    // note: by default, `new int[]` default initializes all values to 0 so we're good
    private static Map<Move, int[]> initDpTable(List<Move> moveset, int timeDuration) {
        Map<Move, int[]> dpTable = new HashMap<>();

        for (Move move : moveset) {
            int[] timeArr = new int[timeDuration + 1];

            for (int i = 0; i < move.animationDuration && i <= timeDuration; ++i) {
                timeArr[i] = -1;
            }

            dpTable.put(move, timeArr);
        }

        return dpTable;
    }
}
