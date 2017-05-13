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
package com.gmail.socraticphoenix.sponge.menu.impl.tracker;

import com.gmail.socraticphoenix.sponge.menu.Tracker;
import com.gmail.socraticphoenix.sponge.menu.data.map.SerializableMap;
import com.gmail.socraticphoenix.sponge.menu.event.TargetMenuEvent;

import java.util.function.BiConsumer;

public class GeneralTracker<T extends TargetMenuEvent> extends Tracker<T> {
    private String pluginId;
    private String menuId;

    public GeneralTracker(Class<T> event, BiConsumer<SerializableMap, T> consumer, SerializableMap vars, String id, String pluginId, String menuId) {
        super(event, consumer, vars, id);
        this.pluginId = pluginId;
        this.menuId = menuId;
    }

    public String getMenuId() {
        return this.menuId;
    }

    @Override
    public String compositeId() {
        return this.pluginId + ".tracker.general." + this.id() + "." + this.menuId;
    }

    @Override
    public void invoke(T event) {
        this.getConsumer().accept(this.getVars(), event);
    }

}
