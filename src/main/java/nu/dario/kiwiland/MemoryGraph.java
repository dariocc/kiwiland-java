package nu.dario.kiwiland;

import java.util.*;
import java.util.stream.Stream;

public class MemoryGraph implements Graph {
    private final List<LinkDistancePair> linkDistancePairs;

    private MemoryGraph(List<LinkDistancePair> linkDistancePairs) {
        this.linkDistancePairs = new ArrayList<>(linkDistancePairs);
    }

    @Override
    public Stream<LinkDistancePair> linkDistancePairs() {
        return linkDistancePairs.stream();
    }

    public static class Builder {
        private final ArrayList<LinkDistancePair> linkDistancePairs = new ArrayList<>();

        public Builder connect(City origin, City end, int distance) {
            LinkDistancePair linkDistancePair = new LinkDistancePair(origin, end, distance);
            linkDistancePairs.add(linkDistancePair);
            return this;
        }

        public Graph build() {
            return new MemoryGraph(new ArrayList<>(linkDistancePairs));
        }
    }
}
