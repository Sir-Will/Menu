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

import com.gmail.socraticphoenix.sponge.menu.MenuPlugin;
import com.gmail.socraticphoenix.sponge.menu.data.MenuKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.Optional;

public class ButtonData extends AbstractData<ButtonData, ImmutableButtonData> {
    private String id;
    private PluginContainer owner;

    public ButtonData(String id, PluginContainer owner) {
        this.id = id;
        this.owner = owner;
        registerGettersAndSetters();
    }

    public Value<String> id() {
        return Sponge.getRegistry().getValueFactory().createValue(MenuKeys.BUTTON_ID, this.id);
    }

    public Value<PluginContainer> owner() {
        return Sponge.getRegistry().getValueFactory().createValue(MenuKeys.BUTTON_OWNER, this.owner);
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(MenuKeys.BUTTON_ID, () -> this.id);
        registerFieldGetter(MenuKeys.BUTTON_OWNER, () -> this.owner);
        registerFieldSetter(MenuKeys.BUTTON_ID, id -> this.id = id);
        registerFieldSetter(MenuKeys.BUTTON_OWNER, owner -> this.owner = owner);
        registerKeyValue(MenuKeys.BUTTON_ID, this::id);
        registerKeyValue(MenuKeys.BUTTON_OWNER, this::owner);
    }

    @Override
    public Optional<ButtonData> fill(DataHolder dataHolder, MergeFunction overlap) {
        Optional<ButtonData> otherDataOptional = dataHolder.get(ButtonData.class);
        if (otherDataOptional.isPresent()) {
            ButtonData other = otherDataOptional.get();
            ButtonData finalData = overlap.merge(this, other);
            this.id = finalData.id;
            this.owner = finalData.owner;
        }
        return Optional.of(this);
    }

    @Override
    public Optional<ButtonData> from(DataContainer container) {
        return this.from((DataView) container);
    }

    public Optional<ButtonData> from(DataView view) {
        if (view.contains(MenuKeys.BUTTON_ID) && view.contains(MenuKeys.BUTTON_OWNER)) {
            String owner = view.getString(MenuKeys.BUTTON_OWNER.getQuery()).get();
            if (Sponge.getPluginManager().getPlugin(owner).isPresent()) {
                this.owner = Sponge.getPluginManager().getPlugin(owner).get();
                this.id = view.getString(MenuKeys.BUTTON_ID.getQuery()).get();
            } else {
                this.owner = MenuPlugin.container();
                this.id = MenuPlugin.NO_PLUGIN_ID;
            }
            return Optional.of(this);
        }

        return Optional.empty();
    }

    @Override
    public ButtonData copy() {
        return new ButtonData(this.id, this.owner);
    }

    @Override
    public ImmutableButtonData asImmutable() {
        return new ImmutableButtonData(this.id, this.owner);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(MenuKeys.BUTTON_ID, this.id)
                .set(MenuKeys.BUTTON_OWNER.getQuery(), this.owner.getId());
    }

}
