/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class TextResult {
    private static ObjectMapper parser = new ObjectMapper();

    private List<JsonNode> result;

    public TextResult() {
        result = new ArrayList<>();
    }

    public void append(String json) {
    }

    public List<JsonNode> getResult() {
        return result;
    }

    public void setResult(List<JsonNode> result) {
        this.result = result;
    }
}
