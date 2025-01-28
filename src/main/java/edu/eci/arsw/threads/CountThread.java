/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;
import java.lang.Thread;


/**
 *
 * @author Team 2 (Samuel, Juan, Valentina)
 */
public class CountThread extends Thread {

    private int A;
    private int B;

    public CountThread(int A, int B){
        this.A = A;
        this.B = B;
    }

    public void run(){
        printed();
    }

    public int getA(){
        return A;
    }

    public int getB(){
        return B;
    }

    public void printed(){
        for (int i = getA();  i<= getB();i++ ){
            System.out.println(i);
        }
    }   
}
