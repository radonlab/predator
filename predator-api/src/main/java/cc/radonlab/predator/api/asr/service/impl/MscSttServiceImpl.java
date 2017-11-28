/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.stt.service.impl;

import cc.radonlab.predator.api.stt.domain.TextResult;
import cc.radonlab.predator.api.stt.service.SttService;
import org.springframework.stereotype.Service;

@Service
public class MscSttServiceImpl implements SttService {

    public MscSttServiceImpl() {
    }

    @Override
    public TextResult translate() {
        TextResult result = new TextResult();
        result.setResult("hello world");
        return result;
    }
}
