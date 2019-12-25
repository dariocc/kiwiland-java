package nu.dario.kiwiland;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class Guard implements Predicate<Route> {

    public static Guard lengthLessOrEqualTo(int value)
    {
        return new LengthLessOrEqualGuard(value);
    }

    public static Guard distanceLowerThan(int value)
    {
        return new DistanceLowerGuard(value);
    }

    public static Guard acyclic()
    {
        return new AcyclicGuard();
    }

    private Guard() {}

    private static class AcyclicGuard extends Guard {
        public boolean test(Route route)
        {
            if (route.numberOfStops() == 0)
                return true;

            List<Link> links = new ArrayList(route.getLinks());
            Link last = links.remove(links.size() - 1);
            return !links.contains(last);
        }
    }

    private static class DistanceLowerGuard extends Guard {
        private final int operand;

        public DistanceLowerGuard(int operand)
        {
            this.operand = operand;
        }

        public boolean test(Route route)
        {
            return route.getDistance() < operand;
        }
    }

    private static class LengthLessOrEqualGuard extends Guard {
        private final int operand;

        public LengthLessOrEqualGuard(int operand)
        {
            this.operand = operand;
        }

        public boolean test(Route route)
        {
            return route.length() <= operand;
        }
    }
}
