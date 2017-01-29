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

import com.gmail.socraticphoenix.sponge.menu.data.attached.player.MenuData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.util.Optional;

public class PlayerListener {

    @Listener
    public void onJoin(ClientConnectionEvent.Join ev, @First Player player) {
        Optional<MenuData> dataOptional = player.get(MenuData.class);
        if (dataOptional.isPresent()) {
            if (dataOptional.get().context().get().properties().isPersistent()) {
                MenuData data = dataOptional.get();
                data.context().get().refresh(player, data.menu().get());
            } else {
                player.remove(MenuData.class);
            }
        }
    }

    @Listener
    public void onLeave(ClientConnectionEvent.Disconnect ev, @First Player player) {
        Optional<MenuData> dataOptional = player.get(MenuData.class);
        if (dataOptional.isPresent() && !dataOptional.get().context().get().properties().isPersistent()) {
            player.remove(MenuData.class);
        }
    }

}
