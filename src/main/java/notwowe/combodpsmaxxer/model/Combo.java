package notwowe.combodpsmaxxer.model;

import java.util.ArrayList;
import java.util.List;

public class Combo {

    public int totalDamage; //< negative values indicate invalid combos
    public List<Move> moveSequence;

    public Combo(int totalDamage) {
        this(totalDamage, new ArrayList<>());
    }

    public Combo(int totalDamage, List<Move> moveSequence) {
        this.totalDamage = totalDamage;
        this.moveSequence = moveSequence;
    }

    public boolean isInvalid() {
        return this.totalDamage < 0;
    }

    @Override
    public String toString() {
        // TODO: make this valid json
        return "{damage: " + totalDamage + ", sequence: " + moveSequence.toString() + "}";
    }
}
