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

/**
 * Contains all the default {@link InputType InputTypes}.
 */
public interface InputTypes {

    /**
     * A {@link Input} which receives input through button presses in an inventory.
     */
    InputType INVENTORY_BUTTON = new InputType("menuapi:inventory_button", "Inventory Button Input");
    /**
     * A {@link Input} which receives input through textual input in the chat.
     */
    InputType CHAT_TEXT = new InputType("menuapi:chat_text", "Chat Text Input");
    /**
     * A {@link Input} which receives input through button presses in the chat.
     */
    InputType CHAT_BUTTON = new InputType("menuapi:chat_button", "Chat Button Input");
    /**
     * A {@link Input} which receives input through textual input in an anvil.
     */
    InputType ANVIL_TEXT_PAGE = new InputType("menuapi:anvil_text_page", "Anvil Text Page Input");
    /**
     * A {@link Input} which does not receive input.
     */
    InputType EMPTY = new InputType("menuapi:empty", "Empty Input");
    /**
     * A {@link Input} whose type is unknown. This type can be used by plugins adding their own {@link Input}
     * implementation, though it is suggested that such {@link Input Inputs} have their own custom type.
     */
    InputType UNKNOWN = new InputType("menuapi:unknown", "Unknown Input");

}
