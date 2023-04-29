package com.example.enotebookvocabulary;


import java.util.Objects;

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

    // Override the equals() method to compare objects based on their name property
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(russianWord, word.russianWord);
    }

    // Override the hashCode() method to use the name property as the hash code
    @Override
    public int hashCode() {
        return Objects.hash(russianWord);
    }

    @Override
    public String toString() {
        return " RUSS: " + russianWord + " \n" +' ' +
                "ENG:  " + englishWord + " \n" +' ' +
                "GER:  " + deutschWord + " \n" +' ';
    }

}
