package nu.dario.kiwiland;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LinkTests {
    @Test
    public void testCreatingRouteFailsWhenOriginAndEndAreTheSame() {
        City foo = new City();
        assertThrows(IllegalArgumentException.class, () -> new Link(foo, foo));
    }
}
