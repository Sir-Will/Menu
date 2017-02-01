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

import com.gmail.socraticphoenix.sponge.menu.impl.finalizer.GridFinalizer;
import com.gmail.socraticphoenix.sponge.menu.impl.page.target.GridTarget;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.plugin.PluginContainer;

/**
 * Represents the final step in the {@link Page} display process. The finalizer takes a {@link Page}, a filled {@link
 * PageTarget} and displays it to the player in the game. For example, the {@link GridFinalizer} takes a {@link
 * GridTarget} and creates a custom {@link Inventory} and forces the player to open that {@link Inventory}.
 *
 * @param <T> The type of {@link Page} this finalizer accepts.
 * @param <K> The type of {@link PageTarget} this finalizer accepts.
 */
public interface Finalizer<T extends Page, K extends PageTarget> {

    /**
     * Displays the given {@link Page}, formatted to the given {@link PageTarget}, to the given {@link Player}.
     *
     * @param player The {@link Player} to display the given {@link Page} to.
     * @param target The formatted {@link Page}.
     * @param page   The {@link Page}.
     * @param owner  The plugin which owns the {@link Menu} being displayed.
     */
    void display(Player player, K target, T page, PluginContainer owner);

    /**
     * @return The type of {@link Page} this finalizer accepts.
     */
    Class<T> page();

    /**
     * @return The type of {@link PageTarget} this finalizer accepts.
     */
    Class<K> target();

}
