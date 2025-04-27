package Entities.States;

public enum Process {
    NONE("Žiadny"),
    CUTTING("Rezanie"),
    VARNISHING("Morenie"),
    STAINING("Lakovanie"),
    ASSEMBLING("Skladanie"),
    FITTING("Montáž kovania");

    private final String displayName;

    Process(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
