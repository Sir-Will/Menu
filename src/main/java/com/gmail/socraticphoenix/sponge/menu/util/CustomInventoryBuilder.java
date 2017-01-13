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
package com.gmail.socraticphoenix.sponge.menu.util;

import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.item.inventory.Carrier;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetype;
import org.spongepowered.api.item.inventory.InventoryProperty;

import java.util.Locale;
import java.util.function.Consumer;

import static com.google.common.base.Preconditions.checkNotNull;

public class CustomInventoryBuilder implements Inventory.Builder {
    private Inventory.Builder builder;

    public CustomInventoryBuilder() {
        this.builder = Inventory.builder();
    }

    public CustomInventoryBuilder of(InventoryArchetype archetype) {
        this.builder.of(archetype);
        return this;
    }

    public CustomInventoryBuilder property(String name, InventoryProperty property) {
        this.builder.property(name, property);
        return this;
    }

    public CustomInventoryBuilder property(InventoryProperty property) {
        return this.property(checkNotNull(property, "property").getClass().getSimpleName().toLowerCase(Locale.ENGLISH), property);
    }

    public CustomInventoryBuilder withCarrier(Carrier carrier) {
        this.builder.withCarrier(carrier);
        return this;
    }

    public <E extends InteractInventoryEvent> CustomInventoryBuilder listener(Class<E> type, Consumer<E> listener) {
        this.builder.listener(type, listener);
        return this;
    }

    public CustomInventoryBuilder forCarrier(Carrier carrier) {
        this.builder.forCarrier(carrier);
        return this;
    }

    public CustomInventoryBuilder forCarrier(Class<? extends Carrier> carrier) {
        this.builder.forCarrier(carrier);
        return this;
    }

    public Inventory build(Object plugin) {
        return this.builder.build(plugin);
    }

    public CustomInventoryBuilder from(Inventory value) {
        this.builder.from(value);
        return this;
    }

    public CustomInventoryBuilder reset() {
        this.builder.reset();
        return this;
    }


}
