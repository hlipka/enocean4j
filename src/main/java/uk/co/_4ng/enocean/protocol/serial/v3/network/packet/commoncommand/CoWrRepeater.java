/*
 * Copyright $DateInfo.year enocean4j development teams
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
 * Write repeater level OFF, 1, 2;
 *
 * @author Andrea Biasi <biasiandrea04@gmail.com>
 */
public class CoWrRepeater extends ESP3Packet {

    /**
     * @param repEnable : OFF=0 ON=1
     * @param repLevel  : When repeater OFF must be 0, when ON then 1 for Level-1 , 2 for
     *                  Level-2
     */
    public CoWrRepeater(byte repEnable, byte repLevel) {
        packetType = COMMON_COMMAND;
        data[0] = 0x09;
        data[1] = repEnable;
        data[2] = repLevel;
        buildPacket();
    }
}