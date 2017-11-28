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

@Service
public class AsrServiceImpl implements AsrService, NlsListener {
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

    public void sendQuery() throws Exception {
        NlsRequest request = createRequest();
        NlsFuture future = client.createNlsFuture(request, this);
        future.await(1000 * 10);
    }

    @Override
    public TextResult translate() {
        TextResult result = new TextResult();
        result.setResult("hello world");
        return result;
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
