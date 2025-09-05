package notwowe.combodpsmaxxer.model;

import java.util.Map;
import java.util.Set;

public class Moveset {
    public final Set<Move> moves;
    public final Map<Move, Map<Move, Integer>> forwardTransitions;
    public final Map<Move, Map<Move, Integer>> backTransitions;

    public Moveset(Set<Move> moves, Map<Move, Map<Move, Integer>> forwardTransitions, Map<Move, Map<Move, Integer>> backTransitions) {
        this.moves = moves;
        this.forwardTransitions = forwardTransitions;
        this.backTransitions = backTransitions;
    }
}
