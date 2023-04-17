package com.example.smell3;

public class CodeSmellSample {
    private double multiplyBy2(double n) {
        return n * 2;
    }

    private double divideBy2(double n) {
        return n / 2;
    }

    private double add3(double n) {
        return n + 3;
    }

    private double subtract1(double n) {
        return n - 1;
    }

    public double apply(double n) {
        // ((((n * 2) * 2) / 2) + 3) - 1
        return subtract1(add3(divideBy2(multiplyBy2(multiplyBy2(n)))));
    }

    public double apply2(double n) {
        // ((((n * 2) * 2) / 2) + 3) - 1
        double ans1 = multiplyBy2(n);
        double ans2 = multiplyBy2(ans1);
        double ans3 = divideBy2(ans2);
        double ans4 = add3(ans3);
        return subtract1(ans4);
    }
}
