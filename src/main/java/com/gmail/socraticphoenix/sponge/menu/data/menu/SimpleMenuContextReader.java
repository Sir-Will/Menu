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
package com.gmail.socraticphoenix.sponge.menu.data.menu;

import com.gmail.socraticphoenix.sponge.menu.Formatter;
import com.gmail.socraticphoenix.sponge.menu.InputContext;
import com.gmail.socraticphoenix.sponge.menu.Menu;
import com.gmail.socraticphoenix.sponge.menu.MenuContext;
import com.gmail.socraticphoenix.sponge.menu.MenuPlugin;
import com.gmail.socraticphoenix.sponge.menu.MenuVariables;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.data.pair.SerializablePair;
import com.gmail.socraticphoenix.sponge.menu.impl.menu.context.SimpleMenuContext;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleMenuContextReader implements MenuContextReader {

    @Override
    public Optional<MenuContext> read(Menu.Type type, DataView container) {
        if(container.contains(MenuQueries.CONTEXT_PAGE, MenuQueries.MENU_OWNER, MenuQueries.CONTEXT_INPUT, MenuQueries.CONTEXT_FORMATTERS, MenuQueries.CONTEXT_SPECIFIC_FORMATTERS, MenuQueries.CONTEXT_VARIABLES)) {
            Map<String, Formatter> specificFormatters = new HashMap<>();
            List<SerializablePair<String, Formatter>> formatters = (List<SerializablePair<String, Formatter>>) (List<?>) container.getSerializableList(MenuQueries.CONTEXT_SPECIFIC_FORMATTERS, SerializablePair.class).get();
            for(SerializablePair<String, Formatter> pair : formatters) {
                specificFormatters.put(pair.getLeft(), pair.getRight());
            }

            return Optional.of(new SimpleMenuContext(type, container.getInt(MenuQueries.CONTEXT_PAGE).get(), container.getSerializable(MenuQueries.CONTEXT_INPUT, InputContext.class).get(), Sponge.getPluginManager().getPlugin(container.getString(MenuQueries.MENU_OWNER).get()).orElse(MenuPlugin.container()), specificFormatters, container.getSerializableList(MenuQueries.CONTEXT_FORMATTERS, Formatter.class).get().stream().collect(Collectors.toSet()), container.getSerializable(MenuQueries.CONTEXT_VARIABLES, MenuVariables.class).get()));
        }
        return Optional.empty();
    }

}
