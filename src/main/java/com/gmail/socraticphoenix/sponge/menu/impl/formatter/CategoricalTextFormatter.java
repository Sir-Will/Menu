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

import com.gmail.socraticphoenix.sponge.menu.Formatter;
import com.gmail.socraticphoenix.sponge.menu.TextButtonPage;
import com.gmail.socraticphoenix.sponge.menu.data.DataApplicator;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import com.gmail.socraticphoenix.sponge.menu.impl.formatter.tree.TreeNode;
import com.gmail.socraticphoenix.sponge.menu.impl.page.target.TextTarget;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

public class CategoricalTextFormatter extends Formatter<TextButtonPage, TextTarget> {
    private TreeNode categories;
    private Text indent;

    public CategoricalTextFormatter(Object plugin, TreeNode categories, Text indent) {
        super(TextButtonPage.class, TextTarget.class, plugin);
        this.categories = categories;
        this.indent = indent;
    }

    @Override
    public void format(TextButtonPage page, TextTarget target, PluginContainer owner) {
        target.getBuilder().append(page.title()).append(Text.NEW_LINE);
        this.categories.traverse((node, level) -> {
            Text.Builder indent = Text.builder();
            for (int i = 0; i < level; i++) {
                indent.append(this.indent);
            }

            if(node.isLeaf()) {
                String id = node.getValue().toPlain();
                page.buttons().stream().filter(b -> b.id().equals(id)).forEach(b -> target.getBuilder().append(indent.build()).append(DataApplicator.createText(b, owner)).append(Text.NEW_LINE));
            } else {
                target.getBuilder().append(indent.build()).append(node.getValue()).append(Text.NEW_LINE);
            }
        });
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(MenuQueries.CATEGORICAL_TEXT_CATEGORIES, this.categories)
                .set(MenuQueries.CATEGORICAL_TEXT_INDENT, this.indent);
    }

}
