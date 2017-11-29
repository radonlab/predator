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

    private NlsRequest createRequest() {
        NlsRequest request = new NlsRequest();
        request.authorize(this.acKeyId, this.acKeySecret);
        return request;
    }

    private void sendAudioData (MultipartFile audio, NlsFuture future) {
    }

    @Override
    public Future<TextResult> translate(MultipartFile audio) {
        CompletableFuture<TextResult> deferred = new CompletableFuture<>();
        // create nls request
        NlsRequest request = createRequest();
        // setup callback
        NlsFuture future = client.createNlsFuture(request, );
        // send data
        sendAudioData(audio, future);
        // set timeout
        future.await(1000 * 10);
        return deferred;
    }

}
