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
package com.gmail.socraticphoenix.sponge.menu.data.map;

import com.gmail.socraticphoenix.sponge.menu.builder.SerializableMapBuilder;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.data.pair.SerializablePair;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class SerializableMap<K, V> implements DataSerializable {
    private static Map<Class, Class> primitivesByBox;

    static {
        SerializableMap.primitivesByBox = new HashMap<>();
        SerializableMap.primitivesByBox.put(Byte.class, byte.class);
        SerializableMap.primitivesByBox.put(Short.class, short.class);
        SerializableMap.primitivesByBox.put(Character.class, char.class);
        SerializableMap.primitivesByBox.put(Integer.class, int.class);
        SerializableMap.primitivesByBox.put(Long.class, long.class);
        SerializableMap.primitivesByBox.put(Float.class, float.class);
        SerializableMap.primitivesByBox.put(Double.class, double.class);
        SerializableMap.primitivesByBox.put(Boolean.class, boolean.class);
        SerializableMap.primitivesByBox.put(Void.class, void.class);

    }

    private Map<K, SerializablePair<K, V>> variables;

    public static <L, R> com.gmail.socraticphoenix.sponge.menu.builder.SerializableMapBuilder<L, R> builder() {
        return new SerializableMapBuilder<>();
    }

    public SerializableMap(Map<K, SerializablePair<K, V>> variables) {
        this.variables = variables;
    }

    public SerializablePair<K, V> rawGet(K key) {
        return this.variables.get(key);
    }

    public void rawPut(SerializablePair<K, V> pair) {
        this.put(pair.getLeft(), pair.getRight(), pair.getLeftTarget(), pair.getRightTarget());
    }

    public SerializableMap() {
        this.variables = new HashMap<>();
    }

    public Set<K> keySet() {
        return this.variables.keySet();
    }

    public Optional<V> get(K key) {
        SerializablePair<K, V> pair = this.variables.get(key);
        return pair == null ? Optional.empty() : Optional.of(pair.getRight());
    }

    public void put(K key, V object, Class<? extends K> keyType, Class<? extends V> valueType) {
        this.variables.put(key, new SerializablePair(key, object, keyType, valueType));
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        List<SerializablePair<K, V>> variables = new ArrayList<>();
        variables.addAll(this.variables.values());
        return new MemoryDataContainer().set(Queries.CONTENT_VERSION, this.getContentVersion())
                .set(MenuQueries.VARIABLES, variables);
    }

}
