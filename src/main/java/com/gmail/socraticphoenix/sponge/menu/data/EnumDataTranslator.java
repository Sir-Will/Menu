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
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.persistence.DataTranslator;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class EnumDataTranslator<T extends Enum<T>> implements DataTranslator<T> {
    public static final DataQuery ENUM_NAME = DataQuery.of('.', "menuapi.enum.name");
    private Map<String, T> enumTypes;
    private TypeToken<T> typeToken;
    private String id;

    public EnumDataTranslator(TypeToken<T> type) {
        this.typeToken = type;
        this.enumTypes = new HashMap<>();
        this.id = type.getRawType().getName();
        Stream.of(type.getRawType().getEnumConstants()).map(e -> (Enum) e).forEach(e -> this.enumTypes.put(e.name(), (T) e));
    }

    @Override
    public TypeToken<T> getToken() {
        return this.typeToken;
    }

    @Override
    public T translate(DataView view) throws InvalidDataException {
        return this.enumTypes.get(view.getString(EnumDataTranslator.ENUM_NAME).get());
    }

    @Override
    public DataContainer translate(T obj) throws InvalidDataException {
        return new MemoryDataContainer().set(EnumDataTranslator.ENUM_NAME, obj.name());
    }

    @Override
    public String getId() {
        return "menuapi:enum." + this.id;
    }

    @Override
    public String getName() {
        return this.id + " Translator";
    }
}
