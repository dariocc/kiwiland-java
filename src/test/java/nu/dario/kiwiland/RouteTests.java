package nu.dario.kiwiland;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RouteTests {

    @Test
    public void testRetrievingStopsLinksAndCities() {
        City foo = new City();
        City fee = new City();
        City bar = new City();

        Route route = Route.of(foo).extend(fee, 1).extend(bar, 2);

        List<City> expectedStops = List.of(fee, bar);
        List<Link> expectedLinks = List.of(new Link(foo, fee), new Link(fee, bar));
        List<City> expectedCities = List.of(foo, fee, bar);

        assertAll( () -> assertEquals(expectedStops, route.getStops()),
                () -> assertEquals(expectedLinks, route.getLinks()),
                () -> assertEquals(expectedCities, route.getCities())
        );
    }
}
