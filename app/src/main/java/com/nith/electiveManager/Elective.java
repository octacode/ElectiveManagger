package com.nith.electiveManager;

public class Elective {
    private String elective, code;

    public Elective (String elective, String code) {
        this.elective = elective;
        this.code = code;
    }

    public String getElective() {
        return elective;
    }

    public String getCode() {
        return code;
    }
}
