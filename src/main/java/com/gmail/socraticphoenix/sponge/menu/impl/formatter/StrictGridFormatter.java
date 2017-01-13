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
package com.gmail.socraticphoenix.sponge.menu.impl.formatter;

import com.flowpowered.math.vector.Vector2i;
import com.gmail.socraticphoenix.sponge.menu.Button;
import com.gmail.socraticphoenix.sponge.menu.Formatter;
import com.gmail.socraticphoenix.sponge.menu.data.DataApplicator;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.data.pair.SerializablePair;
import com.gmail.socraticphoenix.sponge.menu.impl.page.InventoryButtonPage;
import com.gmail.socraticphoenix.sponge.menu.impl.page.target.GridTarget;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrictGridFormatter extends Formatter<InventoryButtonPage, GridTarget> {
    private Map<String, List<Vector2i>> locations;

    public StrictGridFormatter(Map<String, List<Vector2i>> locations, Object plugin) {
        super(InventoryButtonPage.class, GridTarget.class, plugin);
        Map<String, List<Vector2i>> copy = new HashMap<>();
        for (String key : locations.keySet()) {
            copy.put(key, Collections.unmodifiableList(locations.get(key)));
        }
        this.locations = Collections.unmodifiableMap(copy);
    }

    public StrictGridFormatter(Map<String, List<Vector2i>> locations, PluginContainer plugin) {
        super(InventoryButtonPage.class, GridTarget.class, plugin);
        Map<String, List<Vector2i>> copy = new HashMap<>();
        for (String key : locations.keySet()) {
            copy.put(key, Collections.unmodifiableList(locations.get(key)));
        }
        this.locations = Collections.unmodifiableMap(copy);
    }

    @Override
    public void format(InventoryButtonPage page, GridTarget target, PluginContainer owner) {
        Map<String, List<Vector2i>> locations = new HashMap<>();
        for (String key : this.locations.keySet()) {
            List<Vector2i> copy = new ArrayList<>();
            copy.addAll(this.locations.get(key));
            locations.put(key, copy);
        }

        for (Button button : page.buttons()) {
            String id = button.id();
            if (locations.containsKey(id)) {
                List<Vector2i> choices = locations.get(id);
                if (choices.size() >= 1) {
                    Vector2i targetLoc = choices.remove(0);
                    target.set(targetLoc.getX(), targetLoc.getY(), DataApplicator.createIcon(button, owner));
                }
            }
        }
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        List<SerializablePair<String, Vector2i>> locations = new ArrayList<>();
        for (Map.Entry<String, List<Vector2i>> location : this.locations.entrySet()) {
            for (Vector2i vector : location.getValue()) {
                locations.add(new SerializablePair<>(location.getKey(), vector, String.class, Vector2i.class));
            }
        }
        return super.toContainer()
                .set(MenuQueries.STRICT_GRID_LOCATIONS, locations);
    }
}
