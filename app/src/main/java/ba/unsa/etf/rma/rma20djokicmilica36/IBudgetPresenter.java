package ba.unsa.etf.rma.rma20djokicmilica36;

import android.os.Parcelable;

public interface IBudgetPresenter {
    void create(double budget, int total, int month);

    void setAccount(Parcelable account);

    Account getAccount();

    void ChangeAccount(Account stari, Account novi);
}
