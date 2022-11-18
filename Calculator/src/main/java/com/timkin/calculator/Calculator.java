package com.timkin.calculator;

public enum Calculator {
    SUM {
        @Override
        double executeBinaryOperation(double a, double b) {
            return a + b;
        }
    },

    SUBTRACT {
        @Override
        double executeBinaryOperation(double a, double b) {
            return a - b;
        }
    },

    MULTIPLY {
        @Override
        double executeBinaryOperation(double a, double b) {
            return a * b;
        }
    },

    DIVIDE {
        @Override
        double executeBinaryOperation(double a, double b) {
            if (b == 0) return Double.NaN;

            return a / b;
        }
    };

    abstract double executeBinaryOperation(double a, double b);
}
