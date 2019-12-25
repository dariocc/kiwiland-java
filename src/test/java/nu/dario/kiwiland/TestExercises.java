package nu.dario.kiwiland;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Cities {
    public final static City A = new City();
    public final static City B = new City();
    public final static City C = new City();
    public final static City D = new City();
    public final static City E = new City();
}

@Tag("Functional")
public class TestExercises {
    private static final Graph GRAPH;

    static {
        GRAPH = new MemoryGraph.Builder()
                .connect(Cities.A, Cities.B, 5)
                .connect(Cities.B, Cities.C, 4)
                .connect(Cities.C, Cities.D, 8)
                .connect(Cities.D, Cities.C, 8)
                .connect(Cities.D, Cities.E, 6)
                .connect(Cities.A, Cities.D, 5)
                .connect(Cities.C, Cities.E, 2)
                .connect(Cities.E, Cities.B, 3)
                .connect(Cities.A, Cities.E, 7)
                .build();
    }

    private static Stream<Arguments> exercises1to5Cases() {
        return Stream.of(
                Arguments.of(Cities.A, List.of(Cities.B, Cities.C), Optional.of(9)),
                Arguments.of(Cities.A, List.of(Cities.D), Optional.of(5)),
                Arguments.of(Cities.A, List.of(Cities.D, Cities.C), Optional.of(13)),
                Arguments.of(Cities.A, List.of(Cities.E, Cities.B, Cities.C, Cities.D), Optional.of(22)),
                Arguments.of(Cities.A, List.of(Cities.E, Cities.D), Optional.empty()));
    }

    @ParameterizedTest
    @MethodSource("exercises1to5Cases")
    public void testExercise1to5(City origin, List<City> expectedStops, Optional<Integer> expectedDistance) {
        Optional<Integer> distance = GRAPH
                .traverse(origin, Guard.lengthLessOrEqualTo(expectedStops.size() + 1))
                .filter(it -> expectedStops.equals(it.getStops()))
                .map(it -> it.getDistance())
                .findAny();

        assertEquals(expectedDistance, distance);
    }

    @Test
    public void testExercise6() {
        long result = GRAPH.traverse(Cities.C, Guard.lengthLessOrEqualTo(4))
                .filter(route -> route.getEnd().orElse(null).equals(Cities.C))
                .count();
        assertEquals(2, result);
    }

    @Test
    public void testExercise7() {
        long result = GRAPH.traverse(Cities.A, Guard.lengthLessOrEqualTo(5))
                .filter(route -> route.numberOfStops() == 4)
                .filter(route -> route.getEnd().orElse(null).equals(Cities.C))
                .count();
        assertEquals(3, result);
    }

    @Test
    public void testExercise8() {
        long result = GRAPH.traverse(Cities.A, Guard.acyclic())
                .filter(route -> route.getEnd().orElse(null).equals(Cities.C))
                .map(route -> route.getDistance())
                .min(Integer::compare)
                .get();
        assertEquals(9, result);
    }

    @Test
    public void testExercise9() {
        long result = GRAPH.traverse(Cities.B, Guard.acyclic())
                .filter(route -> route.getEnd().orElse(null).equals(Cities.B))
                .map(route -> route.getDistance())
                .min(Integer::compare)
                .get();
        assertEquals(9, result);
    }

    @Test
    public void testExercise10() {
        long result = GRAPH.traverse(Cities.C, Guard.distanceLowerThan(30))
                .filter(route -> route.getEnd().orElse(null).equals(Cities.C))
                .count();
        assertEquals(7, result);
    }
}

