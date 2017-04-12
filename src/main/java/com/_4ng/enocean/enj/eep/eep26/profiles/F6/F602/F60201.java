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
package com._4ng.enocean.enj.eep.eep26.profiles.F6.F602;

import com._4ng.enocean.enj.eep.EEPAttributeChangeDispatcher;
import com._4ng.enocean.enj.eep.EEPIdentifier;
import com._4ng.enocean.enj.eep.eep26.attributes.EEP26RockerSwitch2RockerAction;
import com._4ng.enocean.enj.eep.eep26.attributes.EEP26RockerSwitch2RockerButtonCount;
import com._4ng.enocean.enj.eep.eep26.attributes.EEP26RockerSwitch2RockerEnergyBow;
import com._4ng.enocean.enj.eep.eep26.telegram.EEP26Telegram;
import com._4ng.enocean.enj.eep.eep26.telegram.EEP26TelegramType;
import com._4ng.enocean.enj.eep.eep26.telegram.RPSTelegram;
import com._4ng.enocean.enj.model.EnOceanDevice;

/**
 * @author <a href="mailto:dario.bonino@gmail.com">Dario Bonino</a>
 */
public class F60201 extends F602 {

    // the type definition
    public static final byte type = (byte) 0x01;

    public static final int CHANNEL_1 = 0;
    public static final int CHANNEL_2 = 1;

    /**
     *
     */
    public F60201() {

        // add attributes
        addChannelAttribute(CHANNEL_1, new EEP26RockerSwitch2RockerAction());
        addChannelAttribute(CHANNEL_2, new EEP26RockerSwitch2RockerAction());
        addChannelAttribute(CHANNEL_1, new EEP26RockerSwitch2RockerButtonCount());
        addChannelAttribute(CHANNEL_1, new EEP26RockerSwitch2RockerEnergyBow());
    }

    @Override
    public EEPIdentifier getEEPIdentifier() {
        return new EEPIdentifier(F602.rorg, F602.func, type);
    }

    @Override
    public boolean handleProfileUpdate(EEP26Telegram telegram, EnOceanDevice device) {
        boolean success = false;
        // handle the telegram, as first cast it at the right type (or fail)
        if (telegram.getTelegramType() == EEP26TelegramType.RPS) {
            // cast the telegram to handle to its real type
            RPSTelegram profileUpdate = (RPSTelegram) telegram;

            // get the packet payload
            byte[] payload = profileUpdate.getPayload();

            // parse the F602 data payload
            F6020102RockerSwitchMessage message = new F6020102RockerSwitchMessage(payload, profileUpdate.getStatus());

            if (message.isValid()) {
                if (message.isActionMessage()) {
                    // update the rocker switch attribute
                    // shortcut used here: channel0 = action1, channel1 =
                    // action2
                    EEP26RockerSwitch2RockerAction rockerSwitchAttribute1 = (EEP26RockerSwitch2RockerAction) getChannelAttribute(CHANNEL_1, EEP26RockerSwitch2RockerAction.NAME);
                    EEP26RockerSwitch2RockerAction rockerSwitchAttribute2 = (EEP26RockerSwitch2RockerAction) getChannelAttribute(CHANNEL_2, EEP26RockerSwitch2RockerAction.NAME);

                    // if not null update in any case
                    if (rockerSwitchAttribute1 != null) {
                        boolean[] actions = message.getButtonActions1();
                        rockerSwitchAttribute1.setButtonValue(EEP26RockerSwitch2RockerAction.AO, actions[EEP26RockerSwitch2RockerAction.AO]);
                        rockerSwitchAttribute1.setButtonValue(EEP26RockerSwitch2RockerAction.AI, actions[EEP26RockerSwitch2RockerAction.AI]);
                        rockerSwitchAttribute1.setButtonValue(EEP26RockerSwitch2RockerAction.BO, actions[EEP26RockerSwitch2RockerAction.BO]);
                        rockerSwitchAttribute1.setButtonValue(EEP26RockerSwitch2RockerAction.BI, actions[EEP26RockerSwitch2RockerAction.BI]);

                        // build the dispatching task
                        EEPAttributeChangeDispatcher dispatcherTask = new EEPAttributeChangeDispatcher(rockerSwitchAttribute1, CHANNEL_1, telegram, device);

                        // submit the task for execution
                        attributeNotificationWorker.submit(dispatcherTask);
                    }

                    // if action2 is enabled
                    if (message.isAction2Enabled() && rockerSwitchAttribute2 != null) {
                        boolean[] actions = message.getButtonActions1();
                        rockerSwitchAttribute2.setButtonValue(EEP26RockerSwitch2RockerAction.AO, actions[EEP26RockerSwitch2RockerAction.AO]);
                        rockerSwitchAttribute2.setButtonValue(EEP26RockerSwitch2RockerAction.AI, actions[EEP26RockerSwitch2RockerAction.AI]);
                        rockerSwitchAttribute2.setButtonValue(EEP26RockerSwitch2RockerAction.BO, actions[EEP26RockerSwitch2RockerAction.BO]);
                        rockerSwitchAttribute2.setButtonValue(EEP26RockerSwitch2RockerAction.BI, actions[EEP26RockerSwitch2RockerAction.BI]);

                        // build the dispatching task
                        EEPAttributeChangeDispatcher dispatcherTask = new EEPAttributeChangeDispatcher(rockerSwitchAttribute2, CHANNEL_2, telegram, device);

                        // submit the task for execution
                        attributeNotificationWorker.submit(dispatcherTask);
                    }

                }
                else if (message.isActionMessage() == false) {
                    // get the number of buttons attribute
                    EEP26RockerSwitch2RockerButtonCount btnCountAttribute = (EEP26RockerSwitch2RockerButtonCount) getChannelAttribute(CHANNEL_1, EEP26RockerSwitch2RockerButtonCount.NAME);

                    // check not null
                    if (btnCountAttribute != null) {
                        // set the attribute value
                        btnCountAttribute.setValue(message.getnButtonsPressed());

                        // build the dispatching task
                        EEPAttributeChangeDispatcher dispatcherTask = new EEPAttributeChangeDispatcher(btnCountAttribute, CHANNEL_1, telegram, device);

                        // submit the task for execution
                        attributeNotificationWorker.submit(dispatcherTask);
                    }
                }

                // handle energy bow (common to all messages)
                EEP26RockerSwitch2RockerEnergyBow energyBowAttribute = (EEP26RockerSwitch2RockerEnergyBow) getChannelAttribute(CHANNEL_1, EEP26RockerSwitch2RockerEnergyBow.NAME);

                // check not null
                if (energyBowAttribute != null) {
                    // update the energy bow attribute
                    energyBowAttribute.setValue(message.isEnergyBowPressed());

                    // build the dispatching task
                    EEPAttributeChangeDispatcher dispatcherTask = new EEPAttributeChangeDispatcher(energyBowAttribute, CHANNEL_1, telegram, device);

                    // submit the task for execution
                    attributeNotificationWorker.submit(dispatcherTask);

                }

                //if comes here everything is fine
                success = true;
            }
        }
        return success;
    }
}