/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.service.impl;

import cc.radonlab.predator.api.asr.service.CodecService;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class FFmpegCodecServiceImpl implements CodecService {
    public FFmpegCodecServiceImpl() {
    }

    @Override
    public InputStream transcode(InputStream is) {
        return null;
    }
}
