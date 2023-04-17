package com.example.monad1;

import java.util.function.Function;

public class Monad<T> {
    private final T value;

    public Monad(T value) {
        this.value = value;
    }
    public <U> Monad<U> map(Function<T, U> func) {
        return new Monad(func.apply(this.value));
    }

    public static <U> Monad<U> unwrap(Monad<Monad<U>> nested) {
        return nested.value;
    }
}