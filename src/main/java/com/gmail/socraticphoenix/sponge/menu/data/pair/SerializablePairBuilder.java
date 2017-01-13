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
package com.gmail.socraticphoenix.sponge.menu.data.pair;

import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.Optional;

public class SerializablePairBuilder extends AbstractDataBuilder<SerializablePair> {

    public SerializablePairBuilder() {
        super(SerializablePair.class, 1);
    }

    @Override
    protected Optional<SerializablePair> buildContent(DataView container) throws InvalidDataException {
        if(container.contains(MenuQueries.SERIALIZABLE_PAIR_RIGHT_TARGET, MenuQueries.SERIALIZABLE_PAIR_LEFT_TARGET, MenuQueries.SERIALIZABLE_PAIR_LEFT, MenuQueries.SERIALIZABLE_PAIR_RIGHT)) {
            String leftTargetS = container.getString(MenuQueries.SERIALIZABLE_PAIR_LEFT_TARGET).get();
            String rightTargetS = container.getString(MenuQueries.SERIALIZABLE_PAIR_RIGHT_TARGET).get();
            try {
                Class leftTarget = Class.forName(leftTargetS);
                Class rightTarget = Class.forName(rightTargetS);
                return Optional.of(new SerializablePair(DataSerializable.class.isAssignableFrom(leftTarget) ? container.getSerializable(MenuQueries.SERIALIZABLE_PAIR_LEFT, leftTarget).get() : container.getObject(MenuQueries.SERIALIZABLE_PAIR_LEFT, leftTarget).get(), DataSerializable.class.isAssignableFrom(rightTarget) ? container.getSerializable(MenuQueries.SERIALIZABLE_PAIR_RIGHT, rightTarget).get() : container.getObject(MenuQueries.SERIALIZABLE_PAIR_RIGHT, rightTarget).get(), leftTarget, rightTarget));
            } catch (ClassNotFoundException e) {
                throw new InvalidDataException("Could not find classes in SerializablePair", e);
            }
        }
        return Optional.empty();
    }


}
