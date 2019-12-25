package nu.dario.kiwiland;


import java.util.Objects;

public final class LinkDistancePair {
    private final Link link;
    private final int distance;

    public LinkDistancePair(Link link, int distance) {
        if (distance <= 0) throw new
            IllegalArgumentException("Negative or zero distances not valid.");

        this.link = link;
        this.distance = distance;
    }

    public LinkDistancePair(City origin, City end, int distance)
    {
        this(new Link(origin, end), distance);
    }

    public int getDistance() {
        return this.distance;
    }

    public City getOrigin() {
        return link.getOrigin();
    }

    public City getEnd() {
        return link.getEnd();
    }

    public Link getLink() {
        return link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkDistancePair linkDistancePair = (LinkDistancePair) o;
        return distance == linkDistancePair.distance &&
                link.equals(linkDistancePair.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, distance);
    }
}
