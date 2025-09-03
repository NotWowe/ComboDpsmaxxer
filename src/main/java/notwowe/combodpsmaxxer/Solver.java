package notwowe.combodpsmaxxer;

import notwowe.combodpsmaxxer.model.ComboSequence;
import notwowe.combodpsmaxxer.model.Move;

import java.util.List;

public interface Solver {
    ComboSequence getMaxDpsComboSequenceFromMovesetInTime(List<Move> moveset, int timeDuration);
}
