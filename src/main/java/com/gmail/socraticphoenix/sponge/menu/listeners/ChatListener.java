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
import com.gmail.socraticphoenix.sponge.menu.event.MenuInputEvent;
import com.gmail.socraticphoenix.sponge.menu.impl.page.ChatTextPage;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.channel.MutableMessageChannel;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class ChatListener {

    @Listener
    public void onMenuPlayerChat(MessageChannelEvent.Chat ev, @First Player player) {
        if(player.get(MenuData.class).isPresent() && player.get(MenuData.class).get().getCurrentPage().isPresent() && player.get(MenuData.class).get().getCurrentPage().get() instanceof ChatTextPage) {
            MenuData menuData = player.get(MenuData.class).get();
            String message = ev.getRawMessage().toPlain();
            MenuInputEvent event = new MenuInputEvent.Text(player, menuData.context().get(), menuData.menu().get(), message);
            player.sendMessage(Text.of(TextColors.AQUA, "> ", TextColors.GOLD, message));
            Sponge.getEventManager().post(event);
            ev.setCancelled(true);
        }
    }

    @Listener
    public void onChat(MessageChannelEvent ev) {
        MessageChannel channel = ev.getChannel().orElse(ev.getOriginalChannel());
        MutableMessageChannel mutableChannel = channel instanceof MutableMessageChannel ? (MutableMessageChannel) channel : channel.asMutable();

        Collection<MessageReceiver> members = new ArrayList<>();
        members.addAll(mutableChannel.getMembers());

        boolean modified = false;

        for(MessageReceiver receiver : members) {
            if(receiver instanceof Player) {
                Player p = (Player) receiver;
                Optional<MenuData> menuDataOptional = p.get(MenuData.class);
                if(menuDataOptional.isPresent() && menuDataOptional.get().context().get().properties().isRestrictChat() && menuDataOptional.get().getCurrentPage().isPresent() && menuDataOptional.get().getCurrentPage().get().isChatBased()) {
                    if(ev.getCause().get(NamedCause.SOURCE, Player.class).isPresent()) {
                        mutableChannel.removeMember(receiver);
                        modified = true;
                    } else {
                        Sponge.getScheduler().createTaskBuilder().execute(() -> p.get(MenuData.class).ifPresent(m -> m.context().get().refresh(p, m.menu().get()))).delayTicks(1).intervalTicks(0).submit(MenuPlugin.instance());
                    }
                }
            }
        }

        if(modified) {
            ev.setChannel(mutableChannel);
        }
    }

}
