package com.game;

import java.util.HashMap;

/**
 * Utility class for dealing with tile and entity properties
 */
public class PropertiesList {

    public static HashMap<String, String> generatePropertiesMap(String[] properties, String[] values) {
        HashMap<String, String> output = new HashMap<>();

        for (int i = 0; i < properties.length; i++) {
            output.put(properties[i], values[i]);
        }

        return output;
    }
}
