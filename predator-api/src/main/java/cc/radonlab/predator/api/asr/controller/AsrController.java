/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.controller;

import cc.radonlab.predator.api.asr.domain.TextResult;
import cc.radonlab.predator.api.asr.service.AsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that converts audio to text model.
 */
@RestController
@RequestMapping("/asr")
public class AsrController {
    private AsrService asrService;

    @Autowired
    public AsrController(AsrService asrService) {
        this.asrService = asrService;
    }

    @GetMapping
    public TextResult convert() {
        return asrService.translate();
    }
}
