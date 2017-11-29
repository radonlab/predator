/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.service;

import java.io.InputStream;

public interface CodecService {
    public InputStream transcode(InputStream is);
}
