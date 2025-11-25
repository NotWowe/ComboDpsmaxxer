package notwowe.combodpsmaxxer.algo;

import notwowe.combodpsmaxxer.model.Move;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FloydWarshallTest {
    @Test
    public void test_weCalculateTheRightShortestPathMappings() {
        /// GIVEN a sample map
        Move move1 = new Move("1", 0);
        Move move2 = new Move("2", 0);
        Move move3 = new Move("3", 0);
        Move move4 = new Move("4", 0);
        Move move5 = new Move("5", 0);

        Set<Move> allMoves = Set.of(move1, move2, move3, move4, move5);

        Map<Move, Map<Move, Integer>> sampleInput = Map.of(
            move1, Map.of(
                    move1, 0,
                    move2, 4,
                    //move3, null,
                    move4, 5
                    //move5, null
            ),
            move2, Map.of(
                    //move1, null,
                    move2, 0,
                    move3, 1,
                    //move4, null,
                    move5, 6
            ),
            move3, Map.of(
                    move1, 2,
                    //move2, null,
                    move3, 0,
                    move4, 3
                    //move5, null
            ),
            move4, Map.of(
                    //move1, null,
                    //move2, null,
                    move3, 1,
                    move4, 0,
                    move5, 2
            ),
            move5, Map.of(
                    move1, 1,
                    //move2, null,
                    //move3, null,
                    move4, 4,
                    move5, 0
            )
        );

        /// WHEN we run the algorithm
        Map<Move, Map<Move, Integer>> result = FloydWarshall.run(allMoves, sampleInput);

        /// THEN we get back the expected results
        Map<Move, Map<Move, Integer>> expectedOutput = Map.of(
            move1, Map.of(
                move1, 0,
                move2, 4,
                move3, 5,
                move4, 5,
                move5, 7
            ),
            move2, Map.of(
                move1, 3,
                move2, 0,
                move3, 1,
                move4, 4,
                move5, 6
            ),
            move3, Map.of(
                move1, 2,
                move2, 6,
                move3, 0,
                move4, 3,
                move5, 5
            ),
            move4, Map.of(
                move1, 3,
                move2, 7,
                move3, 1,
                move4, 0,
                move5, 2
            ),
            move5, Map.of(
                move1, 1,
                move2, 5,
                move3, 5,
                move4, 4,
                move5, 0
            )
        );

        assertEquals(expectedOutput, result);
    }
}
