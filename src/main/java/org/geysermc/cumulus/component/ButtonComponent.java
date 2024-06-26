/*
 * Copyright (c) 2020-2023 GeyserMC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Cumulus
 */
package org.geysermc.cumulus.component;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.component.impl.ButtonComponentImpl;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.util.FormImage;

import java.util.function.Consumer;

/**
 * Button component is a component that can only be used in SimpleForm. With this component you can
 * show a button with an optional image attached to it.
 */
public interface ButtonComponent {
  static @NonNull ButtonComponent of(
          @NonNull String text,
          @Nullable FormImage image,
          @Nullable Consumer<SimpleFormResponse> callback
  ) {
    return new ButtonComponentImpl(text, image, callback);
  }

  static @NonNull ButtonComponent of(@NonNull String text, @Nullable FormImage image) {
    return new ButtonComponentImpl(text, image, null);
  }

  static @NonNull ButtonComponent of(
          @NonNull String text,
          FormImage.@NonNull Type type,
          @NonNull String data,
          @Nullable Consumer<SimpleFormResponse> callback
  ) {
    return new ButtonComponentImpl(text, FormImage.of(type, data), callback);
  }

  static @NonNull ButtonComponent of(
      @NonNull String text, FormImage.@NonNull Type type, @NonNull String data) {
    return new ButtonComponentImpl(text, FormImage.of(type, data), null);
  }

  static @NonNull ButtonComponent of(@NonNull String text, @Nullable Consumer<SimpleFormResponse> callback) {
    return new ButtonComponentImpl(text, null, callback);
  }

  static @NonNull ButtonComponent of(@NonNull String text) {
    return new ButtonComponentImpl(text, null, null);
  }

  /**
   * Returns the text that will be shown in the button.
   *
   * @since 1.1
   */
  @NonNull String text();

  /**
   * Returns the image that will be shown next to the button.
   *
   * @since 1.1
   */
  @Nullable FormImage image();

  /**
   * Returns the consumer that is ran on button click.
   */
  @Nullable Consumer<SimpleFormResponse> callback();
}
