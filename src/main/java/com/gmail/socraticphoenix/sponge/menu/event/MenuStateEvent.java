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
package com.gmail.socraticphoenix.sponge.menu.event;

import com.gmail.socraticphoenix.sponge.menu.EndMenuReason;
import com.gmail.socraticphoenix.sponge.menu.Menu;
import com.gmail.socraticphoenix.sponge.menu.MenuContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.impl.AbstractEvent;

public class MenuStateEvent extends AbstractEvent {
    private Cause cause;
    private boolean cancelled;

    public MenuStateEvent(Cause cause) {
        this.cause = cause;
        this.cancelled = false;
    }

    @Override
    public Cause getCause() {
        return this.cause;
    }

    public static class Close extends MenuStateEvent {
        private EndMenuReason reason;
        private Player player;
        private Menu menu;
        private MenuContext context;

        public Close(EndMenuReason reason, Player player, Menu menu, MenuContext context) {
            super(Cause.of(NamedCause.of("reason", reason), NamedCause.of("player", player), NamedCause.of("menu", menu), NamedCause.of("context", context)));
            this.reason = reason;
            this.player = player;
            this.menu = menu;
            this.context = context;
        }

        public EndMenuReason reason() {
            return this.reason;
        }

        public Player player() {
            return this.player;
        }

        public Menu menu() {
            return this.menu;
        }

        public MenuContext context() {
            return this.context;
        }

    }

    public static class Open extends MenuStateEvent {
        private Player player;
        private Menu menu;
        private MenuContext context;

        public Open(Player player, Menu menu, MenuContext context) {
            super(Cause.of(NamedCause.of("player", player), NamedCause.of("menu", menu), NamedCause.of("context", context)));
            this.player = player;
            this.menu = menu;
            this.context = context;
        }

        public Player player() {
            return this.player;
        }

        public Menu menu() {
            return this.menu;
        }

        public MenuContext context() {
            return this.context;
        }

    }
}
