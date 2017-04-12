package com._4ng.enocean.protocol.serial.v3.network.packet.commoncommand;

import com._4ng.enocean.protocol.serial.v3.network.packet.ESP3Packet;

/**
 * Enable/Disable all supplied filters
 *
 * @author Andrea Biasi <biasiandrea04@gmail.com>
 */

public class CoWrFilterEnable extends ESP3Packet {
    /**
     * @param filterOnoff    : All filter disable = 0 (OFF)
     *                       All filter enable = 1 (ON)
     * @param filterOperator : OR composition of filters = 0
     *                       AND composition of filters = 1
     */
    public CoWrFilterEnable(byte filterOnoff, byte filterOperator) {
        packetType = COMMON_COMMAND;
        // Command code
        data[0] = 0x0E;
        data[1] = filterOnoff;
        data[2] = filterOperator;
        buildPacket();
    }
}