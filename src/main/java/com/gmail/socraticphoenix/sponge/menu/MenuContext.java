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
import com.gmail.socraticphoenix.sponge.menu.impl.menu.context.SimpleMenuContext;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.Collections;
import java.util.Set;

public interface MenuContext extends DataSerializable {
    MenuContext.Empty EMPTY = new Empty();

    static MenuContext deserialize(DataView view, DataQuery query) {
        return view.getSerializable(query, SimpleMenuContext.class).isPresent() ? view.getSerializable(query, SimpleMenuContext.class).get() :
                MenuContext.EMPTY;
    }

    int page();

    InputContext input();

    Menu.Type type();

    PluginContainer owner();

    Set<Formatter> formatters();

    MenuVariables variables();

    void refresh(Player player, Menu menu);

    void terminate(EndMenuReason reason, Player player, Menu menu);

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
        public Menu.Type type() {
            return Menu.Type.EMPTY;
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
        public MenuVariables variables() {
            return new MenuVariables();
        }

        @Override
        public void refresh(Player player, Menu menu) {

        }

        @Override
        public void terminate(EndMenuReason reason, Player player, Menu menu) {

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
