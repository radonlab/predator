/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.service.impl;

import cc.radonlab.predator.api.asr.domain.TextResult;
import cc.radonlab.predator.api.asr.service.AsrService;
import cc.radonlab.predator.api.asr.service.CodecService;
import com.alibaba.idst.nls.NlsClient;
import com.alibaba.idst.nls.NlsFuture;
import com.alibaba.idst.nls.event.NlsEvent;
import com.alibaba.idst.nls.event.NlsListener;
import com.alibaba.idst.nls.protocol.NlsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

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
    public Future<TextResult> translate(MultipartFile audio) {
        CompletableFuture<TextResult> deferred = new CompletableFuture<>();
        // setup helper
        ServiceHelper helper = new ServiceHelper(deferred);
        // create request
        NlsRequest request = helper.createRequest(this.acKeyId, this.acKeySecret);
        // query nls server
        NlsFuture future = helper.startConnection(request);
        helper.sendData(audio, future);
        // set timeout
        future.await(1000 * 10);
        return deferred;
    }

    class ServiceHelper implements NlsListener {
        private CompletableFuture<TextResult> deffered;

        public ServiceHelper(CompletableFuture<TextResult> deffered) {
            this.deffered = deffered;
        }

        public NlsRequest createRequest(String acKeyId, String acKeySecret) {
            NlsRequest request = new NlsRequest();
            request.setAppKey("nls-service");
            request.setAsrFormat("pcm");
            request.authorize(acKeyId, acKeySecret);
            return request;
        }

        public NlsFuture startConnection(NlsRequest request) {
            NlsFuture future = null;
            try {
                future = client.createNlsFuture(request, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return future;
        }

        public void sendData(MultipartFile audio, NlsFuture future) {
            try {
                InputStream is = codec.transcode(audio.getInputStream());
                byte[] buffer = new byte[8000];
                int size;
                while ((size = is.read(buffer)) > 0) {
                    future.sendVoice(buffer, 0, size);
                }
                future.sendFinishSignal();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onMessageReceived(NlsEvent nlsEvent) {
        }

        @Override
        public void onOperationFailed(NlsEvent e) {
            logger.info("request failed: [{}]: {}",
                    e.getResponse().getStatus(), e.getErrorMessage());
        }

        @Override
        public void onChannelClosed(NlsEvent e) {
            logger.info("connection closed: [{}]: {}",
                    e.getResponse().getStatus(), e.getErrorMessage());
        }
    }
}
