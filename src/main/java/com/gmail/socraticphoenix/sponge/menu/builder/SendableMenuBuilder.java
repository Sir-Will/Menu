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
import com.gmail.socraticphoenix.sponge.menu.impl.formatter.tree.TreeNode;
import com.gmail.socraticphoenix.sponge.menu.impl.menu.SimpleMenu;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SendableMenuBuilder {
    private List<Page> pages;
    private Object plugin;
    private PluginContainer container;
    private Map<String, Formatter> specificFormatters;
    private Set<Formatter> formatters;
    private MenuProperties properties;

    public SendableMenuBuilder(Object plugin) {
        this.container = Sponge.getPluginManager().fromInstance(plugin).orElseThrow(() -> new IllegalArgumentException(plugin + " is not a plugin instance"));
        this.pages = new ArrayList<>();
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

    public SendableMenuBuilder categoricalTextFormatter(TreeNode categories, Text indent) {
        return this.formatter(Formatter.categoricalText(this.plugin, categories, indent));
    }

    public SendableMenuBuilder categoricalTextFormatter(String page, TreeNode categories, Text indent) {
        return this.formatter(page, Formatter.categoricalText(this.plugin, categories, indent));
    }

    public SendableMenuBuilder sequentialTextFormatter(Text separator) {
        return this.formatter(Formatter.sequentialText(this.plugin, separator));
    }

    public SendableMenuBuilder sequentialTextFormatter(String page, Text separator) {
        return this.formatter(page, Formatter.sequentialText(this.plugin, separator));
    }

    public SendableMenuBuilder orderedGridFormatter(boolean vertical) {
        return this.formatter(Formatter.orderedGrid(this.plugin, vertical));
    }

    public SendableMenuBuilder orderedGridFormatter(String page, boolean vertical) {
        return this.formatter(page, Formatter.orderedGrid(this.plugin, vertical));
    }

    public SendableMenuBuilder formatter(String page, Formatter formatter) {
        this.specificFormatters.put(page, formatter);
        return this;
    }

    public SendableMenuBuilder formatter(Formatter formatter) {
        this.formatters.add(formatter);
        return this;
    }

    public SendableMenuBuilder properties(MenuProperties properties) {
        this.properties = properties;
        return this;
    }

    public SendableMenuBuilder page(Page page) {
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
        return new SendableMenu(new SimpleMenu(this.pages), this.plugin, this.specificFormatters, this.formatters, this.properties);
    }
}
