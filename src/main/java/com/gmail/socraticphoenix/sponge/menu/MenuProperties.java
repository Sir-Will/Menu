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
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Represents a set of properties that are applied to a {@link Menu} when it is sent to an {@link Player}. This class is
 * expected to evolve, however, constructors will always be backwards compatible, linking to new constructors and
 * providing default values.
 */
public class MenuProperties implements DataSerializable {
    private boolean persistent;
    private boolean restrictChat;

    //IMPORTANT NOTE: By contract, the below constructor must remain FOR ALL ETERNITY, and simple link to any new constructors and provide default values

    /**
     * Constructs a new set of properties with the given parameters
     *
     * @param persistent   If true, any active {@link Menu Menus} will persist between server restarts, and be reapplied
     *                     to players when the rejoin.
     * @param restrictChat If true, any Player with an active {@link Menu} will not receive chat messages, and their
     *                     chat will be consistently cleared if their current {@link Page} returns true for {@link
     *                     Page#isChatBased()}.
     */
    public MenuProperties(boolean persistent, boolean restrictChat) {
        this.persistent = persistent;
        this.restrictChat = restrictChat;
    }

    /**
     * @return True if the these properties indicate that a {@link Menu} should be persistent.
     */
    public boolean isPersistent() {
        return this.persistent;
    }

    /**
     * @return True if these properties indicated that a {@link Menu} should restrict chat.
     */
    public boolean isRestrictChat() {
        return this.restrictChat;
    }


    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer().set(Queries.CONTENT_VERSION, this.getContentVersion())
                .set(MenuQueries.PROPERTIES.then("persistent"), this.persistent)
                .set(MenuQueries.PROPERTIES.then("restrict_chat"), this.restrictChat);
    }

}
