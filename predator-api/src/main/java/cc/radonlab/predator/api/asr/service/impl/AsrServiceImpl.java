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

import java.io.IOException;
import java.io.InputStream;

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
        recognizer.startListening(new AsrHandler(deferred));
        writeData(audio);
        recognizer.stopListening();
    }

    private void writeData(AudioBuffer audio) {
        try {
            logger.info("Transcode: {}-{}", audio.getName(), audio.getContentType());
            InputStream is = codec.transcode(audio);
            byte[] buffer = new byte[4800];
            int size = 0;
            int length;
            while ((length = is.read(buffer)) > 0) {
                recognizer.writeAudio(buffer, 0, length);
                size += length;
                Thread.sleep(150);
            }
            logger.info("Finish writing data: {} bytes", size);
        } catch (IOException | InterruptedException e) {
            logger.error("Failed to write data", e);
        }
    }

    private class AsrHandler implements RecognizerListener {
        private DeferredResult<TextResult> deffered;
        private TextResult result;

        private AsrHandler(DeferredResult<TextResult> deffered) {
            this.deffered = deffered;
            this.result = new TextResult();
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
        public void onResult(RecognizerResult recognizerResult, boolean last) {
            logger.info("Recognizer result");
            if (recognizerResult != null) {
                result.append(recognizerResult.getResultString());
                if (last) {
                    // last result
                    deffered.setResult(result);
                    logger.info("Response");
                }
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            deffered.setErrorResult(speechError);
            logger.error("Recognizer error", speechError);
        }

        @Override
        public void onEvent(int i, int i1, int i2, String s) {
        }
    }
}
