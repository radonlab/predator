/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.service.impl;

import cc.radonlab.predator.api.asr.domain.AudioBuffer;
import cc.radonlab.predator.api.asr.domain.TextResult;
import cc.radonlab.predator.api.asr.service.AsrService;
import cc.radonlab.predator.api.asr.service.CodecService;
import com.iflytek.cloud.speech.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

@Service
public class AsrServiceImpl implements AsrService {
    private static Logger logger = LoggerFactory.getLogger(AsrServiceImpl.class);

    @Autowired
    private CodecService codec;

    private SpeechRecognizer recognizer;

    @Autowired
    public AsrServiceImpl(
            @Value("${site.asr.appId}")
            String appId
    ) {
        SpeechUtility.createUtility(SpeechConstant.APPID + "=" + appId);
        recognizer = SpeechRecognizer.createRecognizer();
        recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        recognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
        // Use source from data stream
        recognizer.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
    }

    @Override
    @Async
    public void translate(AudioBuffer audio, DeferredResult<TextResult> deferred) {
    }

    private class AsrHandler implements RecognizerListener {
        private DeferredResult<TextResult> deffered;

        private AsrHandler(DeferredResult<TextResult> deffered) {
            this.deffered = deffered;
        }

        @Override
        public void onVolumeChanged(int i) {
        }

        @Override
        public void onBeginOfSpeech() {
        }

        @Override
        public void onEndOfSpeech() {
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
        }

        @Override
        public void onError(SpeechError speechError) {
        }

        @Override
        public void onEvent(int i, int i1, int i2, String s) {
        }
    }
}
