package notwowe.combodpsmaxxer.model;

import java.util.ArrayList;
import java.util.List;

public class Combo {

    public int totalMotionValue; //< negative values indicate invalid combos
    public List<Move> moveSequence;

    public Combo(int totalMotionValue) {
        this(totalMotionValue, new ArrayList<>());
    }

    public Combo(int totalMotionValue, List<Move> moveSequence) {
        this.totalMotionValue = totalMotionValue;
        this.moveSequence = moveSequence;
    }

    public boolean isInvalid() {
        return this.totalMotionValue < 0;
    }

    @Override
    public String toString() {
        // TODO: make this valid json
        return "{MV: " + totalMotionValue + ", sequence: " + moveSequence.toString() + "}";
    }
}
