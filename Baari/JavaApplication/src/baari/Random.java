/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baari;

/**
 *
 * @author wille
 */
import java.util.concurrent.ThreadLocalRandom;
public class Random {
    int getInt(int min, int max){
        if(max==0)
            return 0;
        if(min>max){
            int temp=min;
            min=max;
            max=temp;
        }
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}