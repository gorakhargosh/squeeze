import sys
import os
from pkg_resources import resource_filename

def get_yui_compressor_jar_filename():
    """Return the full path to the YUI Compressor Java archive."""
    return resource_filename(__name__, "yuicompressor.jar")

def get_html_compressor_jar_filename():
    """Return the full path to the HTML Compressor Java archive."""
    return resource_filename(__name__, "htmlcompressor.jar")

def main():
    name = sys.argv[0]
    
    if len(sys.argv) > 2:
        subcommand = sys.argv[1]
        if subcommand == 'htmlcompressor':
            os.execlp("java", 
                name, 
                "-jar", 
                get_html_compressor_jar_filename(), 
                *sys.argv[2:]
            )
        elif subcommand == "yuicompressor":
            os.execlp("java", 
                name, 
                "-jar", 
                get_yui_compressor_jar_filename(), 
                *sys.argv[2:]
            )
        else:
            show_usage()
    else:
        show_usage()

def show_usage():
    sys.stdout.write('''
    Usage: squeeze [htmlcompressor|yuicompressor] [options]
    ''')
    sys.exit(1)

if __name__ == "__main__":
    main()
