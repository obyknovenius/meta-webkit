require wpewebkit.inc

SRC_URI = "git://github.com/WebKit/WebKit.git;protocol=https;branch=main"
SRCREV = "846b4dcaed697bf586d0a250ff23e658b6081777"

S = "${WORKDIR}/git"

PREFIX = "/opt/${PN}"

EXTRA_OECMAKE:append = " -DCMAKE_INSTALL_PREFIX:PATH=${PREFIX}"

# Since 2.44+. The ENABLE_ACCESSIBILITY build option has been removed.
# A new USE_ATK option may be used to disable accessibility.
PACKAGECONFIG[accessibility] = "-DUSE_ATK=ON,-DUSE_ATK=OFF,atk at-spi2-atk"

# libbacktrace. Since 2.44+
PACKAGECONFIG[libbacktrace] = "-DUSE_LIBBACKTRACE=ON,-DUSE_LIBBACKTRACE=OFF,libbacktrace"
PACKAGECONFIG:append = " libbacktrace"

# Skia. Since 2.46+
PACKAGECONFIG[skia] = "-DUSE_SKIA=ON,-DUSE_SKIA=OFF"
PACKAGECONFIG:append = " skia"

PACKAGECONFIG:remove = "remote-inspector"

DEPENDS += " libinput libtasn1 wayland-native"

FILES:${PN} = "\
    ${PREFIX}/bin \
    ${PREFIX}/libexec \
    ${PREFIX}/lib/lib*${SOLIBS} \
    ${PREFIX}/lib/wpe-webkit*/injected-bundle/libWPEInjectedBundle.so"

FILES_SOLIBSDEV = "${PREFIX}/lib/lib*${SOLIBSDEV}"
FILES:${PN}-dev = "\
    ${PREFIX}/include \
    ${FILES_SOLIBSDEV} \
    ${PREFIX}/lib/pkgconfig"

FILES:${PN}-web-inspector-plugin = "${PREFIX}/lib/wpe-webkit-*/libWPEWebInspectorResources.so"
