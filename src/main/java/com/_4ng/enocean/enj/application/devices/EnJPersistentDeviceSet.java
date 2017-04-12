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
package com._4ng.enocean.enj.application.devices;

import com._4ng.enocean.enj.model.EnOceanDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * Set of EnOcean devices, backed on an {@link Hashtable}. It provides
 * additional fast access to entries by high-level and low-level ids and
 * supports persistence on file.
 * <p>
 * TODO: improve persistence with a more flexible / extensible approach
 *
 * @author <a href="mailto:dario.bonino@gmail.com">Dario Bonino</a>
 * @author <a href="mailto:biasiandrea04@gmail.com">Andrea Biasi </a>
 */
public class EnJPersistentDeviceSet implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(EnJPersistentDeviceSet.class);

    /**
     * The serial version identifier for handling serialization /
     * deserialization.
     */
    private static final long serialVersionUID = 1L;
    // the backing hashtable
    private Hashtable<Integer, EnOceanDevice> theSet;

    // the auto-saving flag, should not be persisted
    private volatile boolean autoSave;
    private volatile String filename;

    /**
     *
     */
    public EnJPersistentDeviceSet() {

        // builds the backing hash table
        theSet = new Hashtable<>();

        // by default is disabled
        autoSave = false;
    }

    public EnJPersistentDeviceSet(String filename, boolean autoSave) {

        // builds the backing hash table
        theSet = new Hashtable<>();

        // set auto-save
        this.autoSave = autoSave;

        // set the filename
        this.filename = filename;

        // load persistent devices
        if (this.filename != null && !this.filename.isEmpty()) {
            load(filename);
        }

    }

    public static EnJPersistentDeviceSet fromFile(String filename) {
        // build an empty persistent set
        EnJPersistentDeviceSet set = new EnJPersistentDeviceSet();

        // store the set data
        set.load(filename);

        // return the set
        return set;
    }

    /**
     * Save this set on the file specified at creation time (or at loading
     * time).
     */
    public void save() {
        save(filename);
    }

    /**
     * Loads this set from the file specified at creation time.
     */
    public void load() {
        load(filename);
    }

    /**
     * Saves this set on a binary file to grant persistence
     */
    public void save(String filename) {
        if (this.filename != null && !this.filename.isEmpty()) {
            try {
                // create the file object representing the target file
                File persistentSet = new File(filename);
                File fileToWrite = persistentSet;
                boolean replace = false;
                // check if exists
                if (persistentSet.exists()) {
                    // write on a temporary file and then replace the existing
                    // one
                    fileToWrite = File.createTempFile("device_set", "tmp", persistentSet.getParentFile());
                    replace = true;
                }

                // build a File output stream on the file to write, be it a
                // temporary file or the final one.
                FileOutputStream fileOut = new FileOutputStream(fileToWrite);

                // build an object output stream to serialize this object on
                // file
                ObjectOutputStream outStream = new ObjectOutputStream(fileOut);

                // write this down
                outStream.writeObject(this);

                // flush and close streams
                outStream.flush();
                fileOut.flush();
                outStream.close();
                fileOut.close();

                // check if must be replaced
                if (replace && persistentSet.exists()) {
                    if (!persistentSet.delete()) {
                        logger.warn("Cannot delete file {}", persistentSet.getAbsolutePath());
                    }
                    if (!fileToWrite.renameTo(persistentSet)) {
                        logger.warn("Cannot move file {} to {}", fileToWrite.getAbsoluteFile(), persistentSet.getAbsolutePath());
                    }
                }

                // log debug
                logger.debug("Saved persisten device set");
            }
            catch (IOException i) {
                i.printStackTrace();
            }
        }
    }

    /**
     * loads data from a serialized file and fills this set
     */
    public void load(String filename) {
        if (this.filename != null && !this.filename.isEmpty()) {
            try {
                // prepare a File object representing the persistent set
                File persistentSet = new File(filename);

                // check if exists
                if (persistentSet.exists()) {

                    // Open a file input stream on the persistent set
                    FileInputStream fileIn = new FileInputStream(persistentSet);

                    // Open an object output stream to deserialize the objects
                    // in
                    // the
                    // file
                    ObjectInputStream in = new ObjectInputStream(fileIn);

                    // restores in this object
                    theSet.clear();

                    theSet.putAll(((EnJPersistentDeviceSet) in.readObject()).theSet);

                    // close the input streams
                    in.close();
                    fileIn.close();
                }
            }
            catch (ClassNotFoundException | IOException e) {
                // log the error
                logger.error("Unable to restore the device persistent set.");
            }
        }
    }

    /**
     * Gets a device given its high-level UID, equivalent to performing the get
     * method of the superclass.
     *
     * @param deviceUID The device high-level identifier
     * @return The corresponding {@link EnOceanDevice} instance.
     */
    public EnOceanDevice getByUID(int deviceUID) {
        // return the device, if present
        return theSet.get(deviceUID);
    }

    /**
     * Gets a device given its low-level byte address. Uses the EnOceanDevice
     * utilities for converting the low-level address into an high-level UID and
     * then calls the getByUID method.
     *
     * @param address The low-level address of the device
     * @return The corresponding {@link EnOceanDevice} instance
     */
    public EnOceanDevice getByLowAddress(byte[] address) {
        // build the high-level UID
        int uid = EnOceanDevice.byteAddressToUID(address);

        // get the corresponding EnOcean device
        return theSet.get(uid);
    }

    /**
     * Adds the given {@link EnOceanDevice} to the set of devices.
     *
     * @param device The {@link EnOceanDevice} instance to add.
     */
    public void add(EnOceanDevice device) {
        // add the given device
        theSet.put(device.getDeviceUID(), device);

        if (autoSave) {
            if (filename != null && !filename.isEmpty()) {
                save();
            }
        }
    }

    /**
     * Adds a complete set of {@link EnOceanDevice}s to this set.
     *
     * @param devices The set of devices to add.
     */
    public void addAll(Collection<? extends EnOceanDevice> devices) {
        // adds all devices
        for (EnOceanDevice currentDevice : devices) {
            theSet.put(currentDevice.getDeviceUID(), currentDevice);
        }

        if (autoSave) {
            if (filename != null && !filename.isEmpty()) {
                save();
            }
        }
    }

    public EnOceanDevice remove(EnOceanDevice device) {
        EnOceanDevice removed = theSet.remove(device.getDeviceUID());

        if (autoSave) {
            if (filename != null && !filename.isEmpty()) {
                save();
            }
        }

        return removed;
    }

    public EnOceanDevice remove(int uid) {
        EnOceanDevice removed = theSet.remove(uid);

        if (autoSave) {
            if (filename != null && !filename.isEmpty()) {
                save();
            }
        }

        return removed;
    }

    public Collection<EnOceanDevice> removeAll(Collection<EnOceanDevice> devices) {
        HashSet<EnOceanDevice> removedItems = new HashSet<>();

        for (EnOceanDevice device : devices) {
            removedItems.add(theSet.remove(device.getDeviceUID()));
        }

        if (autoSave) {
            if (filename != null && !filename.isEmpty()) {
                save();
            }
        }

        return removedItems;
    }

    public Collection<EnOceanDevice> removeAll(int deviceUIDs[]) {
        HashSet<EnOceanDevice> removedItems = new HashSet<>();

        for (int deviceUID : deviceUIDs) {
            removedItems.add(theSet.remove(deviceUID));
        }

        return removedItems;
    }

    /**
     * Provides a collection of all devices currently stored in the set.
     *
     * @return
     */
    public Collection<EnOceanDevice> listAll() {
        return theSet.values();
    }
}