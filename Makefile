
PWD=`pwd`
ANT=ant
YUICOMPRESSOR_VERSION=2.4.4
HTMLCOMPRESSOR_VERSION=0.9.9
CSSEMBED_VERSION=0.3.3
DATAURI_VERSION=0.2.2
RM=rm -rf

.PHONY: all clean

all: squeeze/htmlcompressor.jar squeeze/cssembed.jar squeeze/datauri.jar squeeze/compiler.jar squeeze/yuicompressor.jar squeeze/yuicompressor-$(YUICOMPRESSOR_VERSION).jar

squeeze/compiler.jar: vendor/closure-compiler/build/compiler.jar
	cp $< $@

vendor/closure-compiler/build/compiler.jar:
	cd vendor/closure-compiler && ant && cd $(PWD)

squeeze/cssembed.jar: vendor/cssembed/build/cssembed-$(CSSEMBED_VERSION).jar
	cp $< $@

squeeze/datauri.jar: vendor/cssembed/build/datauri-$(DATAURI_VERSION).jar
	cp $< $@

vendor/cssembed/build/cssembed-$(CSSEMBED_VERSION).jar vendor/cssembed/build/datauri-$(DATAURI_VERSION): vendor/cssembed/ant.properties
	cd vendor/cssembed && ant && cd $(PWD)

squeeze/htmlcompressor.jar: vendor/htmlcompressor-$(HTMLCOMPRESSOR_VERSION).jar
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

