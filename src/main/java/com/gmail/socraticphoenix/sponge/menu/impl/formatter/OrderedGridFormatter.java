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
package com.gmail.socraticphoenix.sponge.menu.impl.formatter;

import com.gmail.socraticphoenix.sponge.menu.Button;
import com.gmail.socraticphoenix.sponge.menu.Formatter;
import com.gmail.socraticphoenix.sponge.menu.data.DataApplicator;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.impl.page.InventoryButtonPage;
import com.gmail.socraticphoenix.sponge.menu.impl.page.target.GridTarget;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.Iterator;

public class OrderedGridFormatter extends Formatter<InventoryButtonPage, GridTarget> {
    private boolean vertical;

    public OrderedGridFormatter(Object plugin, boolean vertical) {
        super(InventoryButtonPage.class, GridTarget.class, plugin);
        this.vertical = vertical;
    }

    public OrderedGridFormatter(PluginContainer plugin, boolean vertical) {
        super(InventoryButtonPage.class, GridTarget.class, plugin);
        this.vertical = vertical;
    }

    @Override
    public void format(InventoryButtonPage page, GridTarget target, PluginContainer owner) {
        Iterator<Button> iterator = page.buttons().iterator();
        if(this.vertical) {
            for (int x = 0; x < target.length(); x++) {
                if (!iterator.hasNext()) {
                    break;
                }
                for (int y = 0; y < target.height(); y++) {
                    if (!iterator.hasNext()) {
                        break;
                    }

                    target.set(x, y, DataApplicator.createIcon(iterator.next(), owner));
                }
            }
        } else {
            for (int y = 0; y < target.height(); y++) {
                if (!iterator.hasNext()) {
                    break;
                }
                for (int x = 0; x < target.length(); x++) {
                    if (!iterator.hasNext()) {
                        break;
                    }

                    target.set(x, y, DataApplicator.createIcon(iterator.next(), owner));
                }
            }
        }
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
                .set(MenuQueries.ORDERED_GRID_VERTICAL, this.vertical);
    }
}
