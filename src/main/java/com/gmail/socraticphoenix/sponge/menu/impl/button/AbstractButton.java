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
package com.gmail.socraticphoenix.sponge.menu.impl.button;

import com.gmail.socraticphoenix.sponge.menu.Button;
import com.gmail.socraticphoenix.sponge.menu.ButtonType;
import com.gmail.socraticphoenix.sponge.menu.MenuRegistry;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.data.tracker.DummyConsumer;
import com.gmail.socraticphoenix.sponge.menu.Tracker;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractButton implements Button {
    private List<Tracker> trackers;
    private String id;
    private Text title;
    private ButtonType type;
    private ItemStack icon;


    public AbstractButton(List<Tracker> trackers, String id, Text title, ButtonType type, ItemStack icon) {
        trackers = trackers.stream().filter(t -> !(t.getConsumer() instanceof DummyConsumer)).collect(Collectors.toList());
        this.trackers = Collections.unmodifiableList(trackers);
        trackers.forEach(MenuRegistry::addTracker);
        this.id = id;
        this.title = title;
        this.type = type;
        this.icon = icon;
    }


    @Override
    public List<Tracker> trackers() {
        return this.trackers;
    }

    @Override
    public Text title() {
        return this.title;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public ButtonType type() {
        return this.type;
    }

    @Override
    public ItemStack icon() {
        return this.icon;
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer().set(Queries.CONTENT_VERSION, this.getContentVersion())
                .set(MenuQueries.BUTTON_TYPE, this.type())
                .set(MenuQueries.BUTTON_TITLE, this.title())
                .set(MenuQueries.BUTTON_TRACKERS, this.trackers());
    }

}
