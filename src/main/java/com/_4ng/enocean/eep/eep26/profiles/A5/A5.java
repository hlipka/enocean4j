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
package com._4ng.enocean.eep.eep26.profiles.A5;

import com._4ng.enocean.eep.EEP;
import com._4ng.enocean.eep.Rorg;

/**
 * Top level EEP
 */
public abstract class A5 extends EEP {
    public static final Rorg RORG = new Rorg((byte) 0xa5);

    public A5(String version) {
        super(version);
    }
}