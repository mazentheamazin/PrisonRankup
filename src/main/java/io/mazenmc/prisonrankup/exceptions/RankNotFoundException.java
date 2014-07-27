package io.mazenmc.prisonrankup.exceptions;

import io.mazenmc.prisonrankup.objects.Rank;

public class RankNotFoundException extends IllegalArgumentException {

    public RankNotFoundException(Rank rank) {
        super(rank.getName() + " does not exist or has been configured incorrectly, please review the current configuration");
    }
}
