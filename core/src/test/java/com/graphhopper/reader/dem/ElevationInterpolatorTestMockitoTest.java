package com.graphhopper.reader.dem;

import com.graphhopper.util.PointList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ElevationInterpolatorMockitoTest {

    private final ElevationInterpolator interpolator = new ElevationInterpolator();

    @Mock
    private PointList pointList;

    @Test
    void returnsExactElevationWhenQueryPointMatchesExistingPoint() {
        double[] lats = {10, 15, 20, 25};
        double[] lons = {0, 1, 2, 3};
        double[] eles = {100, 200, 500, 800};

        when(pointList.size()).thenReturn(lats.length);
        lenient().when(pointList.getLat(anyInt())).thenAnswer(inv -> lats[(int) inv.getArgument(0)]);
        lenient().when(pointList.getLon(anyInt())).thenAnswer(inv -> lons[(int) inv.getArgument(0)]);
        lenient().when(pointList.getEle(anyInt())).thenAnswer(inv -> eles[(int) inv.getArgument(0)]);

        double elevation = interpolator.calculateElevationBasedOnPointList(20.000005, 2.000004, pointList);

        // Tolérance pour éviter l’échec dû aux calculs flottants
        assertEquals(500.0, elevation, 0.001);
        verify(pointList, atLeastOnce()).getEle(2);
    }
}
