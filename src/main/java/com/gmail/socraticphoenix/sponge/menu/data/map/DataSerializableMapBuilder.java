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

import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.data.pair.SerializablePair;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DataSerializableMapBuilder extends AbstractDataBuilder<SerializableMap> {

    public DataSerializableMapBuilder() {
        super(SerializableMap.class, 1);
    }

    @Override
    protected Optional<SerializableMap> buildContent(DataView container) throws InvalidDataException {
        if (container.contains(MenuQueries.VARIABLES)) {
            List<SerializablePair> variables = container.getSerializableList(MenuQueries.VARIABLES, SerializablePair.class).get();
            Map<Object, SerializablePair> varMap = new HashMap<>();
            for (SerializablePair variable : variables) {
                varMap.put(variable.getLeft(), variable);
            }
            return Optional.of(new SerializableMap(varMap));
        }
        return Optional.empty();
    }

}
