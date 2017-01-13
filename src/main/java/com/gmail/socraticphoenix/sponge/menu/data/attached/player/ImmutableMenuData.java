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
import com.gmail.socraticphoenix.sponge.menu.data.MenuKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableMenuData extends AbstractImmutableData<ImmutableMenuData, MenuData> {
    private Menu menu;
    private MenuContext context;

    public ImmutableMenuData(Menu menu, MenuContext context) {
        this.menu = menu;
        this.context = context;
        this.registerGetters();;
    }

    public ImmutableValue<Menu> menu() {
        return Sponge.getRegistry().getValueFactory().createValue(MenuKeys.PLAYER_MENU, this.menu).asImmutable();
    }

    public ImmutableValue<MenuContext> context() {
        return Sponge.getRegistry().getValueFactory().createValue(MenuKeys.PLAYER_CONTEXT, this.context).asImmutable();
    }

    @Override
    protected void registerGetters() {
        registerFieldGetter(MenuKeys.PLAYER_MENU, () -> this.menu);
        registerFieldGetter(MenuKeys.PLAYER_CONTEXT, () -> this.context);
        registerKeyValue(MenuKeys.PLAYER_MENU, this::menu);
        registerKeyValue(MenuKeys.PLAYER_CONTEXT, this::context);
    }

    @Override
    public MenuData asMutable() {
        return new MenuData(this.menu, this.context);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

}
