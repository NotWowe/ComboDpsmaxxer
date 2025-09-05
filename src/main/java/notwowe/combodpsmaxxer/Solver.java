package notwowe.combodpsmaxxer;

import notwowe.combodpsmaxxer.model.Combo;
import notwowe.combodpsmaxxer.model.Moveset;

public interface Solver {
    Combo getMaxDpsComboSequenceFromMovesetInTime(Moveset moveset, int timeDuration);
}
