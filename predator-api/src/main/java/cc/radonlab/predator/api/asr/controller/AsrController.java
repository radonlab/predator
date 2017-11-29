/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.controller;

import cc.radonlab.predator.api.asr.domain.TextResult;
import cc.radonlab.predator.api.asr.service.AsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping
    public TextResult convert(@RequestParam("audio") MultipartFile audio) {
        return asrService.translate();
    }
}
