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
package com.gmail.socraticphoenix.sponge.menu.data;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.data.persistence.DataTranslator;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.registry.CatalogRegistryModule;

public class CatalogDataTranslator<T extends CatalogType> implements DataTranslator<T> {
    private Class<T> target;
    private CatalogRegistryModule<T> module;

    public CatalogDataTranslator(Class<T> target, CatalogRegistryModule<T> module) {
        this.target = target;
        this.module = module;
    }

    @Override
    public TypeToken<T> getToken() {
        return TypeToken.of(this.target);
    }

    @Override
    public T translate(DataView view) throws InvalidDataException {
        return this.module.getById(view.getString(MenuQueries.CATALOG_ID).orElseThrow(() -> new InvalidDataException("No string located at query \"" + MenuQueries.CATALOG_ID.asString('.')))).orElseThrow(() -> new InvalidDataException("No " + this.target.getName() + " for id \"" + view.getString(MenuQueries.CATALOG_ID).get() + "\""));
    }

    @Override
    public DataContainer translate(T obj) throws InvalidDataException {
        return new MemoryDataContainer().set(Queries.CONTENT_VERSION, 1)
                .set(MenuQueries.CATALOG_ID, obj.getId());
    }

    @Override
    public String getId() {
        return "menuapi:catalog_" + this.target.getName();
    }

    @Override
    public String getName() {
        return this.target.getName() + " Translator";
    }

}
