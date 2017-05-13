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

import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.data.map.SerializableMap;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Holds contextual information about a {@link Menu}.
 */
public interface MenuContext extends DataSerializable {
    /**
     * A MenuContext with the type {@link MenuTypes#EMPTY}.
     */
    MenuContext.Empty EMPTY = new Empty();

    /**
     * @return The index of the current page.
     */
    int page();

    /**
     * @return The {@link InputContext} associated with this menu context.
     */
    InputContext input();

    /**
     * @return The {@link MenuType} of this menu context.
     */
    MenuType type();

    /**
     * @return The plugin which owns this menu context.
     */
    PluginContainer owner();

    /**
     * @return The set of {@link Formatter Formatters} to apply to {@link Page Pages} when displaying them to the
     * player.
     */
    Set<Formatter> formatters();

    /**
     * @return The set of variables maintained in relation to the {@link Menu} instance. This map can be used to store
     * information about a specific menu across different event calls.
     */
    SerializableMap variables();

    /**
     * @return The {@link MenuProperties} associated with this menu context.
     */
    MenuProperties properties();

    /**
     * Refreshes this menu context for the given player, using the pages in the given {@link Menu}.
     *
     * @param player The player to refresh the {@link Menu} for.
     * @param menu   The {@link Menu} to pull pages from.
     */
    void refresh(Player player, Menu menu);

    /**
     * Refreshes this menu context for the given player, using the pages in the given {@link Menu}. This method will
     * attempt to apply the next page to the menu setup by the previous page.
     *
     * @param player The player to refresh the {@link Menu} for.
     * @param menu   The {@link Menu} to pull pages from.
     */
    void silentRefresh(Player player, Menu menu);

    /**
     * Terminates the current {@link Menu} instance, for the given player, for the given reason.
     *
     * @param reason The reason the {@link Menu} instance is being terminated.
     * @param player The player to terminate the {@link Menu} for.
     * @param menu   The {@link Menu} to terminate.
     */
    void terminate(EndMenuReason reason, Player player, Menu menu);

    /**
     * Sets the current page.
     *
     * @param page The new current page.
     */
    void setPage(int page);

    /**
     * Returns the current {@link Page}, as defined by {@link #page()}, from the given {@link Menu}. If {@link #page()}
     * is out of bounds, {@link Optional#empty()} is returned.
     *
     * @param menu The {@link Menu} to pull the page from.
     *
     * @return The current {@link Page}, if {@link #page()} is in bounds.
     */
    default Optional<Page> getCurrentPage(Menu menu) {
        return this.page() >= 0 && this.page() < menu.pages().size() ? Optional.of(menu.pages().get(this.page())) : Optional.empty();
    }

    /**
     * Sets the current page to {@code this.page() + 1}
     */
    default void nextPage() {
        setPage(page() + 1);
    }

    class Empty implements MenuContext {

        @Override
        public int page() {
            return 0;
        }

        @Override
        public InputContext input() {
            return InputContext.EMPTY;
        }

        @Override
        public MenuType type() {
            return MenuTypes.EMPTY;
        }

        @Override
        public PluginContainer owner() {
            return MenuPlugin.container();
        }

        @Override
        public Set<Formatter> formatters() {
            return Collections.emptySet();
        }

        @Override
        public SerializableMap variables() {
            return new SerializableMap();
        }

        @Override
        public MenuProperties properties() {
            return new MenuProperties(false, false);
        }

        @Override
        public void refresh(Player player, Menu menu) {

        }

        @Override
        public void silentRefresh(Player player, Menu menu) {

        }

        @Override
        public void terminate(EndMenuReason reason, Player player, Menu menu) {

        }

        @Override
        public void setPage(int page) {

        }


        @Override
        public int getContentVersion() {
            return 1;
        }

        @Override
        public DataContainer toContainer() {
            return new MemoryDataContainer().set(Queries.CONTENT_VERSION, this.getContentVersion())
                    .set(MenuQueries.CONTEXT_MENU_TYPE, this.type());
        }
    }

}
