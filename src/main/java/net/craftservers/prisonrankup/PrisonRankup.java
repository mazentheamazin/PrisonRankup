package net.craftservers.prisonrankup;

import net.craftservers.prisonrankup.Models.PRPlayer;
import net.craftservers.prisonrankup.Models.Rank;
import org.bukkit.entity.Player;

@Deprecated
public class PrisonRankup {

    @Deprecated
    public static Rank getNextRank(String playerName) {
        return new PRPlayer(playerName).getNextRank();
    }

    @Deprecated
    public static Rank getNextRank(Player player) {
        return getNextRank(player.getName());
    }

    @Deprecated
    public static void rankup(Player player) {
        player.performCommand("rankup");
    }

    @Deprecated
    public static Rank getRank(String name) {
        return new Rank(name);
    }

    @Deprecated
    public static PRPlayer getPlayer(String name) {
        return new PRPlayer(name);
    }
}
