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
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;

public abstract class MenuStateEvent extends TargetMenuEvent {

    public MenuStateEvent(Cause cause, Menu menu, MenuContext context, Player player) {
        super(cause, menu, context, player);
    }

    public static class Close extends MenuStateEvent {
        private EndMenuReason reason;

        public Close(EndMenuReason reason, Player player, Menu menu, MenuContext context) {
            super(Cause.of(NamedCause.of("reason", reason), NamedCause.of("player", player), NamedCause.of("menu", menu), NamedCause.of("context", context)), menu, context, player);
            this.reason = reason;
        }

        public EndMenuReason reason() {
            return this.reason;
        }

        public static class Pre extends MenuEvent implements Cancellable {
            private boolean cancelled;
            private Player player;

            public Pre(Player player) {
                super(Cause.of(NamedCause.of("player", player)));
                this.player = player;
                this.cancelled = false;
            }


            @Override
            public boolean isCancelled() {
                return this.cancelled;
            }

            @Override
            public void setCancelled(boolean cancel) {
                this.cancelled = cancel;
            }

            public Player player() {
                return this.player;
            }

        }

    }

    public static class Open extends MenuStateEvent {

        public Open(Player player, Menu menu, MenuContext context) {
            super(Cause.of(NamedCause.of("player", player), NamedCause.of("menu", menu), NamedCause.of("context", context)), menu, context, player);
        }

        public static class Pre extends MenuEvent implements Cancellable {
            private boolean cancelled;
            private Player player;

            public Pre(Player player) {
                super(Cause.of(NamedCause.of("player", player)));
                this.player = player;
                this.cancelled = false;
            }


            @Override
            public boolean isCancelled() {
                return this.cancelled;
            }

            @Override
            public void setCancelled(boolean cancel) {
                this.cancelled = cancel;
            }

            public Player player() {
                return this.player;
            }

        }

    }
}
