package com.github.mcnulty2.timesaver.data;

public enum EnumTranslations {
    TIMESHEET("Timesheet", "Stundenzettel", "Feuille de temps"),
    TOTAL("Total", "Gesamt", "Total"),
    DAILY_NOTES("Daily Notes", "Tagesnotizen", "Notes journalières"),
    CLOSE("Close", "Schließen", "Fermer");


    private String textEn;
    private String textDe;
    private String textFr;

    EnumTranslations(String textEn, String textDe, String textFr) {
        this.textEn = textEn;
        this.textDe = textDe;
        this.textFr = textFr;
    }

    public String getText(String language) {
        return switch(language) {
            case "en" -> textEn;
            case "de" -> textDe;
            case "fr" -> textFr;
            default -> textEn;
        };
    }
 }
