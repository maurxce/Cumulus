/*
 * Copyright (c) 2020-2021 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Cumulus
 */

package org.geysermc.cumulus.form.impl.simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.impl.SimpleFormResponseImpl;
import org.geysermc.cumulus.response.result.FormResponseResult;
import org.geysermc.cumulus.response.result.ValidFormResponseResult;
import org.geysermc.cumulus.util.FormCodec;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.cumulus.util.FormType;
import org.geysermc.cumulus.form.impl.FormImpl;

@Getter
public final class SimpleFormImpl extends FormImpl<SimpleForm, SimpleFormResponse>
    implements SimpleForm {

  private final String title;
  private final String content;
  private final List<ButtonComponent> buttons;

  public SimpleFormImpl(
      @NonNull String title,
      @NonNull String content,
      @NonNull List<ButtonComponent> buttons) {
    super(FormType.SIMPLE_FORM);

    this.title = Objects.requireNonNull(title, "title");
    this.content = Objects.requireNonNull(content, "content");
    this.buttons = Collections.unmodifiableList(buttons);
  }

  @Override
  protected @NonNull SimpleFormResponse resultToResponse(
      FormResponseResult<SimpleFormResponse> result) {

    if (result.isClosed()) {
      return SimpleFormResponseImpl.closed();
    }
    if (result.isInvalid()) {
      return SimpleFormResponseImpl.invalid();
    }
    return ((ValidFormResponseResult<SimpleFormResponse>) result).response();
  }

  public static final class Builder
      extends FormImpl.Builder<SimpleForm.Builder, SimpleForm, SimpleFormResponse>
      implements SimpleForm.Builder {

    private final List<ButtonComponent> buttons = new ArrayList<>();
    private String content = "";

    @NonNull
    public Builder content(@NonNull String content) {
      this.content = translate(Objects.requireNonNull(content, "content"));
      return this;
    }

    @NonNull
    public Builder button(
        @NonNull String text,
        FormImage.@NonNull Type type,
        @NonNull String data) {
      buttons.add(ButtonComponent.of(translate(text), type, data));
      return this;
    }

    @NonNull
    public Builder button(@NonNull String text, @Nullable FormImage image) {
      buttons.add(ButtonComponent.of(translate(text), image));
      return this;
    }

    @NonNull
    public Builder button(@NonNull String text) {
      buttons.add(ButtonComponent.of(translate(text)));
      return this;
    }

    @Override
    @NonNull
    public SimpleForm build() {
      SimpleFormImpl form = new SimpleFormImpl(title, content, buttons);
      setResponseHandler(form);
      return form;
    }
  }
}