/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.service.impl;

import cc.radonlab.predator.api.asr.domain.TextResult;
import cc.radonlab.predator.api.asr.service.AsrService;
import com.alibaba.idst.nls.NlsClient;
import com.alibaba.idst.nls.NlsFuture;
import com.alibaba.idst.nls.event.NlsEvent;
import com.alibaba.idst.nls.event.NlsListener;
import com.alibaba.idst.nls.protocol.NlsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class AsrServiceImpl implements AsrService {
    @Value("${site.asr.acKeyId}")
    private String acKeyId;

    @Value("${site.asr.acKeySecret}")
    private String acKeySecret;

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
        NlsFuture future = helper.startRequest(request);
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

        public NlsFuture startRequest(NlsRequest request) {
        }

        @Override
        public void onMessageReceived(NlsEvent nlsEvent) {
        }

        @Override
        public void onOperationFailed(NlsEvent nlsEvent) {
        }

        @Override
        public void onChannelClosed(NlsEvent nlsEvent) {
        }
    }
}
