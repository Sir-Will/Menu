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
package com.gmail.socraticphoenix.sponge.menu;

import com.flowpowered.math.vector.Vector2i;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.impl.formatter.CategoricalTextFormatter;
import com.gmail.socraticphoenix.sponge.menu.impl.formatter.OrderedGridFormatter;
import com.gmail.socraticphoenix.sponge.menu.impl.formatter.SequentialTextFormatter;
import com.gmail.socraticphoenix.sponge.menu.impl.formatter.StrictGridFormatter;
import com.gmail.socraticphoenix.sponge.menu.impl.formatter.tree.TreeNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.Map;

/**
 * This class accepts a {@link Page} and a {@link PageTarget}, and formats the {@link Page} to the {@link PageTarget}.
 *
 * @param <T> The type of {@link Page} this formatter accepts.
 * @param <K> The type of {@link PageTarget} this formatter accepts.
 */
public abstract class Formatter<T extends Page, K extends PageTarget> implements DataSerializable {
    private Class<T> page;
    private Class<K> target;
    private PluginContainer plugin;

    protected Formatter(Class<T> page, Class<K> target, Object plugin) {
        this(page, target, Sponge.getPluginManager().fromInstance(plugin).orElseThrow(() -> new IllegalArgumentException(plugin + " is not a plugin instance")));
    }

    protected Formatter(Class<T> page, Class<K> target, PluginContainer plugin) {
        this.page = page;
        this.target = target;
        this.plugin = plugin;
    }

    /**
     * Creates a new {@link CategoricalTextFormatter} and returns it.
     *
     * @param plugin     The plugin that owns the new {@link Formatter}.
     * @param categories The categories the {@link Formatter} should use.
     * @param indent     The indent the {@link Formatter} should use.
     *
     * @return A new {@link Formatter}.
     */
    public static Formatter categoricalText(Object plugin, TreeNode categories, Text indent) {
        return new CategoricalTextFormatter(plugin, categories, indent);
    }

    /**
     * Creates a new {@link SequentialTextFormatter} and returns it.
     *
     * @param plugin    The plugin that owns the new {@link Formatter}.
     * @param separator The separator used by the {@link Formatter}.
     *
     * @return A new {@link Formatter}.
     */
    public static Formatter sequentialText(Object plugin, Text separator) {
        return new SequentialTextFormatter(plugin, separator);
    }

    /**
     * Creates a new {@link OrderedGridFormatter} and returns it.
     *
     * @param plugin   The plugin that owns the new {@link Formatter}.
     * @param vertical If true, items will be gridded in vertically, otherwise they will be gridded in horizontally.
     *
     * @return A new {@link Formatter}.
     */
    public static Formatter orderedGrid(Object plugin, boolean vertical) {
        return new OrderedGridFormatter(plugin, vertical);
    }

    /**
     * Creates a new {@link StrictGridFormatter} and returns it.
     *
     * @param plugin    The plugin that owns the new {@link Formatter}.
     * @param locations The button id to grid location map.
     *
     * @return A new {@link Formatter}.
     */
    public static Formatter strictGrid(Object plugin, Map<String, List<Vector2i>> locations) {
        return new StrictGridFormatter(locations, plugin);
    }

    /**
     * Takes the given {@link Page} and formats it to the given {@link PageTarget}.
     *
     * @param page   The {@link Page} to format.
     * @param target THe {@link PageTarget} to format the {@link Page} to.
     * @param owner  The plugin that owns the given {@link Page}.
     */
    public abstract void format(T page, K target, PluginContainer owner);

    /**
     * @return The type of {@link Page} this formatter accepts.
     */
    public Class<T> page() {
        return this.page;
    }

    /**
     * @return The type of {@link PageTarget} this formatter accepts.
     */
    public Class<K> target() {
        return this.target;
    }

    /**
     * @return The owner of this formatter.
     */
    public PluginContainer owner() {
        return this.plugin;
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer().set(Queries.CONTENT_VERSION, this.getContentVersion())
                .set(MenuQueries.FORMATTER_OWNER, this.plugin.getId())
                .set(MenuQueries.FORMATTER_PAGE, this.page.getName())
                .set(MenuQueries.FORMATTER_TARGET, this.target.getName());
    }

}
