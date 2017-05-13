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
import com.gmail.socraticphoenix.sponge.menu.MenuProperties;
import com.gmail.socraticphoenix.sponge.menu.Page;
import com.gmail.socraticphoenix.sponge.menu.SendableMenu;
import com.gmail.socraticphoenix.sponge.menu.Tracker;
import com.gmail.socraticphoenix.sponge.menu.data.map.SerializableMap;
import com.gmail.socraticphoenix.sponge.menu.event.MenuStateEvent;
import com.gmail.socraticphoenix.sponge.menu.impl.formatter.tree.TreeNode;
import com.gmail.socraticphoenix.sponge.menu.impl.menu.SimpleMenu;
import com.gmail.socraticphoenix.sponge.menu.impl.tracker.GeneralTracker;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;


public class MenuBuilder {
    private List<Page> pages;
    private List<Tracker> trackers;
    private String id;
    private Object plugin;
    private PluginContainer container;
    private Map<String, Formatter> specificFormatters;
    private Set<Formatter> formatters;
    private MenuProperties properties;

    public MenuBuilder(Object plugin) {
        this.container = Sponge.getPluginManager().fromInstance(plugin).orElseThrow(() -> new IllegalArgumentException(plugin + " is not a plugin instance"));
        this.pages = new ArrayList<>();
        this.trackers = new ArrayList<>();
        this.id = "default_id";
        this.plugin = plugin;
        this.specificFormatters = new HashMap<>();
        this.formatters = new LinkedHashSet<>();
        this.properties = new MenuProperties(true, true);
    }

    public Object getPlugin() {
        return this.plugin;
    }

    public PluginContainer getContainer() {
        return this.container;
    }

    public StrictGridFormatterBuilder strictGridFormatter() {
        return new StrictGridFormatterBuilder(this, null);
    }

    public StrictGridFormatterBuilder strictGridFormatter(String id) {
        return new StrictGridFormatterBuilder(this, id);
    }

    public MenuBuilder categoricalTextFormatter(TreeNode categories, Text indent) {
        return this.formatter(Formatter.categoricalText(this.plugin, categories, indent));
    }

    public MenuBuilder categoricalTextFormatter(String page, TreeNode categories, Text indent) {
        return this.formatter(page, Formatter.categoricalText(this.plugin, categories, indent));
    }

    public MenuBuilder sequentialTextFormatter(Text separator) {
        return this.formatter(Formatter.sequentialText(this.plugin, separator));
    }

    public MenuBuilder sequentialTextFormatter(String page, Text separator) {
        return this.formatter(page, Formatter.sequentialText(this.plugin, separator));
    }

    public MenuBuilder orderedGridFormatter(boolean vertical) {
        return this.formatter(Formatter.orderedGrid(this.plugin, vertical));
    }

    public MenuBuilder orderedGridFormatter(String page, boolean vertical) {
        return this.formatter(page, Formatter.orderedGrid(this.plugin, vertical));
    }

    public MenuBuilder formatter(String page, Formatter formatter) {
        this.specificFormatters.put(page, formatter);
        return this;
    }

    public MenuBuilder formatter(Formatter formatter) {
        this.formatters.add(formatter);
        return this;
    }

    public MenuBuilder onOpen(BiConsumer<SerializableMap, MenuStateEvent.Open> listener, SerializableMap vars, String id) {
        return this.tracker(new GeneralTracker<>(MenuStateEvent.Open.class, listener, vars, id, this.getContainer().getId(), this.id));
    }

    public MenuBuilder onOpen(BiConsumer<SerializableMap, MenuStateEvent.Open> listener, String id) {
        return this.onOpen(listener, new SerializableMap(), id);
    }

    public MenuBuilder onClose(BiConsumer<SerializableMap, MenuStateEvent.Close> listener, SerializableMap vars, String id) {
        return this.tracker(new GeneralTracker<>(MenuStateEvent.Close.class, listener, vars, id, this.getContainer().getId(), this.id));
    }

    public MenuBuilder onClose(BiConsumer<SerializableMap, MenuStateEvent.Close> listener, String id) {
        return this.onClose(listener, new SerializableMap(), id);
    }

    public MenuBuilder onOpenPre(BiConsumer<SerializableMap, MenuStateEvent.Open.Pre> listener, SerializableMap vars, String id) {
        return this.tracker(new GeneralTracker<>(MenuStateEvent.Open.Pre.class, listener, vars, id, this.getContainer().getId(), this.id));
    }

    public MenuBuilder onOpenPre(BiConsumer<SerializableMap, MenuStateEvent.Open.Pre> listener, String id) {
        return this.onOpenPre(listener, new SerializableMap(), id);
    }

    public MenuBuilder onClosePre(BiConsumer<SerializableMap, MenuStateEvent.Close.Pre> listener, SerializableMap vars, String id) {
        return this.tracker(new GeneralTracker<>(MenuStateEvent.Close.Pre.class, listener, vars, id, this.getContainer().getId(), this.id));
    }

    public MenuBuilder onClosePre(BiConsumer<SerializableMap, MenuStateEvent.Close.Pre> listener, String id) {
        return this.onClosePre(listener, new SerializableMap(), id);
    }

    public MenuBuilder tracker(Tracker tracker) {
        this.trackers.add(tracker);
        return this;
    }

    public MenuBuilder id(String id) {
        this.id = id;
        return this;
    }

    public MenuBuilder properties(MenuProperties properties) {
        this.properties = properties;
        return this;
    }

    public MenuBuilder page(Page page) {
        this.pages.add(page);
        return this;
    }

    public TextPageBuilder textPage() {
        return new TextPageBuilder(this);
    }

    public ButtonPageBuilder buttonPage() {
        return new ButtonPageBuilder(this);
    }

    public SendableMenu build() {
        return new SendableMenu(new SimpleMenu(this.id, this.pages, this.trackers), this.plugin, this.specificFormatters, this.formatters, this.properties);
    }
}
