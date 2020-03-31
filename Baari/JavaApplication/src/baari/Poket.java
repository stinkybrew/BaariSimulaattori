/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baari;

/**
 *
 * @author Wille
 */
public class Poket {

    private String nimi;
    private int palkka;
    private Control control;
    private int vasy;

    public Poket(Control kontrolli, String name, int pay) {
        control=kontrolli;
        nimi = name;
        palkka=pay;
    }
    
    void vasyy(){
        vasy++;
    }
    
    boolean tarkkaile(){
        if(control.getRandomInt(1, 100)>vasy/7){
            if(control.getRandomInt(1,100)>control.getAsiakaslista().size()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    void ulosheitto(String asiakas) {
        control.delete(asiakas);
    }
    
    String getNimi(){
        return nimi;
    }
}