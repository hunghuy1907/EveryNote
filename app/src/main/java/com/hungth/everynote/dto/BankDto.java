package com.hungth.everynote.dto;

public class BankDto {
    private String position;
    private String nameBank;
    private String bankAcount;
    private String nameAcount;
    private String noteBank;

    public BankDto(String position, String nameBank, String bankAcount, String nameAcount, String noteBank) {
        this.position = position;
        this.nameBank = nameBank;
        this.bankAcount = bankAcount;
        this.nameAcount = nameAcount;
        this.noteBank = noteBank;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNameBank() {
        return nameBank;
    }

    public void setNameBank(String nameBank) {
        this.nameBank = nameBank;
    }

    public String getBankAcount() {
        return bankAcount;
    }

    public void setBankAcount(String bankAcount) {
        this.bankAcount = bankAcount;
    }

    public String getNameAcount() {
        return nameAcount;
    }

    public void setNameAcount(String nameAcount) {
        this.nameAcount = nameAcount;
    }

    public String getNoteBank() {
        return noteBank;
    }

    public void setNoteBank(String noteBank) {
        this.noteBank = noteBank;
    }
}
