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

import com.gmail.socraticphoenix.sponge.menu.Formatter;
import com.gmail.socraticphoenix.sponge.menu.InputType;
import com.gmail.socraticphoenix.sponge.menu.InputTypes;
import com.gmail.socraticphoenix.sponge.menu.Page;
import com.gmail.socraticphoenix.sponge.menu.data.map.SerializableMap;
import com.gmail.socraticphoenix.sponge.menu.event.MenuInputEvent;
import com.gmail.socraticphoenix.sponge.menu.impl.formatter.tree.TreeNode;
import com.gmail.socraticphoenix.sponge.menu.impl.page.AnvilTextPage;
import com.gmail.socraticphoenix.sponge.menu.impl.page.ChatTextPage;
import com.gmail.socraticphoenix.sponge.menu.tracker.TextTracker;
import com.gmail.socraticphoenix.sponge.menu.tracker.Tracker;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class TextPageBuilder {
    private SendableMenuBuilder parent;
    private Text title;
    private String id;
    private List<Tracker> trackers;

    public TextPageBuilder(SendableMenuBuilder parent) {
        this.parent = parent;
        this.title = Text.of("Page");
        this.id = "default_id";
        this.trackers = new ArrayList<>();
    }

    public TextPageBuilder id(String id) {
        this.id = id;
        return this;
    }

    public TextPageBuilder title(Text title) {
        this.title = title;
        return this;
    }

    public StrictGridFormatterBuilder strictGridFormatter() {
        return new StrictGridFormatterBuilder(this.parent, this.id);
    }

    public TextPageBuilder categoricalTextFormatter(TreeNode categories, Text indent) {
        this.parent.categoricalTextFormatter(this.id, categories, indent);
        return this;
    }

    public TextPageBuilder sequentialTextFormatter(Text separator) {
        this.parent.sequentialTextFormatter(this.id, separator);
        return this;
    }

    public TextPageBuilder orderedGridFormatter(boolean vertical) {
        this.parent.orderedGridFormatter(this.id, vertical);
        return this;
    }

    public TextPageBuilder formatter(Formatter formatter) {
        this.parent.formatter(this.id, formatter);
        return this;
    }

    public TextPageBuilder tracker(BiConsumer<SerializableMap, MenuInputEvent.Text> listener, SerializableMap vars, String id) {
        this.trackers.add(new TextTracker(MenuInputEvent.Text.class, listener, vars, this.id, this.parent.getContainer().getId(), id));
        return this;
    }

    public TextPageBuilder tracker(BiConsumer<SerializableMap, MenuInputEvent.Text> listener, String id) {
        return this.tracker(listener, new SerializableMap(), id);
    }

    public SendableMenuBuilder getParent() {
        return this.parent;
    }

    public void finish(InputType type) {
        Page page;
        if(type == InputTypes.ANVIL_TEXT_PAGE) {
            page = new AnvilTextPage(this.title, this.id, this.trackers);
        } else if (type == InputTypes.CHAT_TEXT) {
            page = new ChatTextPage(this.title, this.id, this.trackers);
        } else {
            throw new IllegalArgumentException("Invalid text input type: " + type.getId());
        }
        this.parent.page(page);
    }



}
