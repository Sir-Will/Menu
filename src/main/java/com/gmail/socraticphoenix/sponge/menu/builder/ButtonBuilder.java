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

import com.gmail.socraticphoenix.sponge.menu.Button;
import com.gmail.socraticphoenix.sponge.menu.data.map.SerializableMap;
import com.gmail.socraticphoenix.sponge.menu.event.MenuInputEvent;
import com.gmail.socraticphoenix.sponge.menu.impl.button.ItemButton;
import com.gmail.socraticphoenix.sponge.menu.impl.button.TextButton;
import com.gmail.socraticphoenix.sponge.menu.tracker.ButtonTracker;
import com.gmail.socraticphoenix.sponge.menu.tracker.Tracker;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

public class ButtonBuilder {
    private ButtonPageBuilder parent;
    private PluginContainer plugin;
    private ItemStack icon;
    private Text title;
    private String id;
    private List<Tracker> trackers;

    public ButtonBuilder(ButtonPageBuilder parent) {
        this.parent = parent;
        this.title = Text.of("Button");
        this.id = "default_id";
        this.plugin = Sponge.getPluginManager().fromInstance(parent).orElseThrow(() -> new IllegalArgumentException(plugin + " is not a plugin instance"));
        this.trackers = new ArrayList<>();
    }

    public ButtonBuilder icon(ItemStack icon) {
        this.icon = icon;
        return this;
    }

    public ButtonBuilder icon(ItemType type, int quantity) {
        return this.icon(ItemStack.of(type, quantity));
    }

    public ButtonBuilder icon(ItemType type) {
        return this.icon(type, 1);
    }

    public ButtonBuilder title(Text title) {
        this.title = title;
        return this;
    }

    public ButtonBuilder title(Objects... elements) {
        return this.title(Text.of(elements));
    }

    public ButtonBuilder id(String id) {
        this.id = id;
        return this;
    }

    public ButtonBuilder tracker(BiConsumer<SerializableMap, MenuInputEvent.Button> listener, SerializableMap vars, String id) {
        this.trackers.add(new ButtonTracker(MenuInputEvent.Button.class, listener, vars, this.id, this.plugin.getId(), id));
        return this;
    }

    public ButtonBuilder tracker(BiConsumer<SerializableMap, MenuInputEvent.Button> listener, String id) {
        return this.tracker(listener, new SerializableMap(), id);
    }

    public void finish() {
        Button button;
        if(this.icon != null) {
            button = new ItemButton(this.title, this.icon, this.trackers, this.id);
        } else {
            button = new TextButton(this.title, this.id, this.trackers);
        }

    }

}
