package io.mazenmc.prisonrankup.exceptions;

public class PlayerNotOnlineException extends RuntimeException{

    public PlayerNotOnlineException(String name) {
        super("Player " + name + " is not online!");
    }
}
