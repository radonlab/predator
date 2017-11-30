/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.controller;

import cc.radonlab.predator.api.asr.domain.AudioBuffer;
import cc.radonlab.predator.api.asr.domain.TextResult;
import cc.radonlab.predator.api.asr.service.AsrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Controller that converts audio to text model.
 */
@RestController
@RequestMapping("/api/asr")
public class AsrController {
    private static Logger logger = LoggerFactory.getLogger(AsrController.class);

    private AsrService asrService;

    @Autowired
    public AsrController(AsrService asrService) {
        this.asrService = asrService;
    }

    @PostMapping
    public DeferredResult<TextResult> convert(@RequestParam("audio") MultipartFile audio) {
        DeferredResult<TextResult> deferred = new DeferredResult<>();
        try {
            AudioBuffer buffer = AudioBuffer.getBuffer(audio);
            asrService.translate(buffer, deferred);
        } catch (IOException e) {
            logger.error("Failed to read uploaded audio", e);
            deferred.setErrorResult(e);
        }
        return deferred;
    }
}
