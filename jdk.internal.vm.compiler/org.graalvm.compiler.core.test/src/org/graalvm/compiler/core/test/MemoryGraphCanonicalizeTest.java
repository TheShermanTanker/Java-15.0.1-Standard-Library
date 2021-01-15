/*
 * Copyright (c) 2017, 2020, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */



package org.graalvm.compiler.core.test;

import org.graalvm.compiler.api.test.Graal;
import org.graalvm.compiler.nodes.StructuredGraph;
import org.graalvm.compiler.nodes.memory.WriteNode;
import org.graalvm.compiler.phases.tiers.Suites;
import org.graalvm.compiler.runtime.RuntimeProvider;
import org.junit.Test;

public class MemoryGraphCanonicalizeTest extends GraalCompilerTest {
    static class TestObject {
        Object object;
        Integer integer;
        int value;
        volatile boolean written;
    }

    public static void simpleElimination(TestObject object) {
        object.object = object;
        object.value = object.integer;
        object.value = object.integer + 2;
        object.value = object.integer + 3;
    }

    @Test
    public void testSimpleElimination() {
        testGraph("simpleElimination", 2);
    }

    public static void complexElimination(TestObject object) {
        object.object = object;
        object.value = object.integer;
        object.value = object.integer + 2;
        if (object.object == null) {
            object.value = object.integer + 3;
        } else {
            object.object = new Object();
        }
        object.written = true;
        object.value = 5;
    }

    @Test
    public void testComplexElimination() {
        testGraph("complexElimination", 5);
    }

    public void testGraph(String name, int expectedWrites) {
        StructuredGraph graph = parseEager(name, StructuredGraph.AllowAssumptions.YES);
        Suites s = Graal.getRequiredCapability(RuntimeProvider.class).getHostBackend().getSuites().getDefaultSuites(getInitialOptions());
        s.getHighTier().apply(graph, getDefaultHighTierContext());
        s.getMidTier().apply(graph, getDefaultMidTierContext());

        int writes = graph.getNodes().filter(WriteNode.class).count();
        assertTrue(writes == expectedWrites, "Expected %d writes, found %d", expectedWrites, writes);
    }
}
