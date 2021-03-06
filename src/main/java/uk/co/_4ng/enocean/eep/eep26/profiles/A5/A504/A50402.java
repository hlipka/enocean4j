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
package uk.co._4ng.enocean.eep.eep26.profiles.A5.A504;

import uk.co._4ng.enocean.eep.eep26.attributes.EEP26HumidityLinear;
import uk.co._4ng.enocean.eep.eep26.attributes.EEP26TemperatureLinear;

/**
 * @author bonino
 */
public class A50402 extends A504 {
    // the used channel
    public static final int CHANNEL = 0;

    /**
     *
     */
    public A50402() {

        // add attributes A50204 has operative range between 0.0 and 40 Celsius
        addChannelAttribute(CHANNEL, new EEP26TemperatureLinear(250, -20.0, 60.0));

        // and between 0 and 100% humidity
        addChannelAttribute(CHANNEL, new EEP26HumidityLinear(250, 0.0, 100.0));
    }
}
