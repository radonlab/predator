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

// Object format
// sn: {number}  - sentence
// ls: {boolean} - last sentence
// bg: {number}  - begin
// ed: {number}  - end
// ws: {Array}   - words
//   bg: {number} - begin
//   cw: {Array}  - chinese word
//     w : {string} - word
//     sc: {number} - score

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
