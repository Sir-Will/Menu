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
package com.gmail.socraticphoenix.sponge.menu.builder;

import com.gmail.socraticphoenix.sponge.menu.Button;
import com.gmail.socraticphoenix.sponge.menu.Menu;
import com.gmail.socraticphoenix.sponge.menu.Tracker;
import com.gmail.socraticphoenix.sponge.menu.data.map.SerializableMap;
import com.gmail.socraticphoenix.sponge.menu.event.MenuInputEvent;
import com.gmail.socraticphoenix.sponge.menu.impl.button.ItemButton;
import com.gmail.socraticphoenix.sponge.menu.impl.button.TextButton;
import com.gmail.socraticphoenix.sponge.menu.impl.tracker.ButtonTracker;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * A builder which constructs an {@link Button}. It requires a reference to a parent {@link ButtonPageBuilder}, and an
 * instance can be obtained through {@link ButtonPageBuilder#button(Button)}. When using this builder, it is necessary
 * to call {@link ButtonBuilder#id(String)} before calling any other methods.
 */
public class ButtonBuilder {
    private ButtonPageBuilder parent;
    private PluginContainer plugin;
    private ItemStack icon;
    private Text title;
    private String id;
    private List<Tracker> trackers;

    ButtonBuilder(ButtonPageBuilder parent) {
        this.parent = parent;
        this.title = Text.of("Button");
        this.id = "default_id";
        this.plugin = parent.getParent().getContainer();
        this.trackers = new ArrayList<>();
    }

    /**
     * Sets the icon of this button. Setting the icon will cause this builder to construct a {@link ItemButton}, instead
     * of a {@link TextButton}.
     *
     * @param icon The {@link ItemStack} to use as an icon.
     *
     * @return This, for method chaining.
     */
    public ButtonBuilder icon(ItemStack icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Sets the icon of this button. Setting the icon will cause this builder to construct a {@link ItemButton}, instead
     * of a {@link TextButton}.
     *
     * @param type     The {@link ItemType} to use as an icon.
     * @param quantity The quantity to use for the icon.
     *
     * @return This, for method chaining.
     */
    public ButtonBuilder icon(ItemType type, int quantity) {
        return this.icon(ItemStack.of(type, quantity));
    }

    /**
     * Sets the icon of this button. Setting the icon will cause this builder to construct a {@link ItemButton}, instead
     * of a {@link TextButton}.
     *
     * @param type The {@link ItemType} to use as an icon.
     *
     * @return This, for method chaining.
     */
    public ButtonBuilder icon(ItemType type) {
        return this.icon(type, 1);
    }

    /**
     * Sets the title of this button.
     *
     * @param title The title of this button.
     *
     * @return This, for method chaining.
     */
    public ButtonBuilder title(Text title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the title of this button. This is a convenience method, which is equivalent to calling {@code
     * builder.title(Text.of(elements))}.
     *
     * @param elements The text elements of the title of this button.
     *
     * @return This, for method chaining.
     */
    public ButtonBuilder title(Objects... elements) {
        return this.title(Text.of(elements));
    }

    /**
     * Sets the id of this button. This method must be called first.
     *
     * @param id The id of this button.
     *
     * @return This, for method chaining.
     */
    public ButtonBuilder id(String id) {
        this.id = id;
        return this;
    }

    /**
     * Applies a tracker to this button, with the given listener, contextual variables, and id. Because {@link Menu
     * Menus} are fully serializable, the listener consumer cannot have a constructor (or, if it is a lambda, use any
     * variables from outside the lambda expression). To get around this, contextual variables are transmitted to the
     * listener through a {@link SerializableMap}.
     *
     * @param listener The listener.
     * @param vars     The contextual variables to transmit to the listener.
     * @param id       The id of the tracker. (See {@link Tracker#id()} for conventions).
     *
     * @return This, for method chaining.
     */
    public ButtonBuilder tracker(BiConsumer<SerializableMap, MenuInputEvent.Button> listener, SerializableMap vars, String id) {
        this.trackers.add(new ButtonTracker(MenuInputEvent.Button.class, listener, vars, this.id, this.plugin.getId(), id));
        return this;
    }

    /**
     * Applies a tracker to this button, with the given listener and id. Because {@link Menu
     * Menus} are fully serializable, the listener consumer cannot have a constructor (or, if it is a lambda, use any
     * variables from outside the lambda expression). If you need to transmit variables to the listener, use the {@link
     * ButtonBuilder#tracker(BiConsumer, SerializableMap, String)} method.
     *
     * @param listener The listener.
     * @param id       The id of the tracker. (See {@link Tracker#id()} for conventions).
     *
     * @return This, for method chaining.
     */
    public ButtonBuilder tracker(BiConsumer<SerializableMap, MenuInputEvent.Button> listener, String id) {
        return this.tracker(listener, new SerializableMap(), id);
    }

    /**
     * Constructs the {@link Button} and applies it to the parent {@link ButtonPageBuilder}. {@link Button Buttons} are
     * applied to the parent builder in the order that this method is called.
     */
    public void finish() {
        Button button;
        if (this.icon != null) {
            button = new ItemButton(this.title, this.icon, this.trackers, this.id);
        } else {
            button = new TextButton(this.title, this.id, this.trackers);
        }
        this.parent.button(button);
    }

}
