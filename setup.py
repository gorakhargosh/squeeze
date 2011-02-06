from setuptools import setup, find_packages

setup(
    name="squeeze",
    description="HTML/JS/CSS Compressor packaged for Python",
    long_description=open('README').read(),
    author='Gora Khargosh',
    author_email='gora.khargosh@gmail.com',
    version="0.1.1",
    url="http://pypi.python.org/pypi/squeeze",
    license='Public Domain',
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
        'License :: OSI Approved :: BSD License',
        'Topic :: Internet :: WWW/HTTP :: Site Management',
    ]
)
