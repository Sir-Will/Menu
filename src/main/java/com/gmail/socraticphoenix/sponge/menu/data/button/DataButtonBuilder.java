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
package com.gmail.socraticphoenix.sponge.menu.data.button;

import com.gmail.socraticphoenix.sponge.menu.Button;
import com.gmail.socraticphoenix.sponge.menu.ButtonType;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.Tracker;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataButtonBuilder extends AbstractDataBuilder<Button> {
    private static List<ButtonReader> readers = new ArrayList<>();

    public DataButtonBuilder() {
        super(Button.class, 1);
    }

    public static void addReader(ButtonReader reader) {
        DataButtonBuilder.readers.add(reader);
    }

    @Override
    protected Optional<Button> buildContent(DataView container) throws InvalidDataException {
        if (container.contains(MenuQueries.BUTTON_ID, MenuQueries.BUTTON_TITLE, MenuQueries.BUTTON_TYPE, MenuQueries.BUTTON_TRACKERS)) {
            String id = container.getString(MenuQueries.BUTTON_ID).get();
            Text title = container.getSerializable(MenuQueries.BUTTON_TITLE, Text.class).get();
            ButtonType type = container.getCatalogType(MenuQueries.BUTTON_TYPE, ButtonType.class).get();
            List<Tracker> trackers = container.getSerializableList(MenuQueries.BUTTON_TRACKERS, Tracker.class).orElse(new ArrayList<>());
            for (ButtonReader reader : DataButtonBuilder.readers) {
                Optional<Button> buttonOptional = reader.read(type, title, id, trackers, container);
                if (buttonOptional.isPresent()) {
                    return buttonOptional;
                }
            }
        }
        return Optional.empty();
    }
}
