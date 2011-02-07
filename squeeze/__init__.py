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
        if subcommand == 'htmlcompressor':
            execute_jar_command(get_html_compressor_jar_filename())
        elif subcommand == "yuicompressor":
            execute_jar_command(get_yui_compressor_jar_filename())
        elif subcommand == "cssembed":
            execute_jar_command(get_cssembed_jar_filename())
        elif subcommand == "datauri":
            execute_jar_command(get_datauri_jar_filename())
        else:
            show_usage()
    else:
        show_usage()

def show_usage():
    sys.stdout.write('''
    Usage: squeeze [htmlcompressor|yuicompressor|cssembed|datauri] [options]
    ''')
    sys.exit(1)

if __name__ == "__main__":
    main()
