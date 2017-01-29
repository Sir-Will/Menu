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
package com.gmail.socraticphoenix.sponge.menu.impl.finalizer;

import com.gmail.socraticphoenix.sponge.menu.Finalizer;
import com.gmail.socraticphoenix.sponge.menu.InventoryReason;
import com.gmail.socraticphoenix.sponge.menu.MenuPlugin;
import com.gmail.socraticphoenix.sponge.menu.impl.page.InventoryButtonPage;
import com.gmail.socraticphoenix.sponge.menu.impl.page.target.GridTarget;
import com.gmail.socraticphoenix.sponge.menu.util.CustomInventoryBuilder;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotPos;
import org.spongepowered.api.plugin.PluginContainer;

public class GridFinalizer implements Finalizer<InventoryButtonPage, GridTarget> {

    @Override
    public void display(Player player, GridTarget target, InventoryButtonPage page, PluginContainer owner) {
        CustomInventoryBuilder builder = new CustomInventoryBuilder();
        builder.of(InventoryArchetypes.CHEST).property(new InventoryDimension(9, target.height()))
                .property(new InventoryTitle(page.title()));
        int offset = (9 - target.length()) / 2;
        offset = offset < 0 ? 0 : offset;

        Inventory inventory = builder.build(owner.getInstance().get());
        for (int x = 0; x < target.length(); x++) {
            for (int y = 0; y < target.height(); y++) {
                Inventory slot = inventory.query(new SlotPos(x + offset, y)).query(Slot.class);
                if(slot instanceof Slot) {
                    target.get(x, y).ifPresent(slot::set);
                }
            }
        }

        player.openInventory(inventory, Cause.of(NamedCause.source(MenuPlugin.container()), NamedCause.of("reason", InventoryReason.OPEN_MENU)));
    }

    @Override
    public Class<InventoryButtonPage> page() {
        return InventoryButtonPage.class;
    }

    @Override
    public Class<GridTarget> target() {
        return GridTarget.class;
    }

}
