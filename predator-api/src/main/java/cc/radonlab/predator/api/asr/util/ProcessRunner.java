/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.util;

public class ProcessRunner implements Runnable {
    private Process process;

    private ProcessRunner(Process process) {
        this.process = process;
    }
}
