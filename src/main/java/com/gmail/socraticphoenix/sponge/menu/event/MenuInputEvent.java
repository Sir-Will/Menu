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

import com.gmail.socraticphoenix.sponge.menu.Menu;
import com.gmail.socraticphoenix.sponge.menu.MenuContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.plugin.PluginContainer;

public abstract class MenuInputEvent extends AbstractEvent implements Cancellable {
    private boolean cancelled;
    private Cause cause;

    public MenuInputEvent(Cause cause) {
        this.cancelled = false;
        this.cause = cause;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public Cause getCause() {
        return this.cause;
    }

    public static class Button extends MenuInputEvent {
        private String buttonId;
        private PluginContainer buttonOwner;
        private MenuContext context;
        private Menu menu;
        private Player player;

        public Button(Player player, String buttonId, PluginContainer buttonOwner, MenuContext context, Menu menu) {
            super(Cause.of(NamedCause.source(player), NamedCause.of("id", buttonId), NamedCause.of("owner", buttonOwner), NamedCause.of("context", context), NamedCause.of("menu", menu)));
            this.buttonId = buttonId;
            this.buttonOwner = buttonOwner;
            this.context = context;
            this.menu = menu;
            this.player = player;
        }

        public Player player() {
            return this.player;
        }

        public String buttonId() {
            return this.buttonId;
        }

        public PluginContainer buttonOwner() {
            return this.buttonOwner;
        }

        public MenuContext context() {
            return this.context;
        }

        public Menu menu() {
            return this.menu;
        }
    }

    public static class Text extends MenuInputEvent {
        private String input;
        private MenuContext context;
        private Menu menu;
        private Player player;

        public Text(Player player, MenuContext context, Menu menu, String input) {
            super(Cause.of(NamedCause.source(player), NamedCause.of("context", context), NamedCause.of("menu", menu)));
            this.input = input;
            this.context = context;
            this.menu = menu;
            this.player = player;
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

        public String input() {
            return this.input;
        }

    }

}
