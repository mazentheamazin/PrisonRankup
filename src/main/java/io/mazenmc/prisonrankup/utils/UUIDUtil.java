/**
 PrisonRankup, the most feature-packed rankup plugin out there.
 Copyright (C) 2014 Mazen K.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
