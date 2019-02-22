package com.wordparser.demo.validator;

import java.util.function.Supplier;

public class ExpectChecked {
    public ExpectChecked() {
    }

    public static <E extends Exception> void isTrue(boolean expression, Supplier<E> excSupplier) throws E {
        if (!expression) {
            throw (E) excSupplier.get();
        }
    }
}