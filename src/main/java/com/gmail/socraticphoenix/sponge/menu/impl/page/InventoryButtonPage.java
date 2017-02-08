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
import com.gmail.socraticphoenix.sponge.menu.InputTypes;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.impl.input.SimpleInput;
import com.gmail.socraticphoenix.sponge.menu.impl.page.target.GridTarget;
import com.gmail.socraticphoenix.sponge.menu.Tracker;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.text.Text;

import java.util.Collections;
import java.util.List;

public class InventoryButtonPage extends AbstractPage implements ButtonPage {
    private List<Button> buttons;
    private int height;
    private int length;

    public InventoryButtonPage(Text title, List<Button> buttons, List<Tracker> trackers, int height, int length, String id) {
        super(title, new SimpleInput(InputTypes.INVENTORY_BUTTON), () -> new GridTarget(length, height), id, trackers, false, true);
        this.buttons = Collections.unmodifiableList(buttons);
        this.height = height;
        this.length = length;
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
    public boolean isChatBased() {
        return false;
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(MenuQueries.PAGE_BUTTONS, this.buttons)
                .set(MenuQueries.PAGE_HEIGHT, this.height)
                .set(MenuQueries.PAGE_LENGTH, this.length);
    }

}
