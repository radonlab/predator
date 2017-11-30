/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.service;

import cc.radonlab.predator.api.asr.domain.AudioBuffer;
import cc.radonlab.predator.api.asr.domain.TextResult;
import org.springframework.web.context.request.async.DeferredResult;

public interface AsrService {
    public DeferredResult<TextResult> translate(AudioBuffer audio);
}
