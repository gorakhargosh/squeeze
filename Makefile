# GNU Makefile

PWD=`pwd`
ANT=ant
YUICOMPRESSOR_VERSION=2.4.8pre
HTMLCOMPRESSOR_VERSION=1.5.3-SNAPSHOT
CSSEMBED_VERSION=0.4.5
DATAURI_VERSION=0.2.2
RM=rm -rf

.PHONY: all clean release help push

all: squeeze/htmlcompressor.jar squeeze/cssembed.jar squeeze/datauri.jar squeeze/compiler.jar squeeze/yuicompressor.jar squeeze/yuicompressor-$(YUICOMPRESSOR_VERSION).jar squeeze/closure-stylesheets.jar squeeze/SoyToJsSrcCompiler.jar

help:
	@echo "Possible targets:"
	@echo "    test        - run testsuite"
	@echo "    clean       - clean up generated files"
	@echo "    release     - performs a release"
	@echo "    push        - 'git push' to all hosted repositories"

release: clean test upload-doc
	python setup.py sdist upload

push:
	@echo "Pushing repository to remote:google [code.google.com]"
	@git push google master
	@echo "Pushing repository to remote:github [github.com]"
	@git push github master
	@echo "Pushing repository to remote:origin"
	@git push origin master

# Google Closure compiler.
squeeze/compiler.jar: vendor/closure-compiler/build/compiler.jar
	cp $< $@

vendor/closure-compiler/build/compiler.jar:
	cd vendor/closure-compiler && ant && cd $(PWD)

# Google Closure Stylesheets
squeeze/closure-stylesheets.jar: vendor/closure-stylesheets/build/closure-stylesheets.jar
	cp $< $@

vendor/closure-stylesheets/build/closure-stylesheets.jar:
	cd vendor/closure-stylesheets && ant && cd $(PWD)

# Nick Zakas' CSS Embed and Data URI embedder.
squeeze/cssembed.jar: vendor/cssembed/build/cssembed-$(CSSEMBED_VERSION).jar
	cp $< $@

squeeze/datauri.jar: vendor/cssembed/build/datauri-$(DATAURI_VERSION).jar
	cp $< $@

vendor/cssembed/build/cssembed-$(CSSEMBED_VERSION).jar vendor/cssembed/build/datauri-$(DATAURI_VERSION): vendor/cssembed/ant.properties
	cd vendor/cssembed && ant && cd $(PWD)

# HTML Compressor
squeeze/htmlcompressor.jar: vendor/htmlcompressor/target/htmlcompressor-$(HTMLCOMPRESSOR_VERSION).jar
	cp $< $@

vendor/htmlcompressor/target/htmlcompressor-$(HTMLCOMPRESSOR_VERSION).jar:
	cd vendor/htmlcompressor && sh ./build.sh && cd $(PWD)

# YUI Compressor
squeeze/yuicompressor.jar: squeeze/yuicompressor-$(YUICOMPRESSOR_VERSION).jar
	cp $< $@

squeeze/yuicompressor-$(YUICOMPRESSOR_VERSION).jar: vendor/yuicompressor/build/yuicompressor-$(YUICOMPRESSOR_VERSION).jar
	cp $< $@

vendor/yuicompressor/build/yuicompressor-$(YUICOMPRESSOR_VERSION).jar: vendor/yuicompressor/ant.properties
	cd vendor/yuicompressor && ant && cd $(PWD)


# Google Closure (Soy) Templates
squeeze/SoyToJsSrcCompiler.jar: vendor/closure-templates/build/SoyToJsSrcCompiler.jar
	cp $< $@

vendor/closure-templates/build/SoyToJsSrcCompiler.jar:
	cd vendor/closure-templates && ant SoyToJsSrcCompiler && cd $(PWD)

vendor/closure-templates/build/soy.jar:
	cd vendor/closute-templates && ant jar && cd $(PWD)

Vendor/closure-templates/build/javascript/soyutils.js vendor/closure-templates/build/javascript/soyutils_usegoog.js:
	cd vendor/closure-templates && ant generated-soyutils && cd $(PWD)

vendor/closure-templates/build/SoyParseInfoGenerator.jar:
	cd vendor/closure-templates && ant SoyParseInfoGenerator && cd $(PWD)

vendor/closure-templates/build/SoyMsgExtractor.jar:
	cd vendor/closure-templates && ant SoyMsgExtractor && cd $(PWD)

# Clean up.
clean:
	$(RM) squeeze/*.jar
	cd vendor/yuicompressor && ant clean && cd $(PWD)
	cd vendor/cssembed && ant clean && cd $(PWD)
	cd vendor/closure-compiler && ant clean && cd $(PWD)
	cd vendor/closure-stylesheets && ant clean && cd $(PWD)
	cd vendor/closure-templates && ant clean && cd $(PWD)
