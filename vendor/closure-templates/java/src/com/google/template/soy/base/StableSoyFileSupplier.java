/*
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.template.soy.base;

import com.google.common.io.InputSupplier;
import com.google.template.soy.internal.base.Pair;

import java.io.IOException;
import java.io.Reader;


/**
 * Record for one input Soy file. Contains an {@link InputSupplier} to supply a {@code Reader} for
 * the file content, and also the file path.
 *
 * <p> Important: Do not use outside of Soy code (treat as superpackage-private).
 *
 */
public class StableSoyFileSupplier implements SoyFileSupplier {


  /** Supplier of a Reader for the Soy file content. */
  private final InputSupplier<? extends Reader> contentSupplier;

  /** The path to the Soy file (used for messages only). */
  private final String path;


  /**
   * Creates a new {@code SoyFileSupplier} given an {@code InputSupplier} for the file content, as
   * well as the desired file path for messages.
   *
   * @param contentSupplier Supplier of a Reader for the Soy file content.
   * @param filePath The path to the Soy file (used for messages only).
   */
  public StableSoyFileSupplier(InputSupplier<? extends Reader> contentSupplier, String filePath) {
    this.contentSupplier = contentSupplier;
    this.path = filePath;
  }


  @Override
  public String getPath() {
    return path;
  }


  @Override
  public boolean hasChangedSince(Version version) {
    return !Version.STABLE_VERSION.equals(version);
  }


  @Override
  public Pair<Reader, Version> open() throws IOException {
    return Pair.of((Reader) contentSupplier.getInput(), Version.STABLE_VERSION);
  }

}
