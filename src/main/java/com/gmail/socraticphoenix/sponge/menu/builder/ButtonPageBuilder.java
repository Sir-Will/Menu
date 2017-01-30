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
package com.gmail.socraticphoenix.sponge.menu.builder;

import com.gmail.socraticphoenix.sponge.menu.Button;
import com.gmail.socraticphoenix.sponge.menu.Formatter;
import com.gmail.socraticphoenix.sponge.menu.InputType;
import com.gmail.socraticphoenix.sponge.menu.InputTypes;
import com.gmail.socraticphoenix.sponge.menu.Page;
import com.gmail.socraticphoenix.sponge.menu.impl.formatter.tree.TreeNode;
import com.gmail.socraticphoenix.sponge.menu.impl.page.ChatButtonPage;
import com.gmail.socraticphoenix.sponge.menu.impl.page.InventoryButtonPage;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ButtonPageBuilder {
    private MenuBuilder parent;
    private Text title;
    private String id;
    private List<Button> buttons;
    private int height;
    private int width;

    public ButtonPageBuilder(MenuBuilder parent) {
        this.parent = parent;
        this.title = Text.of("Page");
        this.id = "default_id";
        this.buttons = new ArrayList<>();
        this.height = 6;
        this.width = 9;
    }

    public MenuBuilder getParent() {
        return this.parent;
    }

    public ButtonPageBuilder id(String id) {
        this.id = id;
        return this;
    }

    public ButtonPageBuilder title(Text title) {
        this.title = title;
        return this;
    }

    public ButtonPageBuilder height(int height) {
        this.height = height;
        return this;
    }

    public ButtonPageBuilder width(int width) {
        this.width = width;
        return this;
    }

    public StrictGridFormatterBuilder strictGridFormatter() {
        return new StrictGridFormatterBuilder(this.parent, this.id);
    }

    public ButtonPageBuilder categoricalTextFormatter(TreeNode categories, Text indent) {
        this.parent.categoricalTextFormatter(this.id, categories, indent);
        return this;
    }

    public ButtonPageBuilder sequentialTextFormatter(Text separator) {
        this.parent.sequentialTextFormatter(this.id, separator);
        return this;
    }

    public ButtonPageBuilder orderedGridFormatter(boolean vertical) {
        this.parent.orderedGridFormatter(this.id, vertical);
        return this;
    }

    public ButtonPageBuilder formatter(Formatter formatter) {
        this.parent.formatter(this.id, formatter);
        return this;
    }

    public ButtonBuilder button() {
        return new ButtonBuilder(this);
    }

    public void finish(InputType type) {
        Page page;
        if(type == InputTypes.INVENTORY_BUTTON) {
            page = new InventoryButtonPage(this.title, this.buttons, new ArrayList<>(), this.height, this.width, this.id);
        } else if (type == InputTypes.CHAT_BUTTON) {
            page = new ChatButtonPage(this.title, this.buttons, new ArrayList<>(), this.id);
        } else {
            throw new IllegalArgumentException("Invalid button input type: " + type.getId());
        }
        this.parent.page(page);
    }

    public ButtonPageBuilder button(Button button) {
        this.buttons.add(button);
        return this;
    }
}
