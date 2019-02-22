package com.wordparser.demo.component;

public enum FileContentType {

    TYPE_TXT ("text/plain");

    private final String name;

    FileContentType(String s){
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
