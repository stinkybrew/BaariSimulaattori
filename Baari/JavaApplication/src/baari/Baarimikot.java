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
public class Baarimikot {

    private String nimi;
    private int palkka;
    private Control control;
    private int vasy=0;
    private int toita = 0;
    private boolean tiski = true;
    private int maksu;

    int hinta = 5;

    public Baarimikot(Control kontrolli, String name, int pay) {
        control=kontrolli;
        nimi = name;
        palkka=pay;
        maksu=control.getTunti();
    }

    boolean Tarjoile(double asiakasRaha) {
        if (asiakasRaha >= hinta&&tiski) {
            control.lisaaRahaa(5);
            toita=1;
            return true;
        } else {
            return false;
        }
    }
    
    void vasyy(){
        vasy++;
    }
    
    void resetVasy(){
        vasy=0;
    }
    
    void tyoskentele(){
        if(toita>0){
            toita--;
            if(tiski){
                if(control.Jonottavat().size()>0){
                Asiakas a = control.Jonottavat().get(control.getRandomInt(0, control.Jonottavat().size()));
                control.tarjoile(a);
                }else if(control.siivoa()){
                    toita+=2;
                    tiski=false;
                }
            }else{
                if(control.siivoa()){
                    toita+=2;
                    tiski=false;
                }else{
                    tiski=true;
                }
            }
        }else{
            vasy--;
        }
        if(maksu!=control.getTunti()){           
            control.maksaPalkka(palkka/(30*12));
        }
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

    void siivoa(String oksennus) {
        control.delete(oksennus);
    }

    void ulosheitto(String asiakas) {
        control.delete(asiakas);
    }

    void tervehdi(String asiakas) {
        System.out.println("Terve " + asiakas);
    }
    
    String getNimi(){
        return nimi;
    }
    
    int getPalkka(){
        return palkka;
    }
}