package com._4ng.enocean.protocol.serial.v3.network.packet.commoncommand;

import com._4ng.enocean.protocol.serial.v3.network.packet.ESP3Packet;

/**
 * Read x bytes of the flash, ram0, data, idata, xdata
 *
 * @author Andrea Biasi <biasiandrea04@gmail.com>
 */

public class CoRdMem extends ESP3Packet {
    /**
     * @param memoryType    : 	Flash 0x00
     *                      RAM 0 0x01
     *                      data RAM 0x02
     *                      idata RAM 0x03
     *                      xdata RAM 0x04
     * @param memoryAddress : Start address to read
     * @param dataLength    : Length to be read
     */
    public CoRdMem(byte memoryType, int memoryAddress, int dataLength) {
        packetType = COMMON_COMMAND;
        //Command code
        data[0] = 0x13;
        data[1] = memoryType;
        data[2] = (byte) (memoryAddress & 0xff);
        data[3] = (byte) ((memoryAddress & 0xff00) >> 8);
        data[4] = (byte) ((memoryAddress & 0xff0000) >> 16);
        data[5] = (byte) ((memoryAddress & 0xff000000) >> 24);
        data[6] = (byte) (dataLength & 0xff);
        data[7] = (byte) ((dataLength & 0xff00) >> 8);
        buildPacket();
    }
}