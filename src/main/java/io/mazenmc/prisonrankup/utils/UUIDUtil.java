package io.mazenmc.prisonrankup.utils;

import java.util.UUID;

public final class UUIDUtil {

    public static String idToString(UUID id) {
        return id.toString().replaceAll("-", "");
    }

    public static UUID stringToID(String id) {
        return UUID.fromString(id.replaceAll(
                "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                "$1-$2-$3-$4-$5"));
    }
}
