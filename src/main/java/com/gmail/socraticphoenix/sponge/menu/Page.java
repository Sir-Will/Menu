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

import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.text.Text;

import java.util.List;

/**
 * Represents one page in a menu.
 */
public interface Page extends DataSerializable {

    /**
     * @return The title of this page.
     */
    Text title();

    /**
     * @return The {@link Input} Object associated with this page.
     */
    Input input();

    /**
     * @return A {@link PageTarget} which this page can be formatted to.
     */
    PageTarget produceTarget();

    /**
     * @return The id associated with this page.
     */
    String id();

    /**
     * @return The {@link Tracker Trackers} associated with this page.
     */
    List<Tracker> trackers();

    /**
     * @return True if this page is displayed in chat, false otherwise.
     */
    boolean isChatBased();

    /**
     * @return True if this page is displayed through an inventory, false otherwise.
     */
    boolean isInventoryBased();

}
