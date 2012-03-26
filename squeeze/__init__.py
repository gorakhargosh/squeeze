#!/usr/bin/env python
# -*- coding: utf-8 -*-


import sys
import os
from pkg_resources import resource_filename

def get_yui_compressor_jar_filename():
  """Return the full path to the YUI Compressor Java archive."""
  return resource_filename(__name__, "yuicompressor.jar")


def get_html_compressor_jar_filename():
  """Return the full path to the HTML Compressor Java archive."""
  return resource_filename(__name__, "htmlcompressor.jar")


def get_datauri_jar_filename():
  """Return the full path to the HTML Compressor Java archive."""
  return resource_filename(__name__, "datauri.jar")


def get_cssembed_jar_filename():
  """Return the full path to the HTML Compressor Java archive."""
  return resource_filename(__name__, "cssembed.jar")


def get_google_closure_compiler_jar_filename():
  """Return the full path to the HTML Compressor Java archive."""
  return resource_filename(__name__, "compiler.jar")


def get_google_closure_stylesheets_jar_filename():
  """Return the full path to the HTML Compressor Java archive."""
  return resource_filename(__name__, "closure-stylesheets.jar")

def get_soy_to_js_src_jar_filename():
  """Return the full path to the HTML Compressor Java archive."""
  return resource_filename(__name__, "SoyToJsSrcCompiler.jar")


def execute_jar_command(jar_file):
  name = sys.argv[0]
  os.execlp("java",
            name,
            "-jar",
            jar_file,
            *sys.argv[2:]
  )


def main():
  if len(sys.argv) > 2:
    subcommand = sys.argv[1]
    command_map = {
      'htmlcompressor': get_html_compressor_jar_filename(),
      'yuicompressor': get_yui_compressor_jar_filename(),
      'cssembed': get_cssembed_jar_filename(),
      'datauri': get_datauri_jar_filename(),
      'closure': get_google_closure_compiler_jar_filename(),
      'gss': get_google_closure_stylesheets_jar_filename(),
      'soytojs': get_soy_to_js_src_jar_filename(),
      }
    try:
      jar_name = command_map[subcommand]
      execute_jar_command(jar_name)
    except KeyError:
      show_usage()
  else:
    show_usage()


def show_usage():
  sys.stdout.write('''
    Usage: squeeze [htmlcompressor|yuicompressor|cssembed|datauri|closure|gss|soytojs] [options]
    ''')
  sys.exit(1)

if __name__ == "__main__":
  main()
