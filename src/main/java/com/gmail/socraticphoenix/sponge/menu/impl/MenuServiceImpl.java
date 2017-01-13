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
package com.gmail.socraticphoenix.sponge.menu.impl;

import com.gmail.socraticphoenix.sponge.menu.EndMenuReason;
import com.gmail.socraticphoenix.sponge.menu.Formatter;
import com.gmail.socraticphoenix.sponge.menu.InputContext;
import com.gmail.socraticphoenix.sponge.menu.Menu;
import com.gmail.socraticphoenix.sponge.menu.MenuContext;
import com.gmail.socraticphoenix.sponge.menu.MenuService;
import com.gmail.socraticphoenix.sponge.menu.MenuVariables;
import com.gmail.socraticphoenix.sponge.menu.data.attached.player.MenuData;
import com.gmail.socraticphoenix.sponge.menu.impl.menu.context.SimpleMenuContext;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.Map;
import java.util.Set;

public class MenuServiceImpl implements MenuService {

    @Override
    public void send(Menu menu, Player target, Object plugin, Map<String, Formatter> specificFormatters, Set<Formatter> formatters) {
        PluginContainer container = Sponge.getPluginManager().fromInstance(plugin).orElseThrow(() -> new IllegalArgumentException(plugin + " is not a plugin instance"));
        MenuContext context = new SimpleMenuContext(Menu.Type.SIMPLE, 0, InputContext.EMPTY, container, specificFormatters, formatters, new MenuVariables());
        if(target.get(MenuData.class).isPresent()) {
            target.get(MenuData.class).get().context().get().terminate(EndMenuReason.NEW_MENU, target, menu);
        }

        MenuData newData = new MenuData(menu, context);
        target.offer(newData);

        context.refresh(target, menu);
    }

}
