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

import com.gmail.socraticphoenix.sponge.menu.MenuPlugin;
import com.gmail.socraticphoenix.sponge.menu.data.attached.player.MenuData;
import com.gmail.socraticphoenix.sponge.menu.event.MenuStateEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.scheduler.Task;

import java.util.Optional;
import java.util.function.Consumer;

public class ChatRestrictTask implements Consumer<Task> {
    private Player player;
    private boolean cancelled;

    public ChatRestrictTask(Player player) {
        this.player = player;
        Sponge.getEventManager().registerListeners(MenuPlugin.instance(), this);
    }

    @Override
    public void accept(Task task) {
        if(this.cancelled) {
            task.cancel();
            Sponge.getEventManager().unregisterListeners(this);
        } else {
            Optional<MenuData> dataOptional = this.player.get(MenuData.class);
            if(dataOptional.isPresent()) {
                MenuData data = dataOptional.get();
                if(data.getCurrentPage().isPresent() && data.getCurrentPage().get().isChatBased()) {
                    data.context().get().refresh(this.player, data.menu().get());
                }
            } else {
                task.cancel();
            }
        }
    }


    @Listener
    public void onMenuEnd(MenuStateEvent.Close ev, @First Player player) {
        if(player.getUniqueId().equals(this.player.getUniqueId())) {
            this.cancelled = true;
        }
    }

}
