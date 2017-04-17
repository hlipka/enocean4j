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
package com._4ng.enocean.eep.eep26.profiles.D2.D201;

/**
 * An hexadecimal valued enumeration representing unit of measures supported by the EEP26, D201 profile
 *
 * @author bonino
 */
public enum D201UnitOfMeasure {
    Ws((byte) 0x00), Wh((byte) 0x01), kWh((byte) 0x02), W((byte) 0x03), kW((byte) 0x04);

    private final byte code;

    private D201UnitOfMeasure(byte modCode) {
        code = modCode;
    }

    public static D201UnitOfMeasure valueOf(byte value) {
        D201UnitOfMeasure unit = null;
        for (D201UnitOfMeasure currentUnit : D201UnitOfMeasure.values()) {
            if (currentUnit.getCode() == value) {
                unit = currentUnit;
                break;
            }
        }

        if (unit == null) {
            throw new IllegalArgumentException();
        }

        return unit;
    }

    /**
     * Returns the hexadecimal code associated to this enumeration instance
     *
     * @return the hexadecimal code as a byte.
     */
    public byte getCode() {
        return code;
    }

    public boolean isEnergy() {
        return code == (byte) 0x00 || code == (byte) 0x01 || code == (byte) 0x02;
    }

    public boolean isPower() {
        return code == (byte) 0x03 || code == (byte) 0x04;
    }
}
