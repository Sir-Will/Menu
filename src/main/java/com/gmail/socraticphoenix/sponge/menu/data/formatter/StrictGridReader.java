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
package com.gmail.socraticphoenix.sponge.menu.data.formatter;

import com.flowpowered.math.vector.Vector2i;
import com.gmail.socraticphoenix.sponge.menu.Formatter;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.data.pair.SerializablePair;
import com.gmail.socraticphoenix.sponge.menu.impl.formatter.StrictGridFormatter;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StrictGridReader implements FormatterReader {

    @Override
    public Optional<Formatter> read(PluginContainer owner, String page, String target, DataView container) {
        if(container.contains(MenuQueries.STRICT_GRID_LOCATIONS)) {
            List<SerializablePair<String, Vector2i>> locations = (List<SerializablePair<String, Vector2i>>) (List<?>) container.getSerializableList(MenuQueries.STRICT_GRID_LOCATIONS, SerializablePair.class).get();
            Map<String, List<Vector2i>> locMap = new HashMap<>();
            for(SerializablePair<String, Vector2i> location : locations) {
                if(!locMap.containsKey(location.getLeft())) {
                    locMap.put(location.getLeft(), new ArrayList<>());
                }

                locMap.get(location.getLeft()).add(location.getRight());
            }

            return Optional.of(new StrictGridFormatter(locMap, owner));
        }

        return Optional.empty();
    }

}
