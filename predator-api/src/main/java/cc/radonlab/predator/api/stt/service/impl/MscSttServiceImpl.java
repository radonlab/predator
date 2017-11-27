/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.stt.service.impl;

import cc.radonlab.predator.api.stt.domain.TextResult;
import cc.radonlab.predator.api.stt.service.SttService;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechRecognizer;
import com.iflytek.cloud.speech.SpeechUtility;
import org.springframework.stereotype.Service;

@Service
public class MscSttServiceImpl implements SttService {
    private static final String STT_APP_ID = "";

    private SpeechRecognizer recognizer;

    public MscSttServiceImpl() {
        SpeechUtility.createUtility(SpeechConstant.APPID + "=" + STT_APP_ID);
        recognizer = initRecognizer();
    }

    private SpeechRecognizer initRecognizer() {
        SpeechRecognizer recognizer = SpeechRecognizer.createRecognizer();
        recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn"); // "zh_cn"/"en_us"
        recognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
        recognizer.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
        return recognizer;
    }

    @Override
    public TextResult translate() {
        TextResult result = new TextResult();
        result.setResult("hello world");
        return result;
    }
}
