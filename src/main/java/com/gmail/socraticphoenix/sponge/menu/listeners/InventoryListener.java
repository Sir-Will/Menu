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
package com.gmail.socraticphoenix.sponge.menu.listeners;

import com.gmail.socraticphoenix.sponge.menu.data.attached.button.ButtonData;
import com.gmail.socraticphoenix.sponge.menu.data.attached.button.ImmutableButtonData;
import com.gmail.socraticphoenix.sponge.menu.data.attached.player.MenuData;
import com.gmail.socraticphoenix.sponge.menu.event.ButtonClickEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;
import org.spongepowered.api.util.Tristate;

import java.util.Optional;

public class InventoryListener {

    @Listener(order = Order.EARLY)
    public void onClick(ClickInventoryEvent.Primary ev, @First Player player) {
        ItemStackSnapshot def = ev.getCursorTransaction().getFinal();
        Optional<ImmutableButtonData> snapshotButtonDataOptional = def.get(ImmutableButtonData.class);
        if(snapshotButtonDataOptional.isPresent()) {
            Optional<MenuData> menuDataOptional = player.get(MenuData.class);
            if(menuDataOptional.isPresent()) {
                MenuData menuData = menuDataOptional.get();
                ImmutableButtonData data = snapshotButtonDataOptional.get();
                ButtonClickEvent event = new ButtonClickEvent(player, data.id().get(), data.owner().get(), menuData.context().get(), menuData.menu().get());
                Sponge.getEventManager().post(event);
            }
            ev.setCancelled(true);
            return;
        }

        for(SlotTransaction transaction : ev.getTransactions()) {
            Slot slot = transaction.getSlot();
            Optional<ItemStack> stackOptional = slot.peek();
            if(stackOptional.isPresent()) {
                ItemStack stack = stackOptional.get();
                Optional<ButtonData> dataOptional = stack.get(ButtonData.class);
                if(dataOptional.isPresent()) {
                    Optional<MenuData> menuDataOptional = player.get(MenuData.class);
                    if(menuDataOptional.isPresent()) {
                        MenuData menuData = menuDataOptional.get();
                        ButtonData data = dataOptional.get();
                        ButtonClickEvent event = new ButtonClickEvent(player, data.id().get(), data.owner().get(), menuData.context().get(), menuData.menu().get());
                        Sponge.getEventManager().post(event);
                    }
                    ev.setCancelled(true);
                    return;
                }
            }
        }
    }

    @Listener(order = Order.LATE)
    @IsCancelled(Tristate.FALSE)
    public void onClick(ClickInventoryEvent ev) {
        for(Inventory inventory : ev.getTargetInventory().slots()) {
            if(inventory instanceof Slot) {
                Slot slot = (Slot) inventory;
                Optional<ItemStack> stackOptional = slot.peek();
                if(stackOptional.isPresent()) {
                    ItemStack stack = stackOptional.get();
                    Optional<ButtonData> buttonDataOptional = stack.get(ButtonData.class);
                    if(buttonDataOptional.isPresent()) {
                        ev.setCancelled(true);
                        break;
                    }
                }
            }
        }
    }

}
