package nu.dario.kiwiland;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LinkDistancePairTests {
    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    public void testCreatingRouteFailsWhenDistanceIsLessThan1(int distance) {
        assertThrows(IllegalArgumentException.class, () -> new LinkDistancePair(new City(), new City(), distance));
    }
}
