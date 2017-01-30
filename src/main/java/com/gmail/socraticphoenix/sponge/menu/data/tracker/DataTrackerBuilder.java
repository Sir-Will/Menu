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
import com.gmail.socraticphoenix.sponge.menu.tracker.Tracker;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class DataTrackerBuilder extends AbstractDataBuilder<Tracker> {
    private static List<TrackerReader> readers = new ArrayList<>();

    public DataTrackerBuilder() {
        super(Tracker.class, 1);
    }

    public static void addReader(TrackerReader reader) {
        DataTrackerBuilder.readers.add(reader);
    }

    @Override
    protected Optional<Tracker> buildContent(DataView container) throws InvalidDataException {
        if(container.contains(MenuQueries.TRACKER_EVENT, MenuQueries.TRACKER_LISTENER, MenuQueries.TRACKER_VARS, MenuQueries.TRACKER_ID)) {
            try {
                Class<? extends MenuInputEvent> event = (Class<? extends MenuInputEvent>) Class.forName(container.getString(MenuQueries.TRACKER_EVENT).get());
                Class<? extends BiConsumer> listener;
                try {
                    listener = (Class<? extends BiConsumer>) Class.forName(container.getString(MenuQueries.TRACKER_LISTENER).get());
                } catch (ClassNotFoundException e) {
                    listener = DummyConsumer.class;
                }
                SerializableMap vars = container.getSerializable(MenuQueries.TRACKER_VARS, SerializableMap.class).get();
                String id = container.getString(MenuQueries.TRACKER_ID).get();
                for(TrackerReader reader : DataTrackerBuilder.readers) {
                    Optional<Tracker> trackerOptional = reader.read(event, listener, vars, id, container);
                    if(trackerOptional.isPresent()) {
                        return trackerOptional;
                    }
                }
            } catch (ClassNotFoundException ignore) {
                //this is impossible, unless I did something stupid... so...
                throw new IllegalStateException("This error should be impossible. If you see this error, please immediately notify the author of *MenuAPI*", ignore);
            }
        }
        return Optional.empty();
    }

}
