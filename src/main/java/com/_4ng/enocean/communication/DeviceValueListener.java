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
package com._4ng.enocean.communication;

import com._4ng.enocean.devices.EnOceanDevice;
import com._4ng.enocean.eep.EEPAttributeChangeJob;

/**
 * EnOcean for Java Device Listener interface, allows to attach and handle all
 * value events related to EnOcean Devices
 *
 * @author steve ohara
 */
public interface DeviceValueListener {
    /**
     * Called when any attribute on a {@link EnOceanDevice} has changed
     *
     * @param eepAttributeChangeJob Attribute change object
     */
    void deviceAttributeChange(EEPAttributeChangeJob eepAttributeChangeJob);
}