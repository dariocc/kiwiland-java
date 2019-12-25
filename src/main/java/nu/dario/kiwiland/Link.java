package nu.dario.kiwiland;

import java.util.Objects;

public final class Link {
    private final City origin;
    private final City end;

    public Link(City origin, City end) {
        if (origin.equals(end)) throw new IllegalArgumentException("Origin and end must be different");
        this.origin = origin;
        this.end = end;
    }

    public City getOrigin() {
        return origin;
    }

    public City getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return origin.equals(link.origin) &&
                end.equals(link.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, end);
    }
}
