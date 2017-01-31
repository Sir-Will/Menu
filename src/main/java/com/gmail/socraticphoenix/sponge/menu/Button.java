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
package com.gmail.socraticphoenix.sponge.menu;

import com.gmail.socraticphoenix.sponge.menu.impl.button.ItemButton;
import com.gmail.socraticphoenix.sponge.menu.impl.button.TextButton;
import com.gmail.socraticphoenix.sponge.menu.tracker.Tracker;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.List;

/**
 * Represents a button in a {@link ButtonPage}.
 */
public interface Button extends DataSerializable {

    /**
     * @return The title of this button.
     */
    Text title();

    /**
     * Returns the icon of this button, if this button is an {@link ItemButton}. If this button is a {@link TextButton},
     * return is guaranteed non-null, but is undefined.
     *
     * @return The icon of this button.
     */
    ItemStack icon();

    /**
     * @return The id of this button.
     */
    String id();

    /**
     * @return The type of this button.
     */
    ButtonType type();

    /**
     * @return The {@link Tracker Trackers} associated with this button.
     */
    List<Tracker> trackers();

}
