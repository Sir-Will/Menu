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
package com.gmail.socraticphoenix.sponge.menu.data.input;

import com.gmail.socraticphoenix.sponge.menu.Input;
import com.gmail.socraticphoenix.sponge.menu.InputType;
import com.gmail.socraticphoenix.sponge.menu.data.MenuQueries;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InputBuilder extends AbstractDataBuilder<Input> {
    private static List<InputReader> readers = new ArrayList<>();

    public InputBuilder() {
        super(Input.class, 1);
    }

    public static void addReader(InputReader reader) {
        InputBuilder.readers.add(reader);
    }

    @Override
    protected Optional<Input> buildContent(DataView container) throws InvalidDataException {
        if (container.contains(MenuQueries.INPUT_TYPE)) {
            InputType type = container.getCatalogType(MenuQueries.INPUT_TYPE, InputType.class).get();
            for (InputReader reader : InputBuilder.readers) {
                Optional<Input> inputOptional = reader.read(type, container);
                if (inputOptional.isPresent()) {
                    return inputOptional;
                }
            }
        }
        return Optional.empty();
    }

}
