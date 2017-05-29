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

import com.gmail.socraticphoenix.sponge.menu.EndMenuReason;
import com.gmail.socraticphoenix.sponge.menu.InventoryReason;
import com.gmail.socraticphoenix.sponge.menu.Page;
import com.gmail.socraticphoenix.sponge.menu.data.attached.button.ButtonData;
import com.gmail.socraticphoenix.sponge.menu.data.attached.button.ImmutableButtonData;
import com.gmail.socraticphoenix.sponge.menu.data.attached.player.MenuData;
import com.gmail.socraticphoenix.sponge.menu.event.MenuInputEvent;
import com.gmail.socraticphoenix.sponge.menu.impl.page.AnvilTextPage;
import com.gmail.socraticphoenix.sponge.menu.impl.page.InventoryButtonPage;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Container;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.transaction.SlotTransaction;
import org.spongepowered.api.util.Tristate;

import java.util.Optional;

public class InventoryListener {

    @Listener
    public void onClose(InteractInventoryEvent.Close ev, @First Player player) {
        if(!ev.getCause().containsType(EndMenuReason.class) && !ev.getCause().containsType(InventoryReason.class) && isInInventoryMenu(player)) {
            MenuData data = player.get(MenuData.class).get();
            data.context().get().terminate(EndMenuReason.QUIT, player, data.menu().get());
        }
    }

    @Listener(order = Order.EARLY)
    public void onClick(ClickInventoryEvent ev, @First Player player, @Getter("getTargetInventory") Container container) {
        if (ev instanceof ClickInventoryEvent.Primary || ev instanceof ClickInventoryEvent.Secondary) {
            if (player.get(MenuData.class).isPresent() && player.get(MenuData.class).get().getCurrentPage().isPresent()) {
                boolean isPrimaryClick = ev instanceof ClickInventoryEvent.Primary;
                if (container.getArchetype() == InventoryArchetypes.UNKNOWN && player.get(MenuData.class).get().getCurrentPage().get() instanceof InventoryButtonPage) {
                    ItemStackSnapshot def = ev.getCursorTransaction().getFinal();
                    Optional<ImmutableButtonData> snapshotButtonDataOptional = def.get(ImmutableButtonData.class);
                    if (snapshotButtonDataOptional.isPresent()) {
                        MenuData menuData = player.get(MenuData.class).get();
                        ImmutableButtonData data = snapshotButtonDataOptional.get();
                        MenuInputEvent event = new MenuInputEvent.Button(player, data.id().get(), data.owner().get(), menuData.context().get(), menuData.menu().get(), isPrimaryClick);
                        Sponge.getEventManager().post(event);
                        return;
                    }

                    for (SlotTransaction transaction : ev.getTransactions()) {
                        Slot slot = transaction.getSlot();
                        Optional<ItemStack> stackOptional = slot.peek();
                        if (stackOptional.isPresent()) {
                            ItemStack stack = stackOptional.get();
                            Optional<ButtonData> dataOptional = stack.get(ButtonData.class);
                            if (dataOptional.isPresent()) {
                                MenuData menuData = player.get(MenuData.class).get();
                                ButtonData data = dataOptional.get();
                                MenuInputEvent.Button event = new MenuInputEvent.Button(player, data.id().get(), data.owner().get(), menuData.context().get(), menuData.menu().get(), isPrimaryClick);
                                Sponge.getEventManager().post(event);
                            }
                            return;
                        }
                    }
                } else if (container.getArchetype() == InventoryArchetypes.ANVIL && player.get(MenuData.class).get().getCurrentPage().get() instanceof AnvilTextPage) {
                    ItemStackSnapshot snapshot = ev.getCursorTransaction().getFinal();
                    if (snapshot.getType() != ItemTypes.NONE) {
                        MenuData menuData = player.get(MenuData.class).get();
                        MenuInputEvent event = new MenuInputEvent.Text(player, menuData.context().get(), menuData.menu().get(), snapshot.get(Keys.DISPLAY_NAME).isPresent() ? snapshot.get(Keys.DISPLAY_NAME).get().toPlain() : snapshot.getType().getTranslation().get());
                        Sponge.getEventManager().post(event);
                        return;
                    }
                }
            }
        }
    }

    @Listener(order = Order.LATE)
    @IsCancelled(Tristate.FALSE)
    public void onClick(ClickInventoryEvent ev, @First Player player) {
        if(isInInventoryMenu(player)) {
            ev.setCancelled(true);
        }
    }

    @Listener(order = Order.LATE)
    @IsCancelled(Tristate.FALSE)
    public void onDrop(DropItemEvent ev, @First Player player) {
        if(isInInventoryMenu(player)) {
            ev.setCancelled(true);
        }
    }

    private static boolean isInInventoryMenu(Player player) {
        Optional<MenuData> dataOptional = player.get(MenuData.class);
        if(dataOptional.isPresent()) {
            MenuData data = dataOptional.get();
            Optional<Page> pageOptional = data.getCurrentPage();
            if(pageOptional.isPresent()) {
                Page page = pageOptional.get();
                return page.isInventoryBased() && player.isViewingInventory();
            }
        }

        return false;
    }

}
