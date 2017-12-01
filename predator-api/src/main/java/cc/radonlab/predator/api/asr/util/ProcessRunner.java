/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessRunner implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(ProcessRunner.class);

    public static ProcessRunner bind(Process process) {
        return new ProcessRunner(process);
    }

    private Process process;

    private ProcessRunner(Process process) {
        this.process = process;
    }

    @Override
    public void run() {
        try {
            InputStream stderr = process.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stderr));
            String line;
            while ((line = br.readLine()) != null) {
                logger.trace(line);
            }
        } catch (IOException e) {
            logger.error("Failed reading stderr");
        }
    }
}
