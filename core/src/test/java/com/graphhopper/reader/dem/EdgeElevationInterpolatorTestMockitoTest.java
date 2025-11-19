package com.graphhopper.reader.dem;

import com.graphhopper.routing.ev.EnumEncodedValue;
import com.graphhopper.routing.ev.RoadEnvironment;
import com.graphhopper.routing.util.AllEdgesIterator;
import com.graphhopper.storage.BaseGraph;
import com.graphhopper.storage.NodeAccess;
import com.graphhopper.util.EdgeExplorer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EdgeElevationInterpolatorMockitoTest {

    @Mock
    private BaseGraph graph;
    @Mock
    private NodeAccess nodeAccess;
    @Mock
    private AllEdgesIterator allEdgesIterator;
    @Mock
    private EdgeExplorer edgeExplorer;
    @Mock
    private EnumEncodedValue<RoadEnvironment> roadEnvEnc;

    private EdgeElevationInterpolator interpolator;

    @BeforeEach
    void setUp() {
        when(graph.getNodeAccess()).thenReturn(nodeAccess);
        when(graph.getAllEdges()).thenReturn(allEdgesIterator);
        when(graph.createEdgeExplorer()).thenReturn(edgeExplorer);
        when(allEdgesIterator.length()).thenReturn(0);
        when(allEdgesIterator.next()).thenReturn(false);
        interpolator = new EdgeElevationInterpolator(graph, roadEnvEnc, RoadEnvironment.BRIDGE);
    }

    @Test
    void executeSkipsWhenNoInterpolatableEdgesArePresent() {
        interpolator.execute();
        verify(graph, times(2)).getAllEdges();
        verify(allEdgesIterator, times(2)).next();
        verify(nodeAccess, never()).setNode(anyInt(), anyDouble(), anyDouble(), anyDouble());
    }
}
