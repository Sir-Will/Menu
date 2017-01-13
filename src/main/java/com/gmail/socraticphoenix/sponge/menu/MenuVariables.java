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

import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.data.pair.SerializablePair;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MenuVariables implements DataSerializable {
    private static Map<Class, Class> primitivesByBox;

    static {
        MenuVariables.primitivesByBox = new HashMap<>();
        MenuVariables.primitivesByBox.put(Byte.class, byte.class);
        MenuVariables.primitivesByBox.put(Short.class, short.class);
        MenuVariables.primitivesByBox.put(Character.class, char.class);
        MenuVariables.primitivesByBox.put(Integer.class, int.class);
        MenuVariables.primitivesByBox.put(Long.class, long.class);
        MenuVariables.primitivesByBox.put(Float.class, float.class);
        MenuVariables.primitivesByBox.put(Double.class, double.class);
        MenuVariables.primitivesByBox.put(Boolean.class, boolean.class);
        MenuVariables.primitivesByBox.put(Void.class, void.class);

    }

    private Map<String, SerializablePair<String, ?>> variables;

    public MenuVariables(Map<String, SerializablePair<String, ?>> variables) {
        this.variables = variables;
    }

    public MenuVariables() {
        this.variables = new HashMap<>();
    }

    public <T> Optional<T> get(String key) {
        SerializablePair<String, ?> pair = variables.get(key);
        return pair == null ? Optional.empty() : (Optional<T>) Optional.of(pair.getRight());
    }

    public <T> void put(String key, T object, Class<? super T> type) {
        if (Sponge.getDataManager().getTranslator(type).isPresent() || DataSerializable.class.isAssignableFrom(type) || type.isPrimitive() || MenuVariables.primitivesByBox.containsKey(type)) {
            this.variables.put(key, new SerializablePair<>(key, object, String.class, type));
        } else {
            throw new IllegalArgumentException(type + " is not DataSerializable or DataTranslatable");
        }
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        List<SerializablePair<String, ?>> variables = new ArrayList<>();
        variables.addAll(this.variables.values());
        return new MemoryDataContainer().set(Queries.CONTENT_VERSION, this.getContentVersion())
                .set(MenuQueries.VARIABLES, variables);
    }

}
