
PWD=`pwd`
ANT=ant
YUICOMPRESSOR_VERSION=2.4.8pre
HTMLCOMPRESSOR_VERSION=1.5.3-SNAPSHOT
CSSEMBED_VERSION=0.4.5
DATAURI_VERSION=0.2.2
RM=rm -rf

.PHONY: all clean

all: squeeze/htmlcompressor.jar squeeze/cssembed.jar squeeze/datauri.jar squeeze/compiler.jar squeeze/yuicompressor.jar squeeze/yuicompressor-$(YUICOMPRESSOR_VERSION).jar squeeze/closure-stylesheets.jar

squeeze/compiler.jar: vendor/closure-compiler/build/compiler.jar
	cp $< $@

squeeze/closure-stylesheets.jar: vendor/closure-stylesheets/build/closure-stylesheets.jar
	cp $< $@

vendor/closure-stylesheets/build/closure-stylesheets.jar:
	cd vendor/closure-stylesheets && ant && cd $(PWD)

vendor/closure-compiler/build/compiler.jar:
	cd vendor/closure-compiler && ant && cd $(PWD)

squeeze/cssembed.jar: vendor/cssembed/build/cssembed-$(CSSEMBED_VERSION).jar
	cp $< $@

squeeze/datauri.jar: vendor/cssembed/build/datauri-$(DATAURI_VERSION).jar
	cp $< $@

vendor/cssembed/build/cssembed-$(CSSEMBED_VERSION).jar vendor/cssembed/build/datauri-$(DATAURI_VERSION): vendor/cssembed/ant.properties
	cd vendor/cssembed && ant && cd $(PWD)

squeeze/htmlcompressor.jar: vendor/htmlcompressor/target/htmlcompressor-$(HTMLCOMPRESSOR_VERSION).jar
	cp $< $@

squeeze/yuicompressor.jar: squeeze/yuicompressor-$(YUICOMPRESSOR_VERSION).jar
	cp $< $@

squeeze/yuicompressor-$(YUICOMPRESSOR_VERSION).jar: vendor/yuicompressor/build/yuicompressor-$(YUICOMPRESSOR_VERSION).jar
	cp $< $@

vendor/yuicompressor/build/yuicompressor-$(YUICOMPRESSOR_VERSION).jar: vendor/yuicompressor/ant.properties
	cd vendor/yuicompressor && ant && cd $(PWD)

clean:
	$(RM) squeeze/*.jar
	cd vendor/yuicompressor && ant clean && cd $(PWD)
	cd vendor/cssembed && ant clean && cd $(PWD)
	cd vendor/closure-compiler && ant clean && cd $(PWD)
	cd vendor/closure-stylesheets && ant clean && cd $(PWD)
