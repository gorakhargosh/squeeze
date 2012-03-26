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

package com.google.template.soy.coredirectives;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.google.template.soy.shared.restricted.SoyPrintDirective;


/**
 * Guice module for basic Soy print directives.
 *
 */
public class CoreDirectivesModule extends AbstractModule {


  @Override public void configure() {

    Multibinder<SoyPrintDirective> soyDirectivesSetBinder =
        Multibinder.newSetBinder(binder(), SoyPrintDirective.class);
    soyDirectivesSetBinder.addBinding().to(NoAutoescapeDirective.class);
    soyDirectivesSetBinder.addBinding().to(IdDirective.class);
    soyDirectivesSetBinder.addBinding().to(EscapeHtmlDirective.class);
  }

}
