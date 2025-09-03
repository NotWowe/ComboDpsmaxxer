package notwowe.combodpsmaxxer;

import notwowe.combodpsmaxxer.model.Combo;
import notwowe.combodpsmaxxer.model.Move;

import java.util.List;

public interface Solver {
    Combo getMaxDpsComboSequenceFromMovesetInTime(List<Move> moveset, int timeDuration);
}
