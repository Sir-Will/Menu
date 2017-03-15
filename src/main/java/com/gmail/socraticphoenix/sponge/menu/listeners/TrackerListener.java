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

import com.gmail.socraticphoenix.sponge.menu.Menu;
import com.gmail.socraticphoenix.sponge.menu.MenuContext;
import com.gmail.socraticphoenix.sponge.menu.MenuRegistry;
import com.gmail.socraticphoenix.sponge.menu.Page;
import com.gmail.socraticphoenix.sponge.menu.event.MenuEvent;
import com.gmail.socraticphoenix.sponge.menu.event.MenuInputEvent;
import com.gmail.socraticphoenix.sponge.menu.impl.tracker.ButtonTracker;
import com.gmail.socraticphoenix.sponge.menu.impl.tracker.GeneralTracker;
import com.gmail.socraticphoenix.sponge.menu.impl.tracker.TextTracker;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.util.Tristate;

import java.util.Optional;

public class TrackerListener {

    @Listener
    public void onMenuEvent(MenuEvent ev) {
        MenuRegistry.getTrackers().filter(tracker -> tracker instanceof GeneralTracker).filter(tracker -> tracker.getEvent().isInstance(ev)).forEach(tracker -> tracker.invoke(ev));
    }

    @Listener
    @IsCancelled(Tristate.FALSE)
    public void onButtonInput(MenuInputEvent.Button ev) {
        MenuRegistry.getTrackers().filter(tracker -> tracker instanceof ButtonTracker).map(tracker -> (ButtonTracker) tracker).forEach(tracker -> {
            String plugin = ev.buttonOwner().getId();
            String button = ev.buttonId();
            if(tracker.getButtonId().equals(button) && tracker.getPluginId().equals(plugin)) {
                tracker.invoke(ev);
            }
        });
    }

    @Listener
    @IsCancelled(Tristate.FALSE)
    public void onTextInput(MenuInputEvent.Text ev) {
        MenuRegistry.getTrackers().filter(tracker -> tracker instanceof TextTracker).map(tracker -> (TextTracker) tracker).forEach(tracker -> {
            Menu menu = ev.menu();
            MenuContext context = ev.context();
            Optional<Page> pageOptional = context.page() >= 0 && context.page() < menu.pages().size() ? Optional.of(menu.pages().get(context.page())) : Optional.empty();
            if(pageOptional.isPresent()) {
                Page page = pageOptional.get();
                String plugin = context.owner().getId();
                String pageId = page.id();
                if(plugin.equals(tracker.getPluginId()) && pageId.equals(tracker.getPageId())) {
                    tracker.invoke(ev);
                }
            }
        });
    }

}
