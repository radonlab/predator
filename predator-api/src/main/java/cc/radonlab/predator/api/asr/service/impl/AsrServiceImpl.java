/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.service.impl;

import cc.radonlab.predator.api.asr.domain.TextResult;
import cc.radonlab.predator.api.asr.service.AsrService;
import com.alibaba.idst.nls.NlsClient;
import com.alibaba.idst.nls.protocol.NlsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public void sendRequest() {
        NlsRequest request = new NlsRequest();
        request.authorize(this.acKeyId, this.acKeySecret);
    }

    @Override
    public TextResult translate() {
        TextResult result = new TextResult();
        result.setResult("hello world");
        return result;
    }
}
