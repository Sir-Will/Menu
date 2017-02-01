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

import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.data.pair.SerializablePair;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A container class holding all the necessary information to call {@link MenuService#send(Menu, MenuProperties, Player,
 * Object, Map, Set)}.
 */
public class SendableMenu implements DataSerializable {
    private Menu menu;
    private Object plugin;
    private String pluginId;
    private Map<String, Formatter> specificFormatters;
    private Set<Formatter> formatters;
    private MenuProperties properties;

    /**
     * Creates a new sendable menu with the given parameters.
     *
     * @param menu               The {@link Menu} to send.
     * @param plugin             The instance of the plugin which owns the {@link Menu}.
     * @param specificFormatters A {@link Map} of {@link Page} ids to {@link Formatter Formatters}. {@link Formatter
     *                           Formatters} in this map will be applied to the {@link Page} with the given id.
     * @param formatters         A {@link Set}
     * @param properties         The {@link MenuProperties} to apply when sending this menu.
     */
    public SendableMenu(Menu menu, Object plugin, Map<String, Formatter> specificFormatters, Set<Formatter> formatters, MenuProperties properties) {
        this.menu = menu;
        this.plugin = plugin;
        this.specificFormatters = specificFormatters;
        this.formatters = formatters;
        this.properties = properties;
        this.pluginId = Sponge.getPluginManager().fromInstance(plugin).orElseThrow(() -> new IllegalArgumentException(plugin + " is not a plugin instance")).getId();
    }

    /**
     * Sends this sendable menu to the given {@link Player}.
     *
     * @param target The {@link Player} to send the sendable menu to.
     */
    public void send(Player target) {
        Sponge.getServiceManager().provideUnchecked(MenuService.class).send(this.menu, this.properties, target, this.plugin, this.specificFormatters, this.formatters);
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        List<SerializablePair<String, Formatter>> specificFormatters = new ArrayList<>();
        for (Map.Entry<String, Formatter> entry : this.specificFormatters.entrySet()) {
            specificFormatters.add(new SerializablePair<>(entry.getKey(), entry.getValue(), String.class, Formatter.class));
        }
        List<Formatter> formatters = new ArrayList<>();
        formatters.addAll(this.formatters);
        return new MemoryDataContainer().set(Queries.CONTENT_VERSION, this.getContentVersion())
                .set(MenuQueries.SENDABLE_MENU, this.menu)
                .set(MenuQueries.SENDABLE_PLUGIN, this.pluginId)
                .set(MenuQueries.SENDABLE_SPECIFIC_FORMATTERS, specificFormatters)
                .set(MenuQueries.SENDABLE_FORMATTERS, formatters)
                .set(MenuQueries.SENDABLE_PROPERTIES, this.properties);
    }

}
