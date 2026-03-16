package com.nastya.prototype;
import com.nastya.model.CurrencyRequest;
public class CurrencyRequestPrototype implements Prototype<CurrencyRequest> {
    private CurrencyRequest request; // поле у объекта прототип
    public CurrencyRequestPrototype(CurrencyRequest req) {
        this.request = req;
    }
    @Override
    public CurrencyRequest copy() { // должен вернуть объект CurrencyRequest
        return new CurrencyRequest( request.getBaseCurrencies(), request.getTarget() );
    }
}