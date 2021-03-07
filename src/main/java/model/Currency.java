package model;

public enum  Currency {
    USD,
    RUB,
    EUR;

    private static final double USD_TO_RUB = 74.43;
    private static final double EUR_TO_RUB = 88.93;

    private static double currencyMultiplier(Currency currency) {
        switch (currency) {
            case EUR: return EUR_TO_RUB;
            case USD: return USD_TO_RUB;
            default: return 1;
        }
    }

    public double convertPriceToCurrency(double price, Currency currencyToConvert) {
        double fromMult = currencyMultiplier(this);
        double toMult = currencyMultiplier(currencyToConvert);
        return price * fromMult / toMult;
    }
}
