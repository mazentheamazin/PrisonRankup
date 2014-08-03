package io.mazenmc.prisonrankup.utils;

import java.util.UUID;

public final class UUIDUtil {

    /**
     * Removes all dashes from a UUID
     * @param id ID you wish to turn into a String
     * @return Modified UUID
     */
    public static String idToString(UUID id) {
        return id.toString().replaceAll("-", "");
    }

    /**
     * Turnes a string without dashes into a UUID Object
     * @param id String representation of UUID
     * @return UUID Object
     */
    public static UUID stringToID(String id) {
        return UUID.fromString(id.replaceAll(
                "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                "$1-$2-$3-$4-$5"));
    }
}
