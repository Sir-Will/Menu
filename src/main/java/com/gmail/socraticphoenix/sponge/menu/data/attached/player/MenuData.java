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
package com.gmail.socraticphoenix.sponge.menu.data.attached.player;

import com.gmail.socraticphoenix.sponge.menu.Menu;
import com.gmail.socraticphoenix.sponge.menu.MenuContext;
import com.gmail.socraticphoenix.sponge.menu.Page;
import com.gmail.socraticphoenix.sponge.menu.data.MenuKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class MenuData extends AbstractData<MenuData, ImmutableMenuData> {
    private Menu menu;
    private MenuContext context;

    public MenuData(Menu menu, MenuContext context) {
        this.menu = menu;
        this.context = context;
        this.registerGettersAndSetters();
    }

    public Optional<Page> getCurrentPage() {
        if(this.context.page() >= 0 && this.context.page() < menu.pages().size()) {
            return Optional.of(this.menu.pages().get(this.context.page()));
        } else {
            return Optional.empty();
        }
    }

    public Value<Menu> menu() {
        return Sponge.getRegistry().getValueFactory().createValue(MenuKeys.PLAYER_MENU, this.menu);
    }

    public Value<MenuContext> context() {
        return Sponge.getRegistry().getValueFactory().createValue(MenuKeys.PLAYER_CONTEXT, this.context);
    }

    @Override
    protected void registerGettersAndSetters() {
        registerFieldGetter(MenuKeys.PLAYER_MENU, () -> this.menu);
        registerFieldGetter(MenuKeys.PLAYER_CONTEXT, () -> this.context);
        registerFieldSetter(MenuKeys.PLAYER_MENU, menu -> this.menu = menu);
        registerFieldSetter(MenuKeys.PLAYER_CONTEXT, context -> this.context = context);
        registerKeyValue(MenuKeys.PLAYER_MENU, this::menu);
        registerKeyValue(MenuKeys.PLAYER_CONTEXT, this::context);
    }

    @Override
    public Optional<MenuData> fill(DataHolder dataHolder, MergeFunction overlap) {
        Optional<MenuData> otherDataOptional = dataHolder.get(MenuData.class);
        if(otherDataOptional.isPresent()) {
            MenuData other = otherDataOptional.get();
            MenuData finalData = overlap.merge(this, other);
            this.menu = finalData.menu;
            this.context = finalData.context;
        }
        return Optional.of(this);
    }

    @Override
    public Optional<MenuData> from(DataContainer container) {
        return this.from((DataView) container);
    }

    public Optional<MenuData> from(DataView view) {
        if(view.contains(MenuKeys.PLAYER_MENU) && view.contains(MenuKeys.PLAYER_CONTEXT)) {
            this.menu = view.getSerializable(MenuKeys.PLAYER_MENU.getQuery(), Menu.class).get();
            this.context = MenuContext.deserialize(view, MenuKeys.PLAYER_CONTEXT.getQuery());
        }
        return Optional.empty();
    }

    @Override
    public MenuData copy() {
        return new MenuData(this.menu, this.context);
    }

    @Override
    public ImmutableMenuData asImmutable() {
        return new ImmutableMenuData(this.menu, this.context);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(MenuKeys.PLAYER_MENU, this.menu)
                .set(MenuKeys.PLAYER_CONTEXT, this.context);
    }

}
