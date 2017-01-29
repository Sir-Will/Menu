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

import com.gmail.socraticphoenix.sponge.menu.catalogs.InputTypeCatalog;
import com.gmail.socraticphoenix.sponge.menu.command.ButtonCommand;
import com.gmail.socraticphoenix.sponge.menu.data.CatalogDataTranslator;
import com.gmail.socraticphoenix.sponge.menu.data.EnumDataTranslator;
import com.gmail.socraticphoenix.sponge.menu.data.SerializableMapBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.attached.button.ButtonData;
import com.gmail.socraticphoenix.sponge.menu.data.attached.button.ButtonDataBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.attached.button.ImmutableButtonData;
import com.gmail.socraticphoenix.sponge.menu.data.attached.player.ImmutableMenuData;
import com.gmail.socraticphoenix.sponge.menu.data.attached.player.MenuData;
import com.gmail.socraticphoenix.sponge.menu.data.attached.player.MenuDataBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.button.ButtonBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.button.ItemButtonReader;
import com.gmail.socraticphoenix.sponge.menu.data.button.TextButtonReader;
import com.gmail.socraticphoenix.sponge.menu.data.formatter.FormatterBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.formatter.OrderedGridReader;
import com.gmail.socraticphoenix.sponge.menu.data.formatter.StrictGridReader;
import com.gmail.socraticphoenix.sponge.menu.data.formatter.tree.TreeNodeBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.input.EmptyInputContextReader;
import com.gmail.socraticphoenix.sponge.menu.data.input.InputBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.input.InputContextBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.input.SimpleInputReader;
import com.gmail.socraticphoenix.sponge.menu.data.menu.EmptyMenuContextReader;
import com.gmail.socraticphoenix.sponge.menu.data.menu.EmptyMenuReader;
import com.gmail.socraticphoenix.sponge.menu.data.menu.MenuBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.menu.MenuContextBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.menu.SendableMenuBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.menu.SimpleMenuContextReader;
import com.gmail.socraticphoenix.sponge.menu.data.menu.SimpleMenuReader;
import com.gmail.socraticphoenix.sponge.menu.data.page.AnvilTextPageReader;
import com.gmail.socraticphoenix.sponge.menu.data.page.ChatButtonPageReader;
import com.gmail.socraticphoenix.sponge.menu.data.page.ChatTextPageReader;
import com.gmail.socraticphoenix.sponge.menu.data.page.InventoryButtonPageReader;
import com.gmail.socraticphoenix.sponge.menu.data.page.PageBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.pair.SerializablePair;
import com.gmail.socraticphoenix.sponge.menu.data.pair.SerializablePairBuilder;
import com.gmail.socraticphoenix.sponge.menu.impl.MenuServiceImpl;
import com.gmail.socraticphoenix.sponge.menu.impl.finalizer.ChatButtonFinalizer;
import com.gmail.socraticphoenix.sponge.menu.impl.finalizer.ChatTextFinalizer;
import com.gmail.socraticphoenix.sponge.menu.impl.finalizer.GridFinalizer;
import com.gmail.socraticphoenix.sponge.menu.impl.formatter.tree.TreeNode;
import com.gmail.socraticphoenix.sponge.menu.listeners.ChatListener;
import com.gmail.socraticphoenix.sponge.menu.listeners.InventoryListener;
import com.gmail.socraticphoenix.sponge.menu.listeners.PlayerListener;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.DataManager;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
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

    @Listener
    public void onPreInit(GamePreInitializationEvent ev) {

    }

    @Listener
    public void init(GameInitializationEvent ev) {
        DataManager dataManager = Sponge.getDataManager();
        GameRegistry registry = Sponge.getRegistry();

        //Catalogs
        registry.registerModule(InputType.class, InputTypeCatalog.instance());
        dataManager.registerTranslator(InputType.class, new CatalogDataTranslator<>(InputType.class, InputTypeCatalog.instance()));

        //Data Translators
        dataManager.registerTranslator(Button.Type.class, new EnumDataTranslator<>(new TypeToken<Button.Type>() {}));
        dataManager.registerTranslator(Menu.Type.class, new EnumDataTranslator<>(new TypeToken<Menu.Type>() {}));

        //Attached Data
        dataManager.register(ButtonData.class, ImmutableButtonData.class, new ButtonDataBuilder());
        dataManager.register(MenuData.class, ImmutableMenuData.class, new MenuDataBuilder());

        //Buttons
        dataManager.registerBuilder(Button.class, new ButtonBuilder());
        ButtonBuilder.addReader(new ItemButtonReader());
        ButtonBuilder.addReader(new TextButtonReader());

        //Formatters
        dataManager.registerBuilder(Formatter.class, new FormatterBuilder());
        FormatterBuilder.addReader(new OrderedGridReader());
        FormatterBuilder.addReader(new StrictGridReader());

        dataManager.registerBuilder(TreeNode.class, new TreeNodeBuilder());

        //Inputs
        dataManager.registerBuilder(Input.class, new InputBuilder());
        InputBuilder.addReader(new SimpleInputReader());

        dataManager.registerBuilder(InputContext.class, new InputContextBuilder());
        InputContextBuilder.addReader(new EmptyInputContextReader());

        //Menus
        dataManager.registerBuilder(Menu.class, new MenuBuilder());
        MenuBuilder.addReader(new EmptyMenuReader());
        MenuBuilder.addReader(new SimpleMenuReader());

        dataManager.registerBuilder(MenuContext.class, new MenuContextBuilder());
        MenuContextBuilder.addReader(new EmptyMenuContextReader());
        MenuContextBuilder.addReader(new SimpleMenuContextReader());

        dataManager.registerBuilder(SendableMenu.class, new SendableMenuBuilder());
        dataManager.registerBuilder(SerializableMap.class, new SerializableMapBuilder());

        //Page
        dataManager.registerBuilder(Page.class, new PageBuilder());
        PageBuilder.addReader(new AnvilTextPageReader());
        PageBuilder.addReader(new ChatButtonPageReader());
        PageBuilder.addReader(new ChatTextPageReader());
        PageBuilder.addReader(new InventoryButtonPageReader());

        //Pair
        dataManager.registerBuilder(SerializablePair.class, new SerializablePairBuilder());

        //Finalizers
        MenuRegistry.addFinalizer(new ChatButtonFinalizer());
        MenuRegistry.addFinalizer(new ChatTextFinalizer());
        MenuRegistry.addFinalizer(new GridFinalizer());
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
    }

}
