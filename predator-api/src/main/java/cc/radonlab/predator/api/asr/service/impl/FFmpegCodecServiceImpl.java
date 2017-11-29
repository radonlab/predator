/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.service.impl;

import cc.radonlab.predator.api.asr.service.CodecService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.io.*;

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

    private File initPipe() {
        try {
            PIPES_DIR.mkdir();
            File pipe = new File(PIPES_DIR, "pipe");
            if (!pipe.exists()) {
                makePipe(pipe);
            }
            return pipe;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            abortBoot();
            return null;
        }
    }

    private void makePipe(File pipe) throws IOException, InterruptedException {
        String[] cmd = {
                "mkfifo",
                pipe.getAbsolutePath()
        };
        logger.info("exec: {}", String.join(" ", cmd));
        Process process = Runtime.getRuntime().exec(cmd);
        int code = process.waitFor();
        logger.info("exit with code: {}", code);
        if (code != 0) {
            throw new RuntimeException("Failed to make pipe");
        }
    }

    private void abortBoot() {
        ctx.close();
    }

    public File getPipe() {
        return pipe;
    }

    @Override
    public synchronized InputStream transcode(InputStream src) throws IOException {
        // make FFmpeg read from pipe
        String[] cmd = {
                "ffmpeg",
                "-i", getPipe().getAbsolutePath(),
                "-f", "s16le",
                "-acodec", "pcm_s16le",
                "-ac", "1",
                "-ar", "16000",
                "-"
        };
        Process process = Runtime.getRuntime().exec(cmd);
        // write to pipe
        InputStream is = new BufferedInputStream(src);
        OutputStream os = new BufferedOutputStream(new FileOutputStream(getPipe()));
        byte[] buffer = new byte[1024 * 8];
        int size;
        while ((size = is.read(buffer)) > 0) {
            os.write(buffer, 0, size);
        }
        return process.getInputStream();
    }
}
