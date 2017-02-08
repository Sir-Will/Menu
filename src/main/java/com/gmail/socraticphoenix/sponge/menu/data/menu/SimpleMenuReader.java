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
package com.gmail.socraticphoenix.sponge.menu.data.menu;

import com.gmail.socraticphoenix.sponge.menu.Menu;
import com.gmail.socraticphoenix.sponge.menu.MenuType;
import com.gmail.socraticphoenix.sponge.menu.MenuTypes;
import com.gmail.socraticphoenix.sponge.menu.Page;
import com.gmail.socraticphoenix.sponge.menu.Tracker;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.impl.menu.SimpleMenu;
import org.spongepowered.api.data.DataView;

import java.util.Optional;

public class SimpleMenuReader implements MenuReader {

    @Override
    public Optional<Menu> read(MenuType type, DataView container) {
        if(type == MenuTypes.SIMPLE && container.contains(MenuQueries.MENU_PAGES, MenuQueries.MENU_ID, MenuQueries.MENU_TRACKERS)) {
            return Optional.of(new SimpleMenu(container.getString(MenuQueries.MENU_ID).get(), container.getSerializableList(MenuQueries.MENU_PAGES, Page.class).get(), container.getSerializableList(MenuQueries.MENU_TRACKERS, Tracker.class).get()));
        }
        return null;
    }

}
