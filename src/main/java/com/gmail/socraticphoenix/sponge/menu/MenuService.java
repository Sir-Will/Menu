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
package com.gmail.socraticphoenix.sponge.menu;

import org.spongepowered.api.entity.living.player.Player;

import java.util.Map;
import java.util.Set;

/**
 * The service responsible for sending a {@link Menu} to a {@link Player}
 */
public interface MenuService {

    /**
     * Sends a {@link Menu}, with the given {@link MenuProperties}, {@link Formatter Formatters}, and owner.
     *
     * @param menu               The {@link Menu} to send.
     * @param properties         The {@link MenuProperties} to apply when sending this menu.
     * @param target             The {@link Player} to send the menu to.
     * @param plugin             The instance of the plugin which owns the {@link Menu}.
     * @param specificFormatters A {@link Map} of {@link Page} ids to {@link Formatter Formatters}. {@link Formatter
     *                           Formatters} in this map will be applied to the {@link Page} with the given id.
     * @param formatters         A {@link Set}
     */
    void send(Menu menu, MenuProperties properties, Player target, Object plugin, Map<String, Formatter> specificFormatters, Set<Formatter> formatters);

}
