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

import com.gmail.socraticphoenix.sponge.menu.MenuPlugin;
import com.gmail.socraticphoenix.sponge.menu.MenuType;
import com.gmail.socraticphoenix.sponge.menu.MenuTypes;
import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class MenuTypeCatalog implements CatalogRegistryModule<MenuType> {
    private static MenuTypeCatalog instance = new MenuTypeCatalog();
    private Map<String, MenuType> types;

    private MenuTypeCatalog() {
        this.types = new LinkedHashMap<>();
        registerDefaults();
    }

    public static MenuTypeCatalog instance() {
        return MenuTypeCatalog.instance;
    }

    public void register(MenuType type) {
        if (this.types.containsKey(type.getId())) {
            throw new IllegalArgumentException("InputType with id \"" + type.getId() + "\" is already registered");
        }
        this.types.put(type.getId(), type);
    }

    @Override
    public void registerDefaults() {
        MenuPlugin.logger().info("REGISTER DEFAULTS CALLED ON MENUTYPECATALOG");
        register(MenuTypes.SIMPLE);
        register(MenuTypes.EMPTY);
        register(MenuTypes.UNKNOWN);
    }

    @Override
    public Optional<MenuType> getById(String id) {
        return Optional.ofNullable(this.types.get(id));
    }

    @Override
    public Collection<MenuType> getAll() {
        return this.types.values();
    }
    
}
