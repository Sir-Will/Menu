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
import com.gmail.socraticphoenix.sponge.menu.ButtonTypes;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.impl.button.ItemButton;
import com.gmail.socraticphoenix.sponge.menu.Tracker;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.Optional;

public class ItemButtonReader implements ButtonReader {

    @Override
    public Optional<Button> read(ButtonType type, Text title, String id, List<Tracker> trackers, DataView container) throws InvalidDataException {
        if(type == ButtonTypes.ITEM) {
            return Optional.of(new ItemButton(title, container.getSerializable(MenuQueries.BUTTON_ICON, ItemStack.class).get(), trackers, id));
        }
        return Optional.empty();
    }

}
