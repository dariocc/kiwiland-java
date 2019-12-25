package nu.dario.kiwiland;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MemoryGraphTests {
    @Test
    public void testBuildingAndRetrievingRoutes() {
        City foo = new City();
        City bar = new City();
        City fee = new City();

        Graph graph = new MemoryGraph.Builder()
                .connect(foo, bar, 3)
                .connect(bar, foo, 4)
                .connect(bar, fee, 1)
                .build();

        Set<Link> expected = new HashSet<>();
        expected.add(new Link(foo, bar));
        expected.add(new Link(bar, foo));
        expected.add(new Link(bar, fee));

        Set<Link> actual = graph.linkDistancePairs().map(it -> it.getLink()).collect(Collectors.toSet());
        assertEquals(new HashSet<Link>(expected), actual);
    }

    @Test
    public void testImmutability() {
        MemoryGraph.Builder builder = new MemoryGraph.Builder();
        Graph graph = builder.build();

        builder.connect(new City(), new City(), 1);
        assertEquals(0, graph.linkDistancePairs().count());
    }

    @Test
    public void testDefiningUpdatesRatherThanAddingWhenTheRouteAlreadyExist() {
        City foo = new City();
        City bar = new City();

        Graph graph = new MemoryGraph.Builder()
                .connect(foo, bar, 3)
                .connect(foo, bar, 4)
                .build();

        assertAll(
                () -> assertEquals(1, graph.linkDistancePairs().count()),
                () -> assertEquals(4, graph.linkDistancePairs().iterator().next().getDistance())
        );
    }

    @Test
    public void testTraversing() {
        City foo = new City();
        City bar = new City();
        City fee = new City();
        City bee = new City();

        HashMap<City, String> names = new HashMap<>();
        names.put(foo, "foo");
        names.put(fee, "fee");
        names.put(bar, "bar");
        names.put(bee, "bee");
        names.put(fee, "fee");

        Graph graph = new MemoryGraph.Builder()
                .connect(foo, bar, 3)
                .connect(bar, foo, 4)
                .connect(bar, fee, 1)
                .connect(bar, bee, 4)
                .connect(bee, foo, 7)
                .connect(bee, fee, 2)
                .build();

        List<String> paths = graph.traverse(bar, Guard.acyclic())
                .map(it -> it.getLinks().stream().map(r -> names.get(r.getEnd()))
                        .reduce("", (acc, name) -> String.format("%s -> %s", acc, name), String::concat))
                .collect(Collectors.toList());

        assertAll(
                () -> assertTrue(paths.contains(" -> bee -> foo -> bar -> fee")),
                () -> assertTrue(paths.contains(" -> bee -> foo -> bar -> foo")),
                () -> assertTrue(paths.contains(" -> bee -> fee")),
                () -> assertTrue(paths.contains(" -> foo -> bar -> bee -> fee")),
                () -> assertTrue(paths.contains(" -> foo -> bar -> bee -> foo")),
                () -> assertTrue(paths.contains(" -> foo -> bar -> fee")),
                () -> assertTrue(paths.contains(" -> fee"))
        );
    }
}

