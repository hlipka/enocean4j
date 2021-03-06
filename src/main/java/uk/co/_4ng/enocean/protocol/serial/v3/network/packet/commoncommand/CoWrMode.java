/*
 * Copyright 2017 enocean4j development teams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co._4ng.enocean.protocol.serial.v3.network.packet.commoncommand;

import uk.co._4ng.enocean.protocol.serial.v3.network.packet.ESP3Packet;

/**
 * Sets the gateway transceiver mode.
 * <p>
 * There are two modes available:
 * - Compatible mode - ERP1 - gateway uses Packet Type 1 to transmit and receive radio telegrams � for ASK products
 * - Advanced mode � ERP2 - gateway uses Packet Type 10 to transmit and receive radio telegrams � for FSK products with advanced protocol
 *
 * @author Andrea Biasi <biasiandrea04@gmail.com>
 */

public class CoWrMode extends ESP3Packet {
    /**
     * @param mode : 0x00 � Compatible mode (default) - ERP1
     *             0x01 � Advanced mode - ERP2
     */
    public CoWrMode(byte mode) {
        packetType = COMMON_COMMAND;
        data[0] = 0x1C;
        data[1] = mode;
        buildPacket();
    }
}