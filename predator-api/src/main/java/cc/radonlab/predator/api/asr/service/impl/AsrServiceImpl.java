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
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechUtility;
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

    @Value("${site.asr.appId}")
    private String appId;

    @Autowired
    private CodecService codec;

    private SpeechRecognizer recognizer;

    public AsrServiceImpl() {
        SpeechUtility.createUtility(SpeechConstant.APPID + "=" + appId);
        recognizer = SpeechRecognizer.createRecognizer();
    }

    @Override
    @Async
    public void translate(AudioBuffer audio, DeferredResult<TextResult> deferred) {
        // setup helper
        ServiceHelper helper = new ServiceHelper(deferred);
        // create request
        NlsRequest request = helper.createRequest(this.acKeyId, this.acKeySecret);
        // query nls server
        NlsFuture future = helper.startConnection(request);
        helper.sendData(audio, future);
    }

    private class ServiceHelper {
        private DeferredResult<TextResult> deffered;

        ServiceHelper(DeferredResult<TextResult> deffered) {
            this.deffered = deffered;
        }

//        void sendData(AudioBuffer audio, NlsFuture future) {
//            try {
//                logger.info("transcode file: {}", audio.getContentType());
//                InputStream is = codec.transcode(audio);
//                byte[] buffer = new byte[8000];
//                int size = 0;
//                int length;
//                while ((length = is.read(buffer)) > 0) {
//                    System.out.println("->" + size);
//                    future.sendVoice(buffer, 0, length);
//                    size += length;
//                    Thread.sleep(50);
//                }
//                future.sendFinishSignal();
//                future.await(10000);
//                logger.info("finish sending data: {} bytes", size);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }
}
