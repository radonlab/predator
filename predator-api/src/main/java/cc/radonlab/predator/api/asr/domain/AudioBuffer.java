/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.domain;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class AudioBuffer {
    private String name;
    private byte[] buffer;
    private String contentType;

    public static AudioBuffer getBuffer(MultipartFile file) throws IOException {
        AudioBuffer audioBuffer = new AudioBuffer();
        audioBuffer.name = file.getName();
        audioBuffer.buffer = file.getBytes();
        audioBuffer.contentType = file.getContentType();
        return audioBuffer;
    }

    private AudioBuffer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
