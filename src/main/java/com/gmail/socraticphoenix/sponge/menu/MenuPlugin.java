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

import com.flowpowered.math.vector.Vector2i;
import com.gmail.socraticphoenix.sponge.menu.command.ButtonCommand;
import com.gmail.socraticphoenix.sponge.menu.data.EnumDataTranslator;
import com.gmail.socraticphoenix.sponge.menu.data.MenuVariablesBuilder;
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
import com.gmail.socraticphoenix.sponge.menu.data.page.BookButtonPageReader;
import com.gmail.socraticphoenix.sponge.menu.data.page.BookTextPageReader;
import com.gmail.socraticphoenix.sponge.menu.data.page.ChatButtonPageReader;
import com.gmail.socraticphoenix.sponge.menu.data.page.ChatTextPageReader;
import com.gmail.socraticphoenix.sponge.menu.data.page.InventoryButtonPageReader;
import com.gmail.socraticphoenix.sponge.menu.data.page.PageBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.pair.SerializablePair;
import com.gmail.socraticphoenix.sponge.menu.data.pair.SerializablePairBuilder;
import com.gmail.socraticphoenix.sponge.menu.event.ButtonClickEvent;
import com.gmail.socraticphoenix.sponge.menu.impl.MenuServiceImpl;
import com.gmail.socraticphoenix.sponge.menu.impl.button.ItemButton;
import com.gmail.socraticphoenix.sponge.menu.impl.finalizer.GridFinalizer;
import com.gmail.socraticphoenix.sponge.menu.impl.formatter.StrictGridFormatter;
import com.gmail.socraticphoenix.sponge.menu.impl.menu.SimpleMenu;
import com.gmail.socraticphoenix.sponge.menu.impl.page.InventoryButtonPage;
import com.gmail.socraticphoenix.sponge.menu.listeners.InventoryListener;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.DataManager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.cause.Named;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.HashMap;
import java.util.List;

@Plugin(id = "menuapi", name = "Menu", version = "0.0.0", description = "GUIs", authors = "Socratic_Phoenix")
public class MenuPlugin {
    public static final String DEFAULT_BUTTON_ID = "default_id";
    public static final String NO_PLUGIN_ID = "missing_plugin";
    private static MenuPlugin instance;
    private static PluginContainer container;

    public MenuPlugin() {
        MenuPlugin.instance = this;
    }

    public static PluginContainer container() {
        return Sponge.getPluginManager().fromInstance(MenuPlugin.instance).get();
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

        //Data Translators
        dataManager.registerTranslator(Button.Type.class, new EnumDataTranslator<>(new TypeToken<Button.Type>() {}));
        dataManager.registerTranslator(Input.Type.class, new EnumDataTranslator<>(new TypeToken<Input.Type>() {}));
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
        dataManager.registerBuilder(MenuVariables.class, new MenuVariablesBuilder());

        //Page
        dataManager.registerBuilder(Page.class, new PageBuilder());
        PageBuilder.addReader(new BookButtonPageReader());
        PageBuilder.addReader(new BookTextPageReader());
        PageBuilder.addReader(new ChatButtonPageReader());
        PageBuilder.addReader(new ChatTextPageReader());
        PageBuilder.addReader(new InventoryButtonPageReader());

        //Pair
        dataManager.registerBuilder(SerializablePair.class, new SerializablePairBuilder());

        //Finalizers
        MenuRegistry.addFinalizer(new GridFinalizer());

        //Service
        Sponge.getServiceManager().setProvider(this, MenuService.class, new MenuServiceImpl());

        //Commands
        Sponge.getCommandManager().register(this, CommandSpec.builder()
                .executor(new ButtonCommand())
                .arguments(GenericArguments.string(Text.of("id")), GenericArguments.string(Text.of("owner")))
                .build(), "menuapibuttonevent");

        //Events
        Sponge.getEventManager().registerListeners(this, new InventoryListener());
    }

    @Listener
    public void onJoin(ClientConnectionEvent.Join ev, @First Player player) {
        List<Button> buttons = Lists.newArrayList(
                new ItemButton(Text.of(TextColors.AQUA, "Click Me"), ItemStack.of(ItemTypes.APPLE, 5), "aqua_button"),
                new ItemButton(Text.of(TextColors.RED, "No, Click Me!"), ItemStack.of(ItemTypes.BARRIER, 2), "red_button"),
                new ItemButton(Text.of(TextColors.GREEN, "A Humble Green Button Wishes to be Clicked"), ItemStack.of(ItemTypes.DIAMOND, 1), "green_button"),
                new ItemButton(Text.of(TextColors.LIGHT_PURPLE, "Spooky-Scary Skeletons"), ItemStack.of(ItemTypes.BONE, 2), "light_purple_button"),
                new ItemButton(Text.of(TextColors.YELLOW, "Yellow is the Color of Almost Gold"), ItemStack.of(ItemTypes.ANVIL, 2), "yellow_button")
        );

        Menu menu = new SimpleMenu(Lists.newArrayList(new InventoryButtonPage(Text.of(TextColors.GREEN, "This is a title"), buttons, 5, 5, "pageid")));
        Formatter formatter = new StrictGridFormatter(new HashMap<String, List<Vector2i>>() {{
            put("aqua_button", Lists.newArrayList(new Vector2i(0, 0)));
            put("red_button", Lists.newArrayList(new Vector2i(1, 1)));
            put("green_button", Lists.newArrayList(new Vector2i(2, 2)));
            put("light_purple_button", Lists.newArrayList(new Vector2i(3, 3)));
            put("yellow_button", Lists.newArrayList(new Vector2i(4, 4)));
        }}, this);

        MenuService service = Sponge.getServiceManager().provideUnchecked(MenuService.class);
        service.send(menu, player, this, new HashMap<>(), Sets.newHashSet(formatter));
    }

    @Listener
    public void onClick(ButtonClickEvent ev, @First Player player, @Named("id") String id, @Named("owner") PluginContainer owner, @Named("context") MenuContext context, @Named("menu") Menu menu) {
        if(id.equals("red_button")) {
            if (context.variables().get("red_clicked").isPresent()) {
                player.sendMessage(Text.of("You already clicked the red button!"));
            } else {
                context.variables().put("red_clicked", true, boolean.class);
                player.sendMessage(Text.of("You clicked the red button!"));
            }
        } else {
            player.sendMessage(Text.of("You clicked a button!"));
        }
    }

}
