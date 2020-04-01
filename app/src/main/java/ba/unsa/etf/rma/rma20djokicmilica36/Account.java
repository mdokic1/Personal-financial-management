package ba.unsa.etf.rma.rma20djokicmilica36;

public class Account {
    private double budget;
    private int totalLimit;
    private int monthLimit;

    public Account(double budget, int totalLimit, int monthLimit){
        this.budget = budget;
        this.totalLimit = totalLimit;
        this.monthLimit = monthLimit;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(int totalLimit) {
        this.totalLimit = totalLimit;
    }

    public int getMonthLimit() {
        return monthLimit;
    }

    public void setMonthLimit(int monthLimit) {
        this.monthLimit = monthLimit;
    }
}
