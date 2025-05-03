package Entities.States;

public enum OrderState {
    UNSTARTED("Nezačatá"),
    IN_PROGRESS("Prebieha spracovanie"),
    COMPLETED("Dokončená");
    private final String displayName;
    OrderState(String string) {
       displayName = string;
    }
    @Override
    public String toString() {
        return displayName;
    }
}
