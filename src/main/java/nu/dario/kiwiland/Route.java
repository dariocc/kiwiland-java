package nu.dario.kiwiland;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Route {
    private final City origin;
    private final Collection<EndPoint> endPoints;

    private Route(City origin, Collection<EndPoint> endPoints) {
        this.origin = origin;
        this.endPoints = endPoints;
    }

    public City getLastCity() {
        return getStops().stream().reduce((a, b) -> b).orElse(origin);
    }

    public Collection<Link> getLinks() {
        List<Link> pairs = new LinkedList<>();
        getCities().stream().reduce((a, b) -> {
            pairs.add(new Link(a, b));
            return b;
        });
        return pairs;
    }

    public Collection<City> getStops() {
        return endPoints.stream().map(x -> x.end).collect(Collectors.toList());
    }

    public Collection<City> getCities() {
        return Stream.concat(Stream.of(origin), getStops().stream()).collect(Collectors.toList());
    }

    public City getOrigin() {
        return origin;
    }

    public Optional<City> getEnd() {
        return endPoints.stream().map(x -> x.end).reduce((a, b) -> b);
    }

    public int getDistance() {
        return endPoints.stream().collect(Collectors.summingInt(x -> x.distance));
    }

    public int numberOfStops() {
        return (int) endPoints.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return getOrigin().equals(route.getOrigin()) &&
                getStops().equals(route.getStops());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrigin(), getStops());
    }

    public int length() {
        return numberOfStops() + 1;
    }

    public static Route of(City origin) {
        return new Route(origin, new ArrayList<>());
    }

    public Route extend(City destination, int distance) {
        if (distance <= 0) throw new IllegalArgumentException("Negative or zero distance.");
        EndPoint endPoint = new EndPoint(destination, distance);
        List<EndPoint> endPoints = new ArrayList(this.endPoints);
        endPoints.add(endPoint);
        return new Route(origin, endPoints);
    }

    protected static final class EndPoint {
        private final City end;
        private final int distance;

        public EndPoint(City destination, int distance) {
            this.end = destination;
            this.distance = distance;
        }
    }
}
