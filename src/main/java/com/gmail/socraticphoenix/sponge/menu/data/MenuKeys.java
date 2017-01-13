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
package com.gmail.socraticphoenix.sponge.menu.data;

import com.gmail.socraticphoenix.sponge.menu.Menu;
import com.gmail.socraticphoenix.sponge.menu.MenuContext;
import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

public interface MenuKeys {

    Key<Value<Menu>> PLAYER_MENU = KeyFactory.makeSingleKey(
            TypeToken.of(Menu.class),
            new TypeToken<Value<Menu>>() {
            },
            DataQuery.of('.', "menuapi.player.menu"),
            "menuapi:player_menu",
            "Player Menu");

    Key<Value<MenuContext>> PLAYER_CONTEXT = KeyFactory.makeSingleKey(
            TypeToken.of(MenuContext.class),
            new TypeToken<Value<MenuContext>>() {
            },
            DataQuery.of('.', "menuapi.player.context"),
            "menuapi:player_context",
            "Player Menu Context");

    Key<Value<String>> BUTTON_ID = KeyFactory.makeSingleKey(
            TypeToken.of(String.class),
            new TypeToken<Value<String>>() {
            },
            DataQuery.of('.', "menuapi.button.id"),
            "menuapi:button_id",
            "Button ID");

    Key<Value<PluginContainer>> BUTTON_OWNER = KeyFactory.makeSingleKey(
            TypeToken.of(PluginContainer.class),
            new TypeToken<Value<PluginContainer>>() {
            },
            DataQuery.of('.', "menuapi.button.owner"),
            "menuapi:button_owner",
            "Button Owner");

    Key<Value<Text>> BUTTON_TITLE = KeyFactory.makeSingleKey(
            TypeToken.of(Text.class),
            new TypeToken<Value<Text>>(){},
            DataQuery.of('.', "menuapi.button.title"),
            "menuapi:button_title",
            "Button Title");

}
