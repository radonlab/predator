/*
 * Copyright (C) 2017, Skyler.
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package cc.radonlab.predator.api.stt;

import cc.radonlab.predator.api.stt.domain.TextResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that provides capability of converting speech to text.
 */
@RestController
@RequestMapping("/stt")
public class SttController {
    public SttController() {
    }

    @GetMapping
    public TextResult convert() {
        return new TextResult();
    }
}
