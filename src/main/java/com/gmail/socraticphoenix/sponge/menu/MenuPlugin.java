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

import com.gmail.socraticphoenix.sponge.menu.catalogs.ButtonTypeCatalog;
import com.gmail.socraticphoenix.sponge.menu.catalogs.InputTypeCatalog;
import com.gmail.socraticphoenix.sponge.menu.catalogs.MenuTypeCatalog;
import com.gmail.socraticphoenix.sponge.menu.command.ButtonCommand;
import com.gmail.socraticphoenix.sponge.menu.data.attached.button.ButtonData;
import com.gmail.socraticphoenix.sponge.menu.data.attached.button.ButtonDataBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.attached.button.ImmutableButtonData;
import com.gmail.socraticphoenix.sponge.menu.data.attached.player.ImmutableMenuData;
import com.gmail.socraticphoenix.sponge.menu.data.attached.player.MenuData;
import com.gmail.socraticphoenix.sponge.menu.data.attached.player.MenuDataBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.button.DataButtonBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.button.ItemButtonReader;
import com.gmail.socraticphoenix.sponge.menu.data.button.TextButtonReader;
import com.gmail.socraticphoenix.sponge.menu.data.formatter.DataFormatterBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.formatter.OrderedGridReader;
import com.gmail.socraticphoenix.sponge.menu.data.formatter.StrictGridReader;
import com.gmail.socraticphoenix.sponge.menu.data.formatter.tree.TreeNodeBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.input.DataInputBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.input.EmptyInputContextReader;
import com.gmail.socraticphoenix.sponge.menu.data.input.InputContextBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.input.SimpleInputReader;
import com.gmail.socraticphoenix.sponge.menu.data.map.DataSerializableMapBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.map.SerializableMap;
import com.gmail.socraticphoenix.sponge.menu.data.menu.DataMenuBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.menu.DataMenuContextBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.menu.DataSendableMenuBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.menu.EmptyMenuContextReader;
import com.gmail.socraticphoenix.sponge.menu.data.menu.EmptyMenuReader;
import com.gmail.socraticphoenix.sponge.menu.data.menu.SimpleMenuContextReader;
import com.gmail.socraticphoenix.sponge.menu.data.menu.SimpleMenuReader;
import com.gmail.socraticphoenix.sponge.menu.data.page.AnvilTextPageReader;
import com.gmail.socraticphoenix.sponge.menu.data.page.ChatButtonPageReader;
import com.gmail.socraticphoenix.sponge.menu.data.page.ChatTextPageReader;
import com.gmail.socraticphoenix.sponge.menu.data.page.DataPageBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.page.InventoryButtonPageReader;
import com.gmail.socraticphoenix.sponge.menu.data.pair.DataSerializablePairBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.pair.SerializablePair;
import com.gmail.socraticphoenix.sponge.menu.data.tracker.ButtonTrackerReader;
import com.gmail.socraticphoenix.sponge.menu.data.tracker.DataTrackerBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.tracker.TextTrackerReader;
import com.gmail.socraticphoenix.sponge.menu.impl.MenuServiceImpl;
import com.gmail.socraticphoenix.sponge.menu.impl.finalizer.AnvilTextFinalizer;
import com.gmail.socraticphoenix.sponge.menu.impl.finalizer.ChatButtonFinalizer;
import com.gmail.socraticphoenix.sponge.menu.impl.finalizer.ChatTextFinalizer;
import com.gmail.socraticphoenix.sponge.menu.impl.finalizer.GridFinalizer;
import com.gmail.socraticphoenix.sponge.menu.impl.formatter.tree.TreeNode;
import com.gmail.socraticphoenix.sponge.menu.listeners.ChatListener;
import com.gmail.socraticphoenix.sponge.menu.listeners.InventoryListener;
import com.gmail.socraticphoenix.sponge.menu.listeners.PlayerListener;
import com.gmail.socraticphoenix.sponge.menu.listeners.TrackerListener;
import org.slf4j.Logger;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.DataManager;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

@Plugin(id = "menuapi", name = "Menu", version = "0.0.0", description = "GUIs", authors = "Socratic_Phoenix")
public class MenuPlugin {
    public static final String DEFAULT_BUTTON_ID = "default_id";
    public static final String NO_PLUGIN_ID = "missing_plugin";
    private static MenuPlugin instance;

    public MenuPlugin() {
        MenuPlugin.instance = this;
    }

    public static PluginContainer container() {
        return Sponge.getPluginManager().fromInstance(MenuPlugin.instance).get();
    }

    public static Logger logger() {
        return MenuPlugin.container().getLogger();
    }

    public static MenuPlugin instance() {
        return MenuPlugin.instance;
    }

    @Listener(order = Order.PRE)
    public void onPreInit(GamePreInitializationEvent ev) {
        DataManager dataManager = Sponge.getDataManager();
        GameRegistry registry = Sponge.getRegistry();

        //Catalogs
        registry.registerModule(InputType.class, InputTypeCatalog.instance());
        registry.registerModule(ButtonType.class, ButtonTypeCatalog.instance());
        registry.registerModule(MenuType.class, MenuTypeCatalog.instance());

        //Attached Data
        dataManager.register(ButtonData.class, ImmutableButtonData.class, new ButtonDataBuilder());
        dataManager.register(MenuData.class, ImmutableMenuData.class, new MenuDataBuilder());

        //Buttons
        dataManager.registerBuilder(Button.class, new DataButtonBuilder());
        DataButtonBuilder.addReader(new ItemButtonReader());
        DataButtonBuilder.addReader(new TextButtonReader());

        //Formatters
        dataManager.registerBuilder(Formatter.class, new DataFormatterBuilder());
        DataFormatterBuilder.addReader(new OrderedGridReader());
        DataFormatterBuilder.addReader(new StrictGridReader());

        //Builders
        dataManager.registerBuilder(TreeNode.class, new TreeNodeBuilder());
        dataManager.registerBuilder(SerializableMap.class, new DataSerializableMapBuilder());
        dataManager.registerBuilder(SerializablePair.class, new DataSerializablePairBuilder());

        //Trackers
        dataManager.registerBuilder(Tracker.class, new DataTrackerBuilder());
        DataTrackerBuilder.addReader(new TextTrackerReader());
        DataTrackerBuilder.addReader(new ButtonTrackerReader());

        //Inputs
        dataManager.registerBuilder(Input.class, new DataInputBuilder());
        DataInputBuilder.addReader(new SimpleInputReader());
        dataManager.registerBuilder(InputContext.class, new InputContextBuilder());
        InputContextBuilder.addReader(new EmptyInputContextReader());

        //Menus
        dataManager.registerBuilder(Menu.class, new DataMenuBuilder());
        DataMenuBuilder.addReader(new EmptyMenuReader());
        DataMenuBuilder.addReader(new SimpleMenuReader());
        dataManager.registerBuilder(MenuContext.class, new DataMenuContextBuilder());
        DataMenuContextBuilder.addReader(new EmptyMenuContextReader());
        DataMenuContextBuilder.addReader(new SimpleMenuContextReader());
        dataManager.registerBuilder(SendableMenu.class, new DataSendableMenuBuilder());

        //Page
        dataManager.registerBuilder(Page.class, new DataPageBuilder());
        DataPageBuilder.addReader(new AnvilTextPageReader());
        DataPageBuilder.addReader(new ChatButtonPageReader());
        DataPageBuilder.addReader(new ChatTextPageReader());
        DataPageBuilder.addReader(new InventoryButtonPageReader());

        //Finalizers
        MenuRegistry.addFinalizer(new ChatButtonFinalizer());
        MenuRegistry.addFinalizer(new ChatTextFinalizer());
        MenuRegistry.addFinalizer(new GridFinalizer());
        MenuRegistry.addFinalizer(new AnvilTextFinalizer());

        //Service
        Sponge.getServiceManager().setProvider(this, MenuService.class, new MenuServiceImpl());

        //Commands
        Sponge.getCommandManager().register(this, CommandSpec.builder()
                .executor(new ButtonCommand())
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("id"))), GenericArguments.onlyOne(GenericArguments.remainingJoinedStrings(Text.of("owner"))))
                .build(), "menuapibuttonevent");

        //Events
        EventManager eventManager = Sponge.getEventManager();
        eventManager.registerListeners(this, new InventoryListener());
        eventManager.registerListeners(this, new ChatListener());
        eventManager.registerListeners(this, new PlayerListener());
        eventManager.registerListeners(this, new TrackerListener());
    }

}
