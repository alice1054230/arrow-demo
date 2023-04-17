package com.example.monad2;

import java.util.function.Function;

public class Monad<T> {
    private final T value;

    public Monad(T value) {
        this.value = value;
    }

    public <U> Monad<U> bind(Function<T, Monad<U>> func) {
        return func.apply(this.value);
    }
}