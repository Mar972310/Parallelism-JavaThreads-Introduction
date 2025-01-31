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

        int offset = 0;
        for (PiThread thread : threads) {
            byte[] threadDigits = thread.getDigits();
            System.arraycopy(threadDigits, 0, digits, offset, threadDigits.length);
            offset += threadDigits.length;
        }

        return digits;
    }
}