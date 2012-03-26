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

package com.google.template.soy.basicdirectives;

import com.google.common.collect.ImmutableList;
import com.google.template.soy.jssrc.restricted.JsExpr;
import com.google.template.soy.shared.AbstractSoyPrintDirectiveTestCase;


/**
 * Unit tests for ChangeNewlineToBrDirective.
 *
 */
public class ChangeNewlineToBrDirectiveTest extends AbstractSoyPrintDirectiveTestCase {


  public void testApplyForTofu() {

    ChangeNewlineToBrDirective directive = new ChangeNewlineToBrDirective();
    assertTofuOutput("", "", directive);
    assertTofuOutput("a<br>b", "a\rb", directive);
    assertTofuOutput("a<br>b", "a\nb", directive);
    assertTofuOutput("a<br>b", "a\r\nb", directive);
    assertTofuOutput("abc<br>def<br>xyz", "abc\rdef\nxyz", directive);
  }


  public void testApplyForJsSrc() {

    ChangeNewlineToBrDirective directive = new ChangeNewlineToBrDirective();
    JsExpr dataRef = new JsExpr("opt_data.myKey", Integer.MAX_VALUE);
    assertEquals("soy.$$changeNewlineToBr(opt_data.myKey)",
                 directive.applyForJsSrc(dataRef, ImmutableList.<JsExpr>of()).getText());
  }

}
