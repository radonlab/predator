/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.service.impl;

import cc.radonlab.predator.api.asr.domain.TextResult;
import cc.radonlab.predator.api.asr.service.AsrService;
import org.springframework.stereotype.Service;

@Service
public class AsrServiceImpl implements AsrService {

    public AsrServiceImpl() {
    }

    @Override
    public TextResult translate() {
        TextResult result = new TextResult();
        result.setResult("hello world");
        return result;
    }
}
