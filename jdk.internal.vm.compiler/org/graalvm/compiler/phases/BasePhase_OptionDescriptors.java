// CheckStyle: stop header check
// CheckStyle: stop line length check
// GENERATED CONTENT - DO NOT EDIT
// Source: BasePhase.java
package org.graalvm.compiler.phases;

import java.util.*;
import org.graalvm.compiler.options.*;
import org.graalvm.compiler.options.OptionType;

public class BasePhase_OptionDescriptors implements OptionDescriptors {
    @Override
    public OptionDescriptor get(String value) {
        switch (value) {
        // CheckStyle: stop line length check
        case "CompilationExcludePhases": {
            return OptionDescriptor.create(
                /*name*/ "CompilationExcludePhases",
                /*optionType*/ OptionType.Debug,
                /*optionValueType*/ String.class,
                /*help*/ "Exclude certain phases from compilation, either unconditionally or with a method filter",
                /*declaringClass*/ BasePhase.PhaseOptions.class,
                /*fieldName*/ "CompilationExcludePhases",
                /*option*/ BasePhase.PhaseOptions.CompilationExcludePhases);
        }
        case "VerifyGraalPhasesSize": {
            return OptionDescriptor.create(
                /*name*/ "VerifyGraalPhasesSize",
                /*optionType*/ OptionType.Debug,
                /*optionValueType*/ Boolean.class,
                /*help*/ "Verify before - after relation of the relative, computed, code size of a graph",
                /*declaringClass*/ BasePhase.PhaseOptions.class,
                /*fieldName*/ "VerifyGraalPhasesSize",
                /*option*/ BasePhase.PhaseOptions.VerifyGraalPhasesSize);
        }
        // CheckStyle: resume line length check
        }
        return null;
    }

    @Override
    public Iterator<OptionDescriptor> iterator() {
        return new Iterator<OptionDescriptor>() {
            int i = 0;
            @Override
            public boolean hasNext() {
                return i < 2;
            }
            @Override
            public OptionDescriptor next() {
                switch (i++) {
                    case 0: return get("CompilationExcludePhases");
                    case 1: return get("VerifyGraalPhasesSize");
                }
                throw new NoSuchElementException();
            }
        };
    }
}
