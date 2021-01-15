/*
 * Copyright (c) 2012, 2019, Oracle and/or its affiliates. All rights reserved.
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


package org.graalvm.compiler.loop.phases;

import org.graalvm.compiler.debug.CounterKey;
import org.graalvm.compiler.debug.DebugContext;
import org.graalvm.compiler.loop.LoopEx;
import org.graalvm.compiler.loop.LoopPolicies;
import org.graalvm.compiler.loop.LoopsData;
import org.graalvm.compiler.nodes.StructuredGraph;
import org.graalvm.compiler.nodes.spi.CoreProviders;

public class LoopPeelingPhase extends LoopPhase<LoopPolicies> {

    public static final CounterKey PEELED = DebugContext.counter("Peeled");

    public LoopPeelingPhase(LoopPolicies policies) {
        super(policies);
    }

    @Override
    @SuppressWarnings("try")
    protected void run(StructuredGraph graph, CoreProviders context) {
        DebugContext debug = graph.getDebug();
        if (graph.hasLoops()) {
            LoopsData data = new LoopsData(graph);
            try (DebugContext.Scope s = debug.scope("peeling", data.getCFG())) {
                for (LoopEx loop : data.outerFirst()) {
                    if (loop.canDuplicateLoop() && loop.loopBegin().getLoopEndCount() > 0) {
                        if (LoopPolicies.Options.PeelALot.getValue(graph.getOptions()) || getPolicies().shouldPeel(loop, data.getCFG(), context)) {
                            debug.log("Peeling %s", loop);
                            PEELED.increment(debug);
                            LoopTransformations.peel(loop);
                            debug.dump(DebugContext.DETAILED_LEVEL, graph, "Peeling %s", loop);
                        }
                    }
                }
                data.deleteUnusedNodes();
            } catch (Throwable t) {
                throw debug.handle(t);
            }
        }
    }

    @Override
    public float codeSizeIncrease() {
        return 10.0f;
    }
}
