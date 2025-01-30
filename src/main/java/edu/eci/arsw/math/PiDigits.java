package edu.eci.arsw.math;

import java.util.ArrayList;

import edu.eci.arsw.threads.PiThread;

///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>
public class PiDigits {

    private static int DigitsPerSum = 8;
    private static double Epsilon = 1e-17;
    private static ArrayList<PiThread> threads = new ArrayList<>();
    
        
    /**
     * Returns a range of hexadecimal digits of pi.
     * @param start The starting location of the range.
     * @param count The number of digits to return
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count, int threadNumber) {
        byte[] digits = new byte[threadNumber];
        createPiThreads(start, count, threadNumber);
        int i = 0;
        for(PiThread t : threads ){
            
        }
        return null;

        


         
    }

    public static void createPiThreads(int start, int count, int threadNumber){
        int range, modulo;
        range = (count-start) / threadNumber;
        modulo = (count-start) % threadNumber;
        for (int i = 0; i< threadNumber; i++){
            int end = start + range;
            if(i == threadNumber-1){
                end += modulo;                    
            }
            PiThread t = new PiThread(start, end);
            t.start();
            threads.add(t);
            try {
                t.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            start = start+ range;
        }
    }
}
