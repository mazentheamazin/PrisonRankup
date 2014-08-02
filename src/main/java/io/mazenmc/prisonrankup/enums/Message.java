package io.mazenmc.prisonrankup.enums;

import io.mazenmc.prisonrankup.utils.LangUtil;

import static io.mazenmc.prisonrankup.enums.PrisonRankupConfig.CONFIG;

public enum Message {

    NO_PERMISSION("&4You do not have permission to run this command!"),
    RANKS_FORMAT(CONFIG.getString("ranks-format")),
    NOT_ENOUGH_MONEY(CONFIG.getString("not-enough-money")),
    RANKUP(CONFIG.getString("Rankup BC Message")),
    PREFIX(CONFIG.getString("prefix"));

    private String message;

    private Message(String message) {
        this.message = LangUtil.toColor(message);
    }

    public static void refresh() {
        for(Message msg : values()) {
            switch(msg) {
                case RANKS_FORMAT:
                    msg.message = LangUtil.toColor(CONFIG.getString("ranks-format"));
                    break;
                case NOT_ENOUGH_MONEY:
                    msg.message = LangUtil.toColor(CONFIG.getString("not-enough-money"));
                    break;
                case PREFIX:
                    msg.message = LangUtil.toColor(CONFIG.getString("prefix"));
                    break;
                case RANKUP:
                    msg.message = LangUtil.toColor(CONFIG.getString("Rankup BC Message"));
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return message;
    }
}
