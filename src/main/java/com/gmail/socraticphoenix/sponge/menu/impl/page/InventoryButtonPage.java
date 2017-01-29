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
package com.gmail.socraticphoenix.sponge.menu.impl.page;

import com.gmail.socraticphoenix.sponge.menu.Button;
import com.gmail.socraticphoenix.sponge.menu.ButtonPage;
import com.gmail.socraticphoenix.sponge.menu.Input;
import com.gmail.socraticphoenix.sponge.menu.InputTypes;
import com.gmail.socraticphoenix.sponge.menu.PageTarget;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.impl.input.SimpleInput;
import com.gmail.socraticphoenix.sponge.menu.impl.page.target.GridTarget;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.text.Text;

import java.util.Collections;
import java.util.List;

public class InventoryButtonPage implements ButtonPage {
    private Text title;
    private Input input;
    private List<Button> buttons;
    private int height;
    private int length;
    private String id;

    public InventoryButtonPage(Text title, List<Button> buttons, int height, int length, String id) {
        this.title = title;
        this.input = new SimpleInput(InputTypes.INVENTORY_BUTTON);
        this.buttons = Collections.unmodifiableList(buttons);
        this.height = height;
        this.length = length;
        this.id = id;
        if(length > 9 || length < 1) {
            throw new IllegalArgumentException("Length may not be greater than 9 or less than 1");
        } else if (height > 6 || height < 1) {
            throw new IllegalArgumentException("Height may not be greater than 6 or less than 1");
        }
    }

    public List<Button> buttons() {
        return this.buttons;
    }

    @Override
    public Text title() {
        return this.title;
    }

    @Override
    public Input input() {
        return this.input;
    }

    @Override
    public PageTarget produceTarget() {
        return new GridTarget(this.length, this.height);
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public boolean isChatBased() {
        return false;
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer().set(Queries.CONTENT_VERSION, this.getContentVersion())
                .set(MenuQueries.PAGE_TITLE, this.title)
                .set(MenuQueries.PAGE_INPUT, this.input)
                .set(MenuQueries.PAGE_BUTTONS, this.buttons)
                .set(MenuQueries.PAGE_HEIGHT, this.height)
                .set(MenuQueries.PAGE_LENGTH, this.length)
                .set(MenuQueries.PAGE_ID, this.id);
    }

}
