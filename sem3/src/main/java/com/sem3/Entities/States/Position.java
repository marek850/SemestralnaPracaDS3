package com.sem3.Entities.States;

public enum Position {
    STORAGE("Sklad"),
    ASSEMBLY_STATION("Montážne miesto");

    private final String displayName;

    Position(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
