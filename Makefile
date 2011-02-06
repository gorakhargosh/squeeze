
PWD=`pwd`
ANT=ant
YUICOMPRESSOR_VERSION=2.4.4
HTMLCOMPRESSOR_VERSION=0.9.9
RM=rm -rf

.PHONY: all clean

all: squeeze/htmlcompressor.jar squeeze/yuicompressor.jar squeeze/yuicompressor-$(YUICOMPRESSOR_VERSION).jar

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
