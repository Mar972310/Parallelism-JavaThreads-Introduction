package edu.eci.arsw.math;

import java.util.ArrayList;
import edu.eci.arsw.threads.PiThread;

public class PiDigits {
    
    private static ArrayList<PiThread> threads = new ArrayList<>();
    private static byte[] digits;

    public static byte[] getDigits(int start, int count, int threadNumber) {
        
        digits = new byte[count];
        threads.clear();
        
        if (threadNumber > count) {
            threadNumber = count;
        }

        int digitsPerThread = count / threadNumber;
        int remainingDigits = count % threadNumber;
        int currentStart = start;

        System.out.println("Iniciando cálculo con " + threadNumber + " hilos...");

        long startTime = System.nanoTime(); // Medir tiempo de inicio

        for (int i = 0; i < threadNumber; i++) {
            int digitsForThisThread = digitsPerThread;
            if (i == threadNumber - 1) {
                digitsForThisThread += remainingDigits;
            }

            int threadEnd = currentStart + digitsForThisThread;
            
            PiThread thread = new PiThread(currentStart, threadEnd);
            threads.add(thread);
            thread.start();
            
            currentStart = threadEnd;
        }

        try {
            for (PiThread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Calculation interrupted", e);
        }

        long endTime = System.nanoTime(); // Medir tiempo de finalización
        double elapsedTime = (endTime - startTime) / 1e9;
        System.out.println("Tiempo total de ejecución en PiDigits: " + elapsedTime + " segundos");

        int offset = 0;
        for (PiThread thread : threads) {
            byte[] threadDigits = thread.getDigits();
            System.arraycopy(threadDigits, 0, digits, offset, threadDigits.length);
            offset += threadDigits.length;
        }

        return digits;
    }
}