package com.example.enotebookvocabulary;


public class Word {
    private String russianWord;
    private String englishWord;
    private String deutschWord;

    public Word(String rusW, String engW, String deW) {
        this.russianWord = rusW;
        this.englishWord = engW;
        this.deutschWord = deW;
    }

    public String getRussianWord() {
        return russianWord;
    }

    public void setRussianWord(String russianWord) {
        this.russianWord = russianWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getDeutschWord() {
        return deutschWord;
    }

    public void setDeutschWord(String deutschWord) {
        this.deutschWord = deutschWord;
    }

    @Override
    public String toString() {
        return " RUSS: " + russianWord + " \n" +' ' +
                "ENG:  " + englishWord + " \n" +' ' +
                "GER:  " + deutschWord + " \n" +' ';
    }

}
