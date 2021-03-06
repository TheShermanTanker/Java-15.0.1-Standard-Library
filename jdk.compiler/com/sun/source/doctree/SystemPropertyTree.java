/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
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
 *
 *
 */

package com.sun.source.doctree;


import javax.lang.model.element.Name;

/**
 * A tree node for an {@code @systemProperty} inline tag.
 *
 * <p>
 * {&#064;systemProperty property-name}
 *
 * @since 12
 */
public interface SystemPropertyTree extends InlineTagTree {
    /**
     * Returns the specified system property name.
     * @return the system property name
     */
    Name getPropertyName();
}
