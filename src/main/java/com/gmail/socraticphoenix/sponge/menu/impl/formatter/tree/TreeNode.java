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
package com.gmail.socraticphoenix.sponge.menu.impl.formatter.tree;

import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

public class TreeNode implements DataSerializable {
    private List<TreeNode> children;
    private Text value;

    public TreeNode(List<TreeNode> children, Text value) {
        this.children = children;
        this.value = value;
    }

    public TreeNode(Text value) {
        this(Collections.emptyList(), value);
    }

    public List<TreeNode> getChildren() {
        return this.children;
    }

    public Text getValue() {
        return this.value;
    }

    public boolean isLeaf() {
        return this.children.isEmpty();
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    public void traverse(BiConsumer<TreeNode, Integer> consumer) {
        consumer.accept(this, 0);
        for(TreeNode child : this.children) {
            child.traversePaths(consumer, 1);
        }
    }

    private void traversePaths(BiConsumer<TreeNode, Integer> consumer, int level) {
        consumer.accept(this, level);
        for(TreeNode child : this.children) {
            child.traversePaths(consumer, level + 1);
        }
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer().set(Queries.CONTENT_VERSION, this.getContentVersion())
                .set(MenuQueries.TREE_CHILDREN, this.children)
                .set(MenuQueries.TREE_VALUE, this.value);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Builder parent;
        private Text value;
        private List<TreeNode> children;

        private Builder(Builder parent) {
            this();
            this.parent = parent;
        }

        public Builder() {
            this.value = Text.EMPTY;
            this.children = new ArrayList<>();
        }

        public Builder value(String value) {
            return this.value(Text.of(value));
        }

        public Builder value(Text value) {
            this.value = value;
            return this;
        }

        public Builder child() {
            return new Builder(this);
        }

        public Builder parent() {
            if(this.parent != null) {
                this.parent.children.add(this.build());
                return this.parent;
            } else {
                return this;
            }
        }

        public TreeNode build() {
            return new TreeNode(this.children, this.value);
        }

    }

}
