package com.getir.bookstoreapi.model;

public enum BookStockMethod {

    ADD {
        @Override
        public int performOperation(int value, int stock) {
            return value + stock;
        }
    }, SUBTRACT {

        @Override
        public int performOperation(int value, int stock) {
            return Math.max(0, stock - value);
        }
    };

    public abstract int performOperation(int value, int stock);
}
