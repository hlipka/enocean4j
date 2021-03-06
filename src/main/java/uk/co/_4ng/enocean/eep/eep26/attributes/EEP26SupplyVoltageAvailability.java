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
package uk.co._4ng.enocean.eep.eep26.attributes;

import uk.co._4ng.enocean.eep.EEPAttribute;

/**
 * @author bonino
 */
public class EEP26SupplyVoltageAvailability extends EEPAttribute<Boolean> {

    // the EEPFunction name
    public static final String NAME = "SupplyVoltageAvailability";

    // the human readable values
    public static final boolean AVAILABLE = true;
    public static final boolean NOT_AVAILABLE = false;

    /**
     * Supply voltage availability
     */
    public EEP26SupplyVoltageAvailability() {
        super(NAME);

        // set the default value at not available
        value = NOT_AVAILABLE;
    }

    public EEP26SupplyVoltageAvailability(boolean value) {
        // call the superclass constructor
        super(NAME);

        // set the given value
        this.value = value;
    }

    @Override
    public byte[] byteValue() {
        // by default is disabled
        byte value = 0x00;

        // if value is true than the local control is enabled and the value
        // should be 0b1 == 0x01
        if (this.value == AVAILABLE) {
            value = 0x01;
        }

        return new byte[]{value};
    }

}
