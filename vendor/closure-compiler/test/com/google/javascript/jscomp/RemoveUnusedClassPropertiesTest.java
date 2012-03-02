/*
 * Copyright 2011 The Closure Compiler Authors.
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

package com.google.javascript.jscomp;

/**
 * @author johnlenz@google.com (John Lenz)
 */
public class RemoveUnusedClassPropertiesTest extends CompilerTestCase {

  private static final String EXTERNS =
      "var window;\n" +
      "function alert(a) {}\n" +
      "var EXT = {};" +
      "EXT.ext;";

  public RemoveUnusedClassPropertiesTest() {
    super(EXTERNS);
  }

  @Override
  protected CompilerPass getProcessor(Compiler compiler) {
    return new RemoveUnusedClassProperties(compiler);
  }

  public void testSimple1() {
    // A property defined on "this" can be removed
    test("this.a = 2", "2");
    test("x = (this.a = 2)", "x = 2");
    testSame("this.a = 2; x = this.a;");
  }

  public void testSimple2() {
    // A property defined on "this" can be removed, even when defined
    // as part of an expression
    test("this.a = 2, f()", "2, f()");
    test("x = (this.a = 2, f())", "x = (2, f())");
    test("x = (f(), this.a = 2)", "x = (f(), 2)");
  }

  public void testSimple3() {
    // A property defined on an object other than "this" can not be removed.
    testSame("y.a = 2");
    // but doesn't prevent the removal of the definition on 'this'.
    test("y.a = 2; this.a = 2", "y.a = 2; 2");
    // Some use of the property "a" prevents the removal.
    testSame("y.a = 2; this.a = 1; alert(x.a)");
  }

  public void testObjLit() {
    // A property defined on an object other than "this" can not be removed.
    testSame("({a:2})");
    // but doesn't prevent the removal of the definition on 'this'.
    test("({a:0}); this.a = 1;", "({a:0});1");
    // Some use of the property "a" prevents the removal.
    testSame("x = ({a:0}); this.a = 1; alert(x.a)");
  }

  public void testExtern() {
    // A property defined in the externs is can not be removed.
    testSame("this.ext = 2");
  }

  public void testExport() {
    // An exported property can not be removed.
    testSame("this.ext = 2; window['export'] = this.ext;");
    testSame("function f() { this.ext = 2; } window['export'] = this.ext;");
  }


  public void testAssignOp1() {
    // Properties defined using a compound assignment can be removed if the
    // result of the assignment expression is not immediately used.
    test("this.x += 2", "2");
    testSame("x = (this.x += 2)");
    testSame("this.x += 2; x = this.x;");
    // But, of course, a later use prevents its removal.
    testSame("this.x += 2; x.x;");
  }

  public void testAssignOp2() {
    // Properties defined using a compound assignment can be removed if the
    // result of the assignment expression is not immediately used.
    test("this.a += 2, f()", "2, f()");
    test("x = (this.a += 2, f())", "x = (2, f())");
    testSame("x = (f(), this.a += 2)");
  }

  public void testInc1() {
    // Increments and Decrements are handled similiarly to compound assignments
    // but need a placeholder value when replaced.
    test("this.x++", "0");
    testSame("x = (this.x++)");
    testSame("this.x++; x = this.x;");

    test("--this.x", "0");
    testSame("x = (--this.x)");
    testSame("--this.x; x = this.x;");
  }

  public void testInc2() {
    // Increments and Decrements are handled similiarly to compound assignments
    // but need a placeholder value when replaced.
    test("this.a++, f()", "0, f()");
    test("x = (this.a++, f())", "x = (0, f())");
    testSame("x = (f(), this.a++)");

    test("--this.a, f()", "0, f()");
    test("x = (--this.a, f())", "x = (0, f())");
    testSame("x = (f(), --this.a)");
  }

  public void testJSCompiler_renameProperty() {
    // JSCompiler_renameProperty introduces a use of the property
    testSame("this.a = 2; x[JSCompiler_renameProperty('a')]");
    testSame("this.a = 2; JSCompiler_renameProperty('a')");
  }

  public void testForIn() {
    // This is the basic assumption that this pass makes:
    // it can remove properties even when the object is used in a FOR-IN loop
    test("this.y = 1;for (var a in x) { alert(x[a]) }",
         "1;for (var a in x) { alert(x[a]) }");
  }

  public void testObjectKeys() {
    // This is the basic assumption that this pass makes:
    // it can remove properties even when the object are referenced
    test("this.y = 1;alert(Object.keys(this))",
         "1;alert(Object.keys(this))");
  }
}
