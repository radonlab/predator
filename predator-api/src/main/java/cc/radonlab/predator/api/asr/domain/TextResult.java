/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.asr.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
    private static Logger logger = LoggerFactory.getLogger(TextResult.class);
    private static ObjectMapper parser = new ObjectMapper();

    private List<JsonNode> result;

    public TextResult() {
        result = new ArrayList<>();
    }

    public void append(String json) {
        try {
            JsonNode node = parser.readTree(json);
            result.add(node);
        } catch (IOException e) {
            logger.error("Failed to append result", e);
        }
    }

    public List<JsonNode> getResult() {
        return result;
    }

    public String getOverview() {
        StringBuilder sb = new StringBuilder();
        for (JsonNode node : result) {
            Iterable<JsonNode> ws = node.get("ws");
            for (JsonNode w : ws) {
                Iterable<JsonNode> cw = w.get("cw");
                for (JsonNode word : cw) {
                    String s = word.get("w").asText();
                    sb.append(s);
                }
            }
        }
        return sb.toString();
    }
}
