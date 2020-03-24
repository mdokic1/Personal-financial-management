package ba.unsa.etf.rma.rma20djokicmilica36;

import java.util.ArrayList;

public class TransactionListInteractor implements ITransactionListInteractor {
    @Override
    public ArrayList<Transaction> get() {
        return TransactionsModel.transactions;
    }

}
