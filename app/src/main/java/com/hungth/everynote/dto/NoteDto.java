package com.hungth.everynote.dto;

/**
 * Created by Admin on 4/2/2018.
 */

public class NoteDto {
    private String title;
    private String content;
    private int colorBackground;
    private int colorText;
    private int id;

    public NoteDto(String title, String content, int colorBackground, int colorText, int id) {
        this.title = title;
        this.content = content;
        this.colorBackground = colorBackground;
        this.colorText = colorText;
        this.id = id;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(int colorBackground) {
        this.colorBackground = colorBackground;
    }

    public int getColorText() {
        return colorText;
    }

    public void setColorText(int colorText) {
        this.colorText = colorText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
