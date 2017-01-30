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
package com.gmail.socraticphoenix.sponge.menu.catalogs;

import com.gmail.socraticphoenix.sponge.menu.ButtonType;
import com.gmail.socraticphoenix.sponge.menu.ButtonTypes;
import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ButtonTypeCatalog implements CatalogRegistryModule<ButtonType> {
    private static ButtonTypeCatalog instance = new ButtonTypeCatalog();
    private Map<String, ButtonType> types;

    private ButtonTypeCatalog() {
        this.types = new LinkedHashMap<>();
        registerDefaults();
    }

    public static ButtonTypeCatalog instance() {
        return ButtonTypeCatalog.instance;
    }

    public void register(ButtonType type) {
        if (this.types.containsKey(type.getId())) {
            throw new IllegalArgumentException("InputType with id \"" + type.getId() + "\" is already registered");
        }
        this.types.put(type.getId(), type);
    }

    @Override
    public void registerDefaults() {
        register(ButtonTypes.TEXT);
        register(ButtonTypes.ITEM);
        register(ButtonTypes.UNKNOWN);
    }

    @Override
    public Optional<ButtonType> getById(String id) {
        return Optional.ofNullable(this.types.get(id));
    }

    @Override
    public Collection<ButtonType> getAll() {
        return this.types.values();
    }

}
