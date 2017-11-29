/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.service;

import cc.radonlab.predator.api.asr.domain.TextResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.Future;

public interface AsrService {
    public Future<TextResult> translate(MultipartFile audio);
}
