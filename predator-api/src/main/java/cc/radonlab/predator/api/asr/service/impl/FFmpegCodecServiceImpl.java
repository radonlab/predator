/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.service.impl;

import cc.radonlab.predator.api.asr.service.CodecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.access.BootstrapException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FFmpegCodecServiceImpl implements CodecService {
    private static Logger logger = LoggerFactory.getLogger(FFmpegCodecServiceImpl.class);

    private static String TMP_DIR = System.getProperty("java.io.tmpdir");
    private static File PIPES_DIR = new File(TMP_DIR, "pdt-pipes");

    private ConfigurableApplicationContext ctx;
    private File pipe;

    @Autowired
    public FFmpegCodecServiceImpl(ConfigurableApplicationContext ctx) {
        this.ctx = ctx;
        this.pipe = initPipe();
    }

    public File getPipe() {
        return pipe;
    }

    @Override
    public InputStream transcode(InputStream is) {
        return null;
    }
}
