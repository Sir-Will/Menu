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
import com.gmail.socraticphoenix.sponge.menu.impl.page.AnvilTextPage;
import com.gmail.socraticphoenix.sponge.menu.impl.page.target.TextTarget;
import com.gmail.socraticphoenix.sponge.menu.builder.CustomInventoryBuilder;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.plugin.PluginContainer;

public class AnvilTextFinalizer implements Finalizer<AnvilTextPage, TextTarget> {

    @Override
    public void display(Player player, TextTarget target, AnvilTextPage page, PluginContainer owner) {
        CustomInventoryBuilder builder = new CustomInventoryBuilder();
        builder.of(InventoryArchetypes.ANVIL).property(new InventoryTitle(page.title()));
        Inventory inventory = builder.build(owner.getInstance());
        ItemStack rename = ItemStack.of(ItemTypes.DIAMOND, 1);
        rename.offer(Keys.DISPLAY_NAME, page.title());
        //inventory.offer(rename); currently broken :(
        player.closeInventory(Cause.of(NamedCause.source(MenuPlugin.container()), NamedCause.of("reason", InventoryReason.NEW_PAGE)));
        player.openInventory(inventory, Cause.of(NamedCause.source(MenuPlugin.container()), NamedCause.of("reason", InventoryReason.NEW_PAGE)));
    }

    @Override
    public Class<AnvilTextPage> page() {
        return AnvilTextPage.class;
    }

    @Override
    public Class<TextTarget> target() {
        return TextTarget.class;
    }

}
