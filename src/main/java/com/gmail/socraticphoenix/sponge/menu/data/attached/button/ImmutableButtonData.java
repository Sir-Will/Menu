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
package com.gmail.socraticphoenix.sponge.menu.data.attached.button;

import com.gmail.socraticphoenix.sponge.menu.data.MenuKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.plugin.PluginContainer;

public class ImmutableButtonData extends AbstractImmutableData<ImmutableButtonData, ButtonData> {
    private String id;
    private PluginContainer owner;

    public ImmutableButtonData(String id, PluginContainer owner) {
        this.id = id;
        this.owner = owner;
        this.registerGetters();
    }

    public ImmutableValue<String> id() {
        return Sponge.getRegistry().getValueFactory().createValue(MenuKeys.BUTTON_ID, this.id).asImmutable();
    }

    public ImmutableValue<PluginContainer> owner() {
        return Sponge.getRegistry().getValueFactory().createValue(MenuKeys.BUTTON_OWNER, this.owner).asImmutable();
    }

    @Override
    protected void registerGetters() {
        registerFieldGetter(MenuKeys.BUTTON_ID, () -> this.id);
        registerFieldGetter(MenuKeys.BUTTON_OWNER, () -> this.owner);
        registerKeyValue(MenuKeys.BUTTON_ID, this::id);
        registerKeyValue(MenuKeys.BUTTON_OWNER, this::owner);
    }

    @Override
    public ButtonData asMutable() {
        return new ButtonData(this.id, this.owner);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(MenuKeys.BUTTON_ID.getQuery(), this.id)
                .set(MenuKeys.BUTTON_OWNER.getQuery(), this.owner.getId());
    }

}
