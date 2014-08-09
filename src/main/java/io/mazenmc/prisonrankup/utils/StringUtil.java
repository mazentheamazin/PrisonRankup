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

public final class StringUtil {

    /**
     * Builds a string that is safe to be used in a loop
     * @param objects Objects you wish to build into a string
     * @return Builded String
     */
    public static String buildString(Object... objects) {
        StringBuilder sb = new StringBuilder();

        for(Object o : objects) {
            sb.append(o);
        }

        return sb.toString();
    }
}
