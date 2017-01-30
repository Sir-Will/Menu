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
package com.gmail.socraticphoenix.sponge.menu.tracker;

import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.data.map.SerializableMap;
import com.gmail.socraticphoenix.sponge.menu.event.MenuInputEvent;
import org.spongepowered.api.data.DataContainer;

import java.util.function.BiConsumer;

public class TextTracker extends Tracker<MenuInputEvent.Text> {
    private String pageId;
    private String pluginId;

    public TextTracker(Class<MenuInputEvent.Text> event, BiConsumer<SerializableMap, MenuInputEvent.Text> consumer, SerializableMap vars, String pageId, String pluginId, String id) {
        super(event, consumer, vars, id);
        this.pageId = pageId;
        this.pluginId = pluginId;
    }

    public String getPageId() {
        return this.pageId;
    }

    public String getPluginId() {
        return this.pluginId;
    }

    @Override
    public String compositeId() {
        return this.pluginId + ".tracker.text." + this.id() + "." + this.pageId;
    }

    @Override
    public void invoke(MenuInputEvent.Text event) {
        this.getConsumer().accept(this.getVars(), event);
    }

    public DataContainer toContainer() {
        return super.toContainer().set(MenuQueries.TRACKER_PAGE, this.pageId)
                .set(MenuQueries.TRACKER_PLUGIN, this.pluginId);
    }

}
