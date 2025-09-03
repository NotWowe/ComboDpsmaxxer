package notwowe.combodpsmaxxer.model;

import java.util.List;

public class Move {
    public String name;
    public int motionValue;
    public int animationDuration;

    public List<Move> validFollowUpOptions;
    public List<Move> validPrecursorMoves;

    public Move(String name, int motionValue, int animationDuration) {
        this.name = name;
        this.motionValue = motionValue;
        this.animationDuration = animationDuration;
    }

    @Override
    public String toString() {
        return name;
    }
}
