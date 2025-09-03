package notwowe.combodpsmaxxer.model;

import java.util.List;

public class ComboSequence {
    public int totalMotionValue;
    public List<Move> moveSequence;

    public ComboSequence(int totalMotionValue, List<Move> moveSequence) {
        this.totalMotionValue = totalMotionValue;
        this.moveSequence = moveSequence;
    }
}
