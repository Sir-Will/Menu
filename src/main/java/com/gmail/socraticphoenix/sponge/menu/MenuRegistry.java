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

import com.gmail.socraticphoenix.sponge.menu.tracker.Tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class MenuRegistry {
    private static List<Finalizer> finalizers;
    private static Map<String, Tracker> trackers;

    static {
        MenuRegistry.finalizers = new ArrayList<>();
        MenuRegistry.trackers = new HashMap<>();
    }

    public static void addTracker(Tracker tracker) {
        if(!MenuRegistry.trackers.containsKey(tracker.compositeId())) {
            MenuRegistry.trackers.put(tracker.compositeId(), tracker);
        }
    }

    public static Stream<Tracker> getTrackers() {
        return MenuRegistry.trackers.values().stream();
    }

    public static void addFinalizer(Finalizer finalizer) {
        MenuRegistry.finalizers.add(finalizer);
    }

    public static Finalizer lookForAppropriate(Class target, Class page) {
        return MenuRegistry.finalizers.stream().filter(f -> f.target().isAssignableFrom(target) && f.page().isAssignableFrom(page)).findFirst().orElseThrow(() -> new IllegalArgumentException("No finalizer for page \"" + page + "\" and target \"" + target + "\""));
    }

}
