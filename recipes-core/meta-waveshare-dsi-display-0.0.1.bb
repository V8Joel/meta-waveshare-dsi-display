DESCRIPTION = "Waveshare DSI Display driver for Raspberry Pi 4B"
LICENSE = "CLOSED"

SRC_URI = "git://github.com/waveshare/Waveshare-DSI-LCD.git;protocol=https"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

do_install() {
    # Move driver files
    install -d ${D}/lib/modules/$(uname -r)/kernel/drivers
    install -d ${D}/boot/overlays
    install -m 0644 ${S}/Driver_package/*.ko ${D}/lib/modules/$(uname -r)/kernel/drivers/
    install -m 0644 ${S}/Driver_package/*.dtbo ${D}/boot/overlays/

    # Handle config.txt modifications
    # This is an example approach. Adapt as necessary for your specific needs.
    echo "ignore_lcd=1" >> ${D}/boot/config.txt
    echo "dtoverlay=vc4-kms-v3d" >> ${D}/boot/config.txt
    echo "dtparam=i2c_vc=on" >> ${D}/boot/config.txt
    echo "dtparam=i2c_arm=on" >> ${D}/boot/config.txt
    # Additional dtoverlay settings based on the screen type should be added here
    # You may need to dynamically generate these lines based on your configuration
}

FILES_${PN} += "/usr/bin/WS_xinchDSI.sh \
                /lib/modules/*/kernel/drivers/*.ko \
                /boot/overlays/*.dtbo"