/*
 * Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
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

package jdk.incubator.jpackage.internal;

import java.io.File;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import static jdk.incubator.jpackage.internal.StandardBundlerParam.*;

final class FileAssociation {
    void verify() {
        if (extensions.isEmpty()) {
            Log.error(I18N.getString(
                    "message.creating-association-with-null-extension"));
        }
    }

    static void verify(List<FileAssociation> associations) throws ConfigException {
        // only one mime type per association, at least one file extention
        int assocIdx = 0;
        for (var assoc : associations) {
            ++assocIdx;
            if (assoc.mimeTypes.isEmpty()) {
                String msgKey = "error.no-content-types-for-file-association";
                throw new ConfigException(
                        MessageFormat.format(I18N.getString(msgKey), assocIdx),
                        MessageFormat.format(I18N.getString(msgKey + ".advice"), assocIdx));
            }

            if (assoc.mimeTypes.size() > 1) {
                String msgKey = "error.too-many-content-types-for-file-association";
                throw new ConfigException(
                        MessageFormat.format(I18N.getString(msgKey), assocIdx),
                        MessageFormat.format(I18N.getString(msgKey + ".advice"), assocIdx));
            }

            assoc.verify();
        }
    }

    static List<FileAssociation> fetchFrom(Map<String, ? super Object> params) {
        String launcherName = APP_NAME.fetchFrom(params);

        return FILE_ASSOCIATIONS.fetchFrom(params).stream().filter(
                Objects::nonNull).map(fa -> {
                    FileAssociation assoc = new FileAssociation();

                    assoc.launcherPath = Path.of(launcherName);
                    assoc.description = FA_DESCRIPTION.fetchFrom(fa);
                    assoc.extensions = Optional.ofNullable(
                            FA_EXTENSIONS.fetchFrom(fa)).orElse(Collections.emptyList());
                    assoc.mimeTypes = Optional.ofNullable(
                            FA_CONTENT_TYPE.fetchFrom(fa)).orElse(Collections.emptyList());

                    File icon = FA_ICON.fetchFrom(fa);
                    if (icon != null) {
                        assoc.iconPath = icon.toPath();
                    }

                    return assoc;
                }).collect(Collectors.toList());
    }

    Path launcherPath;
    Path iconPath;
    List<String> mimeTypes;
    List<String> extensions;
    String description;
}
