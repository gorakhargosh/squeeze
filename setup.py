from setuptools import setup, find_packages

setup(
  name="squeeze",
  description="Bundled HTML/JS/CSS compressor and data-uri embedding tools " \
              "(YUICompressor, CSSEmbed, DataURI, Google Closure Compiler, " \
              "HTMLCompressor, Google Closure Stylesheets)",
  long_description=open('README').read(),
  author='Yesudeep Mangalapilly',
  author_email='yesudeep@google.com',
  version="0.1.7",
  url="http://pypi.python.org/pypi/squeeze",
  license='MIT License',
  packages=find_packages(),
  install_requires=[],
  entry_points={
    'console_scripts': [
      "squeeze = squeeze:main"
    ]
  },
  package_data={
    '': ["*.jar"]
  },
  include_package_data=True,
  zip_safe=False,
  classifiers=[
    'Development Status :: 5 - Production/Stable',
    'Environment :: Web Environment',
    'License :: OSI Approved :: MIT License',
    'Topic :: Internet :: WWW/HTTP :: Site Management',
    ]
)
