package Entities.States;

public enum OrderState {
    UNSTARTED("Nezačaté"),
    IN_PROGRESS("Prebieha spracovanie"),
    COMPLETED("Dokončené");
    private final String displayName;
    OrderState(String string) {
       displayName = string;
    }
    @Override
    public String toString() {
        return displayName;
    }
}
