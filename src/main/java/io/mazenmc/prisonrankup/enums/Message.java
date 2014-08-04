package io.mazenmc.prisonrankup.enums;

import io.mazenmc.prisonrankup.utils.LangUtil;

import static io.mazenmc.prisonrankup.enums.PrisonRankupConfig.CONFIG;

public enum Message {

    NO_PERMISSION("&4You do not have permission to run this command!"),
    RANKS_FORMAT("ranks-format", "&5[&3%rank%&5]&6: %price%"),
    NOT_ENOUGH_MONEY("not-enough-money", "&6You need &2$%price%&6 to rankup to &2%rank%"),
    RANKUP("Rankup BC Message", "&3%player% &6has ranked up to &3%rank%"),
    PREFIX("prefix", "&a[&bPrison&6-&bRankup&a]");

    private String message;

    private Message(String message) {
        this.message = LangUtil.toColor(message);
    }

    private Message(String path, String def) {
        setup(path, def);
    }

    private void setup(String path, String def) {
        if(!CONFIG.contains(path)) {
            message = LangUtil.toColor(def);
            return;
        }

        message = LangUtil.toColor(CONFIG.getString(path));
    }

    public static void refresh() {
        for(Message msg : values()) {
            switch(msg) {
                case RANKS_FORMAT:
                    msg.setup("ranks-format", "&5[&3%rank%&5]&6: %price%");
                    break;
                case NOT_ENOUGH_MONEY:
                    msg.setup("not-enough-money", "&6You need &2$%price%&6 to rankup to &2%rank%");
                    break;
                case PREFIX:
                    msg.setup("prefix", "&a[&bPrison&6-&bRankup&a]");
                    break;
                case RANKUP:
                    msg.setup("Rankup BC Message", "&3%player% &6has ranked up to &3%rank%");
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return message;
    }
}
