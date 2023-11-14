package org.waitlight.codememo.utils;

import org.junit.jupiter.api.Test;

class RayCrossingTest {

    /**
     * <a href="https://www.geogebra.org/geometry">多边形构造器</a>
     */
    @Test
    void isPointInPolygon() {

        RayCrossing.Point[] points = {
                new RayCrossing.Point(6, 8),
                new RayCrossing.Point(16, 8),
                new RayCrossing.Point(16, 1),
                new RayCrossing.Point(1, 1),
                new RayCrossing.Point(1, 5),
                new RayCrossing.Point(6, 5)
        };

        System.out.println(RayCrossing.isPointInPolygon(new RayCrossing.Point(7, 5), points));
    }
}