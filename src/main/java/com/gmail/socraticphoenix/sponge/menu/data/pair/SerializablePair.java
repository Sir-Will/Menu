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
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;

public class SerializablePair<L, R> implements DataSerializable {
    private L left;
    private R right;
    private Class<? extends L> leftTarget;
    private Class<? extends R> rightTarget;

    public SerializablePair(L left, R right, Class<? extends L> leftTarget, Class<? extends R> rightTarget) {
        this.left = left;
        this.right = right;
        this.leftTarget = leftTarget;
        this.rightTarget = rightTarget;
    }

    public L getLeft() {
        return this.left;
    }

    public R getRight() {
        return this.right;
    }

    public Class<? extends L> getLeftTarget() {
        return this.leftTarget;
    }

    public Class<? extends R> getRightTarget() {
        return this.rightTarget;
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer().set(Queries.CONTENT_VERSION, this.getContentVersion())
                .set(MenuQueries.SERIALIZABLE_PAIR_LEFT, this.left)
                .set(MenuQueries.SERIALIZABLE_PAIR_RIGHT, this.right)
                .set(MenuQueries.SERIALIZABLE_PAIR_LEFT_TARGET, this.leftTarget.getName())
                .set(MenuQueries.SERIALIZABLE_PAIR_RIGHT_TARGET, this.rightTarget.getName());
    }
}
