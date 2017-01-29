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
import com.gmail.socraticphoenix.sponge.menu.Input;
import com.gmail.socraticphoenix.sponge.menu.InputTypes;
import com.gmail.socraticphoenix.sponge.menu.TextButtonPage;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.impl.input.SimpleInput;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.text.Text;

import java.util.Collections;
import java.util.List;

public class ChatButtonPage implements TextButtonPage {
    private Text title;
    private Input input;
    private List<Button> buttons;
    private String id;

    public ChatButtonPage(Text title, List<Button> buttons, String id) {
        this.title = title;
        this.input = new SimpleInput(InputTypes.CHAT_BUTTON);
        this.buttons = Collections.unmodifiableList(buttons);
        this.id = id;
    }

    @Override
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
    public String id() {
        return this.id;
    }

    @Override
    public boolean isChatBased() {
        return true;
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
                .set(MenuQueries.PAGE_ID, this.id);
    }

}
