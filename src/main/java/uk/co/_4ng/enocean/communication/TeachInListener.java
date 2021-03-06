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
package uk.co._4ng.enocean.communication;

import uk.co._4ng.enocean.devices.EnOceanDevice;

/**
 * @author bonino
 */
public interface TeachInListener {
    /**
     * Notifies when teach in is enabled
     */
    void teachInEnabled();

    /**
     * Notifies when teach-in is disabled
     */
    void teachInDisabled();

    /**
     * Called by the system when a new device has been detected during teach-in
     * @param device New device
     */
    void foundNewDevice(EnOceanDevice device);

    /**
     * Called by the system when an existing device has been detected during teach-in
     * @param device Registered device
     */
    void foundRegisteredDevice(EnOceanDevice device);
}
