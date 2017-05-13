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
package com.gmail.socraticphoenix.sponge.menu.impl.menu;

import com.gmail.socraticphoenix.sponge.menu.EndMenuReason;
import com.gmail.socraticphoenix.sponge.menu.Formatter;
import com.gmail.socraticphoenix.sponge.menu.InputContext;
import com.gmail.socraticphoenix.sponge.menu.Menu;
import com.gmail.socraticphoenix.sponge.menu.MenuContext;
import com.gmail.socraticphoenix.sponge.menu.MenuPlugin;
import com.gmail.socraticphoenix.sponge.menu.MenuProperties;
import com.gmail.socraticphoenix.sponge.menu.MenuRegistry;
import com.gmail.socraticphoenix.sponge.menu.MenuType;
import com.gmail.socraticphoenix.sponge.menu.Page;
import com.gmail.socraticphoenix.sponge.menu.PageTarget;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.data.attached.player.MenuData;
import com.gmail.socraticphoenix.sponge.menu.data.map.SerializableMap;
import com.gmail.socraticphoenix.sponge.menu.data.pair.SerializablePair;
import com.gmail.socraticphoenix.sponge.menu.event.MenuStateEvent;
import com.gmail.socraticphoenix.sponge.menu.impl.formatter.OrderedGridFormatter;
import com.gmail.socraticphoenix.sponge.menu.impl.formatter.SequentialTextFormatter;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimpleMenuContext implements MenuContext {
    private static Set<Formatter> defaultFormatters;

    static {
        SimpleMenuContext.defaultFormatters = new HashSet<>();
        SimpleMenuContext.defaultFormatters.add(new OrderedGridFormatter(MenuPlugin.container(), false));
        SimpleMenuContext.defaultFormatters.add(new SequentialTextFormatter(MenuPlugin.container(), Text.NEW_LINE));
    }

    private int page;
    private int lastFormatPage;
    private InputContext context;
    private MenuType type;
    private PluginContainer owner;
    private Set<Formatter> formatters;
    private Map<String, Formatter> specificFormatters;
    private SerializableMap variables;
    private MenuProperties properties;

    public SimpleMenuContext(MenuType type, int page, InputContext context, PluginContainer owner, Map<String, Formatter> specificFormatters, Set<Formatter> formatters, SerializableMap variables, MenuProperties properties) {
        this.page = page;
        this.lastFormatPage = page;
        this.context = context;
        this.type = type;
        this.owner = owner;
        if (!formatters.isEmpty() || !specificFormatters.isEmpty()) {
            this.formatters = Collections.unmodifiableSet(formatters);
            this.specificFormatters = Collections.unmodifiableMap(specificFormatters);
        } else {
            this.formatters = Collections.unmodifiableSet(SimpleMenuContext.defaultFormatters);
            this.specificFormatters = Collections.unmodifiableMap(new HashMap<>());
        }
        this.variables = variables;
        this.properties = properties;
    }

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int page() {
        return this.page;
    }

    @Override
    public InputContext input() {
        return this.context;
    }

    @Override
    public MenuType type() {
        return this.type;
    }

    @Override
    public PluginContainer owner() {
        return this.owner;
    }

    @Override
    public Set<Formatter> formatters() {
        return this.formatters;
    }

    @Override
    public SerializableMap variables() {
        return this.variables;
    }

    @Override
    public MenuProperties properties() {
        return this.properties;
    }

    @Override
    public void refresh(Player player, Menu menu) {
        if (this.page < menu.pages().size() && this.page >= 0) {
            Page page = menu.pages().get(this.page);
            PageTarget target = page.produceTarget();
            Formatter usableFormatter = null;
            if (this.specificFormatters.containsKey(page.id())) {
                Formatter formatter = this.specificFormatters.get(page.id());
                if (formatter.page().isInstance(page) && formatter.target().isInstance(target)) {
                    usableFormatter = formatter;
                }
            }

            if (usableFormatter == null) {
                for (Formatter formatter : this.formatters) {
                    if (formatter.page().isInstance(page) && formatter.target().isInstance(target)) {
                        usableFormatter = formatter;
                        break;
                    }
                }
            }

            if (usableFormatter != null) {
                usableFormatter.format(page, target, this.owner());
                if (this.lastFormatPage == this.page) {
                    MenuRegistry.lookForAppropriate(target.getClass(), page.getClass()).redisplay(player, target, page, this.owner);
                } else {
                    MenuRegistry.lookForAppropriate(target.getClass(), page.getClass()).display(player, target, page, this.owner);
                }
            } else {
                player.sendMessage(Text.of(TextColors.RED, "[MenuAPI] Error: no formatter in context for page \"" + page.getClass() + "\" and target \"" + target.getClass() + "\""));
                this.terminate(EndMenuReason.ERROR, player, menu);
            }
        } else {
            player.sendMessage(Text.of(TextColors.RED, "[MenuAPI] Error: page #" + this.page + " is out of bounds"));
            this.terminate(EndMenuReason.ERROR, player, menu);
        }
        this.lastFormatPage = this.page;
    }

    @Override
    public void silentRefresh(Player player, Menu menu) {
        if (this.page < menu.pages().size() && this.page >= 0) {
            Page page = menu.pages().get(this.page);
            PageTarget target = page.produceTarget();
            Formatter usableFormatter = null;
            if (this.specificFormatters.containsKey(page.id())) {
                Formatter formatter = this.specificFormatters.get(page.id());
                if (formatter.page().isInstance(page) && formatter.target().isInstance(target)) {
                    usableFormatter = formatter;
                }
            }

            if (usableFormatter == null) {
                for (Formatter formatter : this.formatters) {
                    if (formatter.page().isInstance(page) && formatter.target().isInstance(target)) {
                        usableFormatter = formatter;
                        break;
                    }
                }
            }

            if (usableFormatter != null) {
                usableFormatter.format(page, target, this.owner());
                MenuRegistry.lookForAppropriate(target.getClass(), page.getClass()).redisplay(player, target, page, this.owner);
            } else {
                player.sendMessage(Text.of(TextColors.RED, "[MenuAPI] Error: no formatter in context for page \"" + page.getClass() + "\" and target \"" + target.getClass() + "\""));
                this.terminate(EndMenuReason.ERROR, player, menu);
            }
        } else {
            player.sendMessage(Text.of(TextColors.RED, "[MenuAPI] Error: page #" + this.page + " is out of bounds"));
            this.terminate(EndMenuReason.ERROR, player, menu);
        }
        this.lastFormatPage = this.page;
    }

    @Override
    public void terminate(EndMenuReason reason, Player player, Menu menu) {
        MenuStateEvent.Close.Pre pre = new MenuStateEvent.Close.Pre(menu, this, player);
        Sponge.getEventManager().post(pre);

        if (!pre.isCancelled()) {
            if (player.getOpenInventory().isPresent() && this.getCurrentPage(menu).isPresent() && this.getCurrentPage(menu).get().isInventoryBased()) {
                player.closeInventory(Cause.of(NamedCause.source(MenuPlugin.container()), NamedCause.of("reason", reason)));
            }
            player.remove(MenuData.class);
            MenuStateEvent event = new MenuStateEvent.Close(reason, player, menu, this);
            Sponge.getEventManager().post(event);
        }
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        List<Formatter> formatters = new ArrayList<>();
        formatters.addAll(this.formatters);
        List<SerializablePair<String, Formatter>> specificFormatters = new ArrayList<>();
        for (Map.Entry<String, Formatter> entry : this.specificFormatters.entrySet()) {
            specificFormatters.add(new SerializablePair<>(entry.getKey(), entry.getValue(), String.class, Formatter.class));
        }

        return new MemoryDataContainer().set(Queries.CONTENT_VERSION, this.getContentVersion())
                .set(MenuQueries.CONTEXT_MENU_TYPE, this.type)
                .set(MenuQueries.CONTEXT_PAGE, this.page)
                .set(MenuQueries.MENU_OWNER, this.owner.getId())
                .set(MenuQueries.CONTEXT_INPUT, this.context)
                .set(MenuQueries.CONTEXT_FORMATTERS, formatters)
                .set(MenuQueries.CONTEXT_SPECIFIC_FORMATTERS, specificFormatters)
                .set(MenuQueries.CONTEXT_VARIABLES, this.variables)
                .set(MenuQueries.CONTEXT_PROPERTIES, this.properties);
    }

}
