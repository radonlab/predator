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
import com.alibaba.idst.nls.NlsClient;
import com.alibaba.idst.nls.NlsFuture;
import com.alibaba.idst.nls.event.NlsEvent;
import com.alibaba.idst.nls.event.NlsListener;
import com.alibaba.idst.nls.protocol.NlsRequest;
import com.alibaba.idst.nls.protocol.NlsResponse;
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

    @Value("${site.asr.acKeyId}")
    private String acKeyId;

    @Value("${site.asr.acKeySecret}")
    private String acKeySecret;

    @Autowired
    private CodecService codec;

    private NlsClient client;

    public AsrServiceImpl() {
        client = new NlsClient();
        client.init();
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

    private class ServiceHelper implements NlsListener {
        private DeferredResult<TextResult> deffered;

        ServiceHelper(DeferredResult<TextResult> deffered) {
            this.deffered = deffered;
        }

        NlsRequest createRequest(String acKeyId, String acKeySecret) {
            NlsRequest request = new NlsRequest();
            request.setAppKey("nls-service");
            request.setAsrFormat("pcm");
            request.authorize(acKeyId, acKeySecret);
            return request;
        }

        NlsFuture startConnection(NlsRequest request) {
            NlsFuture future = null;
            try {
                future = client.createNlsFuture(request, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return future;
        }

        void sendData(AudioBuffer audio, NlsFuture future) {
            try {
                logger.info("transcode file: {}", audio.getContentType());
                InputStream is = codec.transcode(audio);
                byte[] buffer = new byte[8000];
                int size = 0;
                int length;
                while ((length = is.read(buffer)) > 0) {
                    future.sendVoice(buffer, 0, length);
                    size += length;
                }
                future.sendFinishSignal();
                logger.info("finish sending data: {} bytes", size);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onMessageReceived(NlsEvent e) {
            logger.info("message received");
            NlsResponse response = e.getResponse();
            String ret = response.getAsr_ret();
            if (ret != null) {
                TextResult result = new TextResult();
                result.setResult(ret);
                deffered.complete(result);
            }
        }

        @Override
        public void onOperationFailed(NlsEvent e) {
            logger.info("request failed: {}", e.getErrorMessage());
        }

        @Override
        public void onChannelClosed(NlsEvent e) {
            logger.info("connection closed: {}", e.getErrorMessage());
        }
    }
}
