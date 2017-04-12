/*
 * EnJ - EnOcean Java API
 * 
 * Copyright 2014 Andrea Biasi, Dario Bonino 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package com._4ng.enocean.enj.eep.eep26.D5.D500;


import com._4ng.enocean.enj.eep.EEPAttributeChangeDispatcher;
import com._4ng.enocean.enj.eep.EEPIdentifier;
import com._4ng.enocean.enj.eep.eep26.attributes.EEP26Switching;
import com._4ng.enocean.enj.eep.eep26.telegram.EEP26Telegram;
import com._4ng.enocean.enj.eep.eep26.telegram.EEP26TelegramType;
import com._4ng.enocean.enj.eep.eep26.telegram.OneBSTelegram;
import com._4ng.enocean.enj.model.EnOceanDevice;

/**
 * @author bonino
 */
public class D50001 extends D500 {

    // the type definition
    public static final byte type = (byte) 0x01;
    public static final int CHANNEL = 0;

    /**
     * @param version
     */
    public D50001() {

        // add attributes, basically a switching one
        addChannelAttribute(CHANNEL, new EEP26Switching(false));
    }

    /*
     * (non-Javadoc)
     *
     * @see com._4ng.enocean.enj.eep.EEP#getEEPIdentifier()
     */
    @Override
    public EEPIdentifier getEEPIdentifier() {
        return new EEPIdentifier(D500.rorg, D500.func, type);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com._4ng.enocean.enj.eep.EEP#handleProfileUpdate(it.polito.elite
     * .enocean.enj.eep.eep26.telegram.EEP26Telegram)
     */
    @Override
    public boolean handleProfileUpdate(EEP26Telegram telegram, EnOceanDevice device) {
        boolean success = false;

        // handle the telegram, as first cast it at the right type (or fail)
        if (telegram.getTelegramType() == EEP26TelegramType.OneBS) {
            // cast the telegram to handle to its real type
            OneBSTelegram profileUpdate = (OneBSTelegram) telegram;

            // get the packet payload
            byte[] payload = profileUpdate.getPayload();

            // build the contact / switch message
            D50001ContactSwitchMessage message = new D50001ContactSwitchMessage(payload);

            // check if it is valid
            if (message.isValid()) {
                // get the attribute
                EEP26Switching switchingAttribute = (EEP26Switching) getChannelAttribute(CHANNEL, EEP26Switching.NAME);

                // update the attribute value
                switchingAttribute.setValue(message.isContactClosed());

                // build the dispatching task
                EEPAttributeChangeDispatcher dispatcherTask = new EEPAttributeChangeDispatcher(switchingAttribute, CHANNEL, telegram, device);

                // submit the task for execution
                attributeNotificationWorker.submit(dispatcherTask);

                success = true;
            }
        }

        return success;
    }

}
