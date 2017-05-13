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
import com.gmail.socraticphoenix.sponge.menu.data.map.SerializableMap;
import com.gmail.socraticphoenix.sponge.menu.event.MenuInputEvent;
import com.gmail.socraticphoenix.sponge.menu.event.TargetMenuEvent;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 * Represents a listener that tracks a single {@link Button}, {@link Page} or {@link Menu}.
 *
 * @param <T> The type of {@link MenuInputEvent} this tracker listens to.
 */
public abstract class Tracker<T extends TargetMenuEvent> implements DataSerializable {
    private Class<T> event;
    private BiConsumer<SerializableMap, T> consumer;
    private SerializableMap vars;
    private String id;

    /**
     * Creates a new Tracker, which tracks the given event, using the given listener, and has the given variables and
     * id.
     *
     * @param event    The type of event this tracker tracks.
     * @param consumer The listener.
     * @param vars     The contextual variables associated with the listener.
     * @param id       The id of this tracker.
     */
    public Tracker(Class<T> event, BiConsumer<SerializableMap, T> consumer, SerializableMap vars, String id) {
        this.event = event;
        this.consumer = consumer;
        this.vars = vars;
        this.id = id;
        if (Stream.of(consumer.getClass().getDeclaredConstructors()).noneMatch(c -> c.getParameterCount() == 0)) {
            throw new IllegalArgumentException("BiConsumer does not have an empty constructor (or variables from outside a lambda expression are being used within it)");
        }
    }

    /**
     * @return The unique, composite id of this tracker, made up of its type, event, and id.
     */
    public abstract String compositeId();

    /**
     * Invokes this tracker with the given event.
     *
     * @param event The event to listen to.
     */
    public abstract void invoke(T event);

    /**
     * @return The type of event this tracker tracks.
     */
    public Class<T> getEvent() {
        return this.event;
    }

    /**
     * @return The listener.
     */
    public BiConsumer<SerializableMap, T> getConsumer() {
        return this.consumer;
    }

    /**
     * @return The contextual variables associated with this tracker.
     */
    public SerializableMap getVars() {
        return this.vars;
    }

    /**
     * Returns the id of this tracker. In general, trackers with the same {@link BiConsumer} listener should have the
     * same id, but this is not always the case. Without going into the implementation, trackers with the same id,
     * {@link BiConsumer} listener, target event, and target element (page, button, menu, etc.) will <i>not</i> stack,
     * meaning applying the exact same tracker twice will result in it being called only once. Therefore, if you are
     * applying the exact same tracker to an element intentionally, it is necessary to use different ids to distinguish
     * the two trackers.
     *
     * @return The id of this tracker.
     */
    public String id() {
        return this.id;
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer().set(Queries.CONTENT_VERSION, this.getContentVersion())
                .set(MenuQueries.TRACKER_EVENT, this.event.getName())
                .set(MenuQueries.TRACKER_LISTENER, this.consumer.getClass().getName())
                .set(MenuQueries.TRACKER_VARS, this.vars)
                .set(MenuQueries.TRACKER_ID, this.id);
    }

}
