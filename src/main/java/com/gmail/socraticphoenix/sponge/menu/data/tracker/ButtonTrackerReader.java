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
package com.gmail.socraticphoenix.sponge.menu.data.tracker;

import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.data.map.SerializableMap;
import com.gmail.socraticphoenix.sponge.menu.event.MenuInputEvent;
import com.gmail.socraticphoenix.sponge.menu.impl.tracker.ButtonTracker;
import com.gmail.socraticphoenix.sponge.menu.tracker.Tracker;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class ButtonTrackerReader implements TrackerReader {

    @Override
    public Optional<Tracker> read(Class<? extends MenuInputEvent> event, Class<? extends BiConsumer> listener, SerializableMap vars, String id, DataView container) {
        if(container.contains(MenuQueries.TRACKER_BUTTON, MenuQueries.TRACKER_PLUGIN) && event.equals(MenuInputEvent.Text.class)) {
            String button = container.getString(MenuQueries.TRACKER_BUTTON).get();
            String plugin = container.getString(MenuQueries.TRACKER_PLUGIN).get();
            Constructor<? extends BiConsumer> defCon = (Constructor<? extends BiConsumer>) Stream.of(listener.getDeclaredConstructors()).filter(c -> c.getParameterCount() == 0).findFirst().get();
            boolean access = defCon.isAccessible();
            defCon.setAccessible(true);
            try {
                Optional<Tracker> trackerOptional = Optional.of(new ButtonTracker((Class<MenuInputEvent.Button>) event, defCon.newInstance(), vars, button, plugin, id));
                defCon.setAccessible(access);
                return trackerOptional;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                defCon.setAccessible(access);
                throw new InvalidDataException("Unable to construct BiConsumer listener \"" + listener.getName() + "\"", e);
            }
        }
        return Optional.empty();
    }

}
