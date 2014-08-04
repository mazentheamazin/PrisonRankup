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
