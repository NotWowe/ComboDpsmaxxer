package notwowe.combodpsmaxxer.model;

public class Move {

    public static final Move NEUTRAL = new Move("neutral", 0, 0);

    public final String name;
    public final int damage;
    public final int animationDuration;

    public Move(String name, int damage, int animationDuration) {
        this.name = name;
        this.damage = damage;
        this.animationDuration = animationDuration;
    }

    @Override
    public String toString() {
        return name;
    }
}
