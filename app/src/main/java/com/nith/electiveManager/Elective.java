package com.nith.electiveManager;

public class Elective {
    private String elective, code;
    private boolean selected;

    public Elective (String elective, String code, boolean selected) {
        this.elective = elective;
        this.code = code;
        this.selected = selected;
    }

    public String getElective() {
        return elective;
    }

    public String getCode() {
        return code;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
