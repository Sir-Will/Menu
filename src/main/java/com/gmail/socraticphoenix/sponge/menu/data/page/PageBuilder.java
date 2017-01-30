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
package com.gmail.socraticphoenix.sponge.menu.data.page;

import com.gmail.socraticphoenix.sponge.menu.Input;
import com.gmail.socraticphoenix.sponge.menu.Page;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.tracker.Tracker;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PageBuilder extends AbstractDataBuilder<Page> {
    private static List<PageReader> readers = new ArrayList<>();

    public PageBuilder() {
        super(Page.class, 1);
    }

    public static void addReader(PageReader reader) {
        PageBuilder.readers.add(reader);
    }

    @Override
    protected Optional<Page> buildContent(DataView container) throws InvalidDataException {
        if(container.contains(MenuQueries.PAGE_TITLE, MenuQueries.PAGE_INPUT, MenuQueries.PAGE_ID, MenuQueries.PAGE_TRACKERS)) {
            Text title = container.getSerializable(MenuQueries.PAGE_TITLE, Text.class).get();
            Input input = container.getSerializable(MenuQueries.PAGE_INPUT, Input.class).get();
            String id = container.getString(MenuQueries.PAGE_ID).get();
            List<Tracker> trackers = container.getSerializableList(MenuQueries.PAGE_TRACKERS, Tracker.class).orElse(new ArrayList<>());
            for(PageReader reader : PageBuilder.readers) {
                Optional<Page> pageOptional = reader.read(title, input, id, trackers, container);
                if(pageOptional.isPresent()) {
                    return pageOptional;
                }
            }
        }
        return Optional.empty();
    }

}
