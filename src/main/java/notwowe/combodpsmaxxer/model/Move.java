package notwowe.combodpsmaxxer.model;

public class Move {

    public static final Move IDLE = new Move("idle", 0);

    public final String name;
    public final int damage;

    public Move(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    @Override
    public String toString() {
        return name;
    }
}
