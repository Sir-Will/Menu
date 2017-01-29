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
package com.gmail.socraticphoenix.sponge.menu.impl.button;

import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.data.attached.button.ButtonData;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

public class ItemButton extends AbstractButton {
    private Text text;
    private ItemStack icon;
    private String id;

    public ItemButton(Text text, ItemStack icon, String id) {
        if(id.contains(" ")) {
            throw new IllegalArgumentException("Button IDs may not containe spaces");
        }
        if(icon.get(ButtonData.class).isPresent()) {
            icon.remove(ButtonData.class);
        }

        this.text = text;
        this.icon = icon;
        this.id = id;
    }

    @Override
    public Text title() {
        return this.text;
    }

    @Override
    public ItemStack icon() {
        return this.icon;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public Type type() {
        return Type.ITEM;
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(MenuQueries.BUTTON_ICON, this.icon);
    }

}
