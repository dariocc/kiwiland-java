package nu.dario.kiwiland;

import java.util.function.Predicate;
import java.util.stream.Stream;

public interface Graph {
    Stream<LinkDistancePair> linkDistancePairs();

    default Stream<Route> traverse(City origin, Guard guard) {
        return traverse(Route.of(origin), guard);
    }

    private Stream<LinkDistancePair> linkDistancePairsFrom(City origin) {
        return linkDistancePairs().filter(x -> x.getOrigin().equals(origin));
    }

    private Stream<Route> traverse(Route route, Predicate<Route> guard) {
        Stream<LinkDistancePair> elements = linkDistancePairsFrom(route.getLastCity());
        return elements.collect(() -> Stream.<Route>builder(),
                (b, pair) -> {
                    Route extendedRoute = route.extend(pair.getLink().getEnd(), pair.getDistance());
                    if (guard.test(extendedRoute)) {
                        b.add(extendedRoute);
                        traverse(extendedRoute, guard).forEach(r -> b.add(r));
                    }
                },
                (b1, b2) -> b1.build().forEach(x -> b2.add(x))).build();
    }
}

