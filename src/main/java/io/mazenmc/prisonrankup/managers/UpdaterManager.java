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

package io.mazenmc.prisonrankup.managers;

import io.mazenmc.prisonrankup.PrisonRankupPlugin;
import io.mazenmc.prisonrankup.utils.Updater;

public class UpdaterManager extends Manager {

    private static UpdaterManager instance = new UpdaterManager();

    private boolean update = false;
    private String name = "";
    private Updater.ReleaseType type = null;
    private String version = "";
    private String link = "";
    private final int PROJECT_ID = 72819;

    private UpdaterManager() {
        Updater updater = new Updater(PrisonRankupPlugin.getInstance(), PROJECT_ID, PrisonRankupPlugin.getInstance().getFile0(), Updater.UpdateType.NO_DOWNLOAD, false);

        update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;
        name = updater.getLatestName();
        version = updater.getLatestGameVersion();
        type = updater.getLatestType();
        link = updater.getLatestFileLink();
    }

    public void update() {
        if(update) {
            Updater updater = new Updater(PrisonRankupPlugin.getInstance(), PROJECT_ID, PrisonRankupPlugin.getInstance().getFile0(), Updater.UpdateType.NO_VERSION_CHECK, true);
        }
    }

    public boolean isUpdateAvailable() {
        return update;
    }

    public static UpdaterManager getInstance() {
        return instance;
    }

    public String getName() {
        return name;
    }

    public Updater.ReleaseType getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public String getLink() {
        return link;
    }
}
