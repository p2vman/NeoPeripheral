package io.p2vman.neoperipheral.util;

public class Myth {
    public static int pow(int base, int exp) {
        int result = 1;
        while (exp > 0) {
            if ((exp & 1) == 1) result *= base;
            base *= base;
            exp >>= 1;
        }
        return result;
    }
}
