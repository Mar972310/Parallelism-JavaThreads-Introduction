package edu.eci.arsw.threads;

public class PiThread extends Thread {
    
    private static int DigitsPerSum = 8;
    private static double Epsilon = 1e-17;

    private byte[] digits;
    private int start;
    private int count;

    public PiThread(int start, int end) {
        this.start = start;
        this.count = end - start;
        this.digits = new byte[this.count];
    }

    public void run() {
        piDigits();
    }

    public byte[] piDigits() {
        if (start < 0 || count < 0) {
            throw new RuntimeException("Invalid Interval");
        }

        double sum = 0;
        int currentPosition = start;

        for (int i = 0; i < count; i++) {
            if (i % DigitsPerSum == 0) {
                sum = 4 * sum(1, currentPosition)
                        - 2 * sum(4, currentPosition)
                        - sum(5, currentPosition)
                        - sum(6, currentPosition);

                currentPosition += DigitsPerSum;
            }

            sum = 16 * (sum - Math.floor(sum));
            digits[i] = (byte) Math.floor(sum);
        }

        return digits;
    }

    public byte[] getDigits() {
        return digits;
    }

    private static double sum(int m, int n) {
        double sum = 0;
        int d = m;
        int power = n;

        while (true) {
            double term;

            if (power > 0) {
                term = (double) hexExponentModulo(power, d) / d;
            } else {
                term = Math.pow(16, power) / d;
                if (term < Epsilon) {
                    break;
                }
            }

            sum += term;
            power--;
            d += 8;
        }

        return sum;
    }

    private static int hexExponentModulo(int p, int m) {
        int power = 1;
        while (power * 2 <= p) {
            power *= 2;
        }

        int result = 1;

        while (power > 0) {
            if (p >= power) {
                result *= 16;
                result %= m;
                p -= power;
            }

            power /= 2;

            if (power > 0) {
                result *= result;
                result %= m;
            }
        }

        return result;
    }
}