/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr;

import cc.radonlab.predator.api.asr.domain.TextResult;
import cc.radonlab.predator.api.asr.service.SttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that provides capability of converting speech to text.
 */
@RestController
@RequestMapping("/asr")
public class SttController {
    private SttService sttService;

    @Autowired
    public SttController(SttService sttService) {
        this.sttService = sttService;
    }

    @GetMapping
    public TextResult convert() {
        return sttService.translate();
    }
}
