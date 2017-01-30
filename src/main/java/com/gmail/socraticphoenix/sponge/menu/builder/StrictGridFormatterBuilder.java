/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 socraticphoenix@gmail.com
 * Copyright (c) 2016 contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.gmail.socraticphoenix.sponge.menu.builder;

import com.flowpowered.math.vector.Vector2i;
import com.gmail.socraticphoenix.sponge.menu.impl.formatter.StrictGridFormatter;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrictGridFormatterBuilder {
    private SendableMenuBuilder parent;
    private String pageId;

    private Map<String, List<Vector2i>> locations;
    private Object plugin;

    public StrictGridFormatterBuilder(SendableMenuBuilder parent, String pageId) {
        this.parent = parent;
        this.locations = new HashMap<>();
        this.plugin = parent.getPlugin();
        this.pageId = pageId;
    }

    public StrictGridFormatterBuilder set(String button, Vector2i... locs) {
        this.locations.put(button, Lists.newArrayList(locs));
        return this;
    }

    public StrictGridFormatterBuilder add(String button, Vector2i loc) {
        if(!this.locations.containsKey(button)) {
            this.locations.put(button, new ArrayList<>());
        }
        this.locations.get(button).add(loc);
        return this;
    }

    public StrictGridFormatterBuilder add(String button, int x, int y) {
        return this.add(button, new Vector2i(x, y));
    }

    public void finish() {
        if(this.pageId == null) {
            this.parent.formatter(new StrictGridFormatter(this.locations, this.plugin));
        } else {
            this.parent.formatter(this.pageId, new StrictGridFormatter(this.locations, this.plugin));
        }
    }

}
