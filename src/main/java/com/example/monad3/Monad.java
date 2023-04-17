package com.example.monad3;

import java.util.function.Function;

public class Monad<T> {
    private final T value;
    public Monad(T value) {
        this.value = value;
    }
    public <U> Monad<U> bind(Function<T, Monad<U>> func) {
        return (this.value != null) ? func.apply(this.value) : Monad.none();
    }
    public static <U> Monad<U> none() {
        return new Monad<>(null);
    }
}