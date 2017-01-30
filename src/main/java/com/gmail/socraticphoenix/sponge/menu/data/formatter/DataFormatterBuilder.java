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
package com.gmail.socraticphoenix.sponge.menu.data.formatter;

import com.gmail.socraticphoenix.sponge.menu.Formatter;
import com.gmail.socraticphoenix.sponge.menu.MenuPlugin;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataFormatterBuilder extends AbstractDataBuilder<Formatter> {
    private static List<FormatterReader> readers = new ArrayList<>();

    public DataFormatterBuilder() {
        super(Formatter.class, 1);
    }

    public static void addReader(FormatterReader reader) {
        DataFormatterBuilder.readers.add(reader);
    }

    @Override
    protected Optional<Formatter> buildContent(DataView container) throws InvalidDataException {
        if (container.contains(MenuQueries.FORMATTER_OWNER) && container.contains(MenuQueries.FORMATTER_PAGE) && container.contains(MenuQueries.FORMATTER_TARGET)) {
            String ownerId = container.getString(MenuQueries.FORMATTER_OWNER).get();
            PluginContainer pluginContainer = Sponge.getPluginManager().getPlugin(ownerId).orElse(MenuPlugin.container());
            String page = container.getString(MenuQueries.FORMATTER_PAGE).get();
            String target = container.getString(MenuQueries.FORMATTER_TARGET).get();
            for (FormatterReader reader : DataFormatterBuilder.readers) {
                Optional<Formatter> formatterOptional = reader.read(pluginContainer, page, target, container);
                if (formatterOptional.isPresent()) {
                    return formatterOptional;
                }
            }
        }
        return Optional.empty();
    }
}
