package ba.unsa.etf.rma.rma20djokicmilica36;

public enum transactionType {
    REGULARPAYMENT(1),
    REGULARINCOME(2),
    PURCHASE(3),
    INDIVIDUALINCOME(4),
    INDIVIDUALPAYMENT(5);

    private final int id;

    transactionType(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
