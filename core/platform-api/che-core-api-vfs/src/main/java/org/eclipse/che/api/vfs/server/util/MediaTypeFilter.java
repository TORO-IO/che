/*******************************************************************************
 * Copyright (c) 2012-2016 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.api.vfs.server.util;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.eclipse.che.api.core.ForbiddenException;
import org.eclipse.che.api.core.ServerException;
import org.eclipse.che.api.vfs.server.VirtualFile;
import org.eclipse.che.api.vfs.server.VirtualFileFilter;

import java.io.IOException;
import java.util.Set;

/**
 * Filter based on media type of the file.
 *
 * @author Valeriy Svydenko
 */
public class MediaTypeFilter implements VirtualFileFilter {
    private final Set<MediaType> mediaTypes;

    public MediaTypeFilter() {
        this.mediaTypes = MediaType.set(MediaType.TEXT_HTML, MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML);
    }

    @Override
    public boolean accept(VirtualFile file) {
        try {
            TikaConfig tikaConfig = new TikaConfig();
            MediaType mimeType = tikaConfig.getDetector().detect(file.getContent().getStream(), new Metadata());
            return mediaTypes.contains(mimeType);
        } catch (TikaException | ForbiddenException | ServerException | IOException e) {
            return false;
        }
    }
}
