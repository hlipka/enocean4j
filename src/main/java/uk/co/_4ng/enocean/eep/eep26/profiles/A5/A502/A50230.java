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
package uk.co._4ng.enocean.eep.eep26.profiles.A5.A502;

import uk.co._4ng.enocean.devices.DeviceManager;
import uk.co._4ng.enocean.devices.EnOceanDevice;
import uk.co._4ng.enocean.eep.eep26.attributes.EEP26TemperatureInverseLinear;
import uk.co._4ng.enocean.eep.eep26.telegram.EEP26Telegram;
import uk.co._4ng.enocean.eep.eep26.telegram.EEP26TelegramType;
import uk.co._4ng.enocean.eep.eep26.telegram.FourBSTelegram;

/**
 * @author bonino
 */
public class A50230 extends A502 {
    // the used channel
    public static final int CHANNEL = 0;

    /**
     */
    public A50230() {

        // add attributes A50230 has operative range between -40.0 and 62.3
        // Celsius
        addChannelAttribute(CHANNEL, new EEP26TemperatureInverseLinear(1023, -40.0, 62.3));
    }

    @Override
    public boolean handleProfileUpdate(DeviceManager deviceManager, EEP26Telegram telegram, EnOceanDevice device) {
        boolean success = false;

        // handle the telegram, as first cast it at the right type (or fail)
        if (telegram.getTelegramType() == EEP26TelegramType.FourBS) {

            // cast the telegram to handle to its real type
            FourBSTelegram profileUpdate = (FourBSTelegram) telegram;

            // get the packet payload
            byte[] payload = profileUpdate.getPayload();

            //wrap the payload as a temperature message
            A502TemperatureMessage msg = new A502ExtendedTemperatureMessage(payload);

            //update the value of the attribute
            success = fireAttributeEvent(deviceManager, getChannelAttribute(0, EEP26TemperatureInverseLinear.NAME), 0, telegram, device, msg.getTemperature());
        }
        return success;
    }


}
