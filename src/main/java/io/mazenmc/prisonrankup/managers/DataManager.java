package io.mazenmc.prisonrankup.managers;

import io.mazenmc.prisonrankup.enums.PrisonRankupConfig;
import io.mazenmc.prisonrankup.objects.PRPlayer;

import java.util.ArrayList;
import java.util.List;

public class DataManager extends Manager{

    private static DataManager instance = new DataManager();

    private PrisonRankupConfig dataConfig = PrisonRankupConfig.DATA;
    private List<PRPlayer> players = new ArrayList<>();

    private DataManager() {
        //
    }

    public void update() {
        //
    }

    public static DataManager getInstance() {
        return instance;
    }
}
