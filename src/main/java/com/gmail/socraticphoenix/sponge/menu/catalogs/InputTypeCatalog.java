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

import com.gmail.socraticphoenix.sponge.menu.InputType;
import com.gmail.socraticphoenix.sponge.menu.InputTypes;
import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class InputTypeCatalog implements CatalogRegistryModule<InputType> {
    private static InputTypeCatalog instance = new InputTypeCatalog();

    public static InputTypeCatalog instance() {
        return InputTypeCatalog.instance;
    }

    private Map<String, InputType> types;

    private InputTypeCatalog() {
        this.types = new LinkedHashMap<>();
    }

    @Override
    public void registerDefaults() {
        register(InputTypes.INVENTORY_BUTTON);
        register(InputTypes.CHAT_TEXT);
        register(InputTypes.CHAT_BUTTON);
        register(InputTypes.ANVIL_TEXT_PAGE);
        register(InputTypes.EMPTY);
        register(InputTypes.UNKNOWN);
    }

    public void register(InputType type) {
        if(this.types.containsKey(type.getId())) {
            throw new IllegalArgumentException("InputType with id \"" + type.getId() + "\" is already registered");
        }
        this.types.put(type.getId(), type);
    }

    @Override
    public Optional<InputType> getById(String id) {
        return Optional.ofNullable(this.types.get(id));
    }

    @Override
    public Collection<InputType> getAll() {
        return this.types.values();
    }

}
