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
package com.gmail.socraticphoenix.sponge.menu.impl.page.target;

import com.gmail.socraticphoenix.sponge.menu.PageTarget;
import com.gmail.socraticphoenix.sponge.menu.impl.page.InventoryButtonPage;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

public class GridTarget implements PageTarget<InventoryButtonPage> {
    private ItemStack[][] grid;
    private int x;
    private int y;

    public GridTarget(int x, int y) {
        this.grid = new ItemStack[y][x];
        this.x = x;
        this.y = y;
    }

    public Optional<ItemStack> get(int x, int y) {
        return this.contains(x, y) ? Optional.ofNullable(this.grid[y][x]) : Optional.empty();
    }

    public boolean set(int x, int y, ItemStack stack) {
        if(this.contains(x, y)) {
            this.grid[y][x] = stack;
            return true;
        }
        return false;
    }

    public int height() {
        return this.y;
    }

    public int length() {
        return this.x;
    }

    public boolean contains(int x, int y) {
        return x >= 0 && y >= 0 && y < this.grid.length && x < this.grid[y].length;
    }

}
