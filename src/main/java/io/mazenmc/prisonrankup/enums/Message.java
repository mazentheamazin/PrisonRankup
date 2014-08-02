package io.mazenmc.prisonrankup.enums;

import io.mazenmc.prisonrankup.utils.LangUtil;

public enum Message {

    NO_PERMISSION("&4You do not have permission to run this command!"), RANKS_FORMAT(PrisonRankupConfig.CONFIG.getString("ranks-format"));

    private String message;

    private Message(String message) {
        this.message = LangUtil.toColor(message);
    }

    public static void refresh() {
        for(Message msg : values()) {
            switch(msg) {
                case RANKS_FORMAT:
                    msg.message = PrisonRankupConfig.CONFIG.getString("ranks-format");
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return message;
    }
}
