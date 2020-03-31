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
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Asiakas {
    private double Raha = ThreadLocalRandom.current().nextDouble(0.0, 500); 
    private double Humalatila = ThreadLocalRandom.current().nextDouble(0.0, 2.0);
    private String Nimi; 
    private String Mielentila;
    private Double Aggressiivisuus = ThreadLocalRandom.current().nextDouble(0.0, 10);
    private Control controlleri;
    private int Ulosheittoriski = 0;
    private int Toiminta=0;
    private int Juomaajaljella=0;
    private String Tila="Hengailemassa";

    
    public Asiakas(Control control){
        controlleri=control;
        Nimi = controlleri.annaNimi();
        ArrayList<String> MielentilaLista = new ArrayList<String>();
        MielentilaLista.add("rauhallinen");
        MielentilaLista.add("agressiivinen");
        MielentilaLista.add("leikkisä");
        MielentilaLista.add("parilla");
        MielentilaLista.add("kaatokänni");
        Mielentila = MielentilaLista.get(controlleri.getRandomInt(0, MielentilaLista.size()));
        if (Mielentila == "kaatokänni"){
            Raha = Raha + 100;
            Humalatila = Humalatila * 1.5;
        }
        if (Mielentila == "leikkisä"){
            Humalatila = Humalatila * 1.2;
        }
        if (Mielentila == "agressiivinen"){
            Aggressiivisuus = Aggressiivisuus * 1.5;
        }
        
    }
    public double getHumalatila() {
        return this.Humalatila;}
    
    public double getRaha() {
        return this.Raha;}

    
    public String getMielentila(){
        return this.Mielentila;
    }
    
    public double getAggressiivisuus(){
        return this.Aggressiivisuus;
    }
    
    public String getTila(){
        return this.Tila;
    }
    
    public void action(){
        if(Toiminta==0){
            if(controlleri.getRandomInt(1, 100)>Humalatila*Humalatila*Humalatila){
                Tila="Rupattelemassa";
                Toiminta++;
            }
        }else{
            Toiminta--;
        }
    }
    
    public int getJuomaajaljella() {
        if (Juomaajaljella >= 1)
        {
            if(Toiminta==0){
                Juomaajaljella--;
                Humalatila+=0.05;
                Tila="Juomassa";
            }
            if(Juomaajaljella<=0)
                controlleri.randomValues[3]++;
        }
        else{
            if(controlleri.tilaaJuoma(Raha)){
                Raha-=5;
                Juomaajaljella=4;
            }else if(Raha<5){
                controlleri.viesti(Nimi+ " huomasi että häneltä on rahat loppu ja meni kotiin.");
                controlleri.delete(Nimi);
            }else{
                Tila="Jonottamassa";
                controlleri.jono(this);
            }
        }
            return this.Toiminta;
    }
    //OMAT SÄÄDÖT
    String getNimi(){
        return Nimi;
    }
    int[] randomEvent(){
        int[] i= new int[4];
        double random=ThreadLocalRandom.current().nextDouble(0, 100);
        if(random<Humalatila/3.0){
            System.out.println(Nimi+" "+Humalatila);
            if(controlleri.detect()){
                Tila="Oksentelemassa";
                Raha-=60;
                if(Raha<=0){
                    controlleri.viesti("Asiakas "+Nimi+" oksensi ja hänellä ei ollut rahaa siivousmaksuun niin hänet heitettiin ulos");
                    controlleri.delete(Nimi);
                }else{
                    if(Ulosheittoriski>2){
                        controlleri.viesti("Asiakas "+Nimi+" oksensi ja hän maksoi siivousmaksun. Hän on kuitenkin häiriköinyt liikaa ja hänet heitettiin ulos");
                        controlleri.delete(Nimi);
                    }else{
                        controlleri.viesti("Asiakas "+Nimi+" oksensi ja hän maksoi siivousmaksun");
                        Ulosheittoriski++;
                    }
                }
            }else{
                i[0]++;
            }
            return i;
        }
        if(controlleri.getAsiakaslista().size()>1 && ( Mielentila.equals("agressiivinen") && Humalatila>2.5)){
            if(random<Aggressiivisuus){
                ArrayList<Asiakas> Kohteet = controlleri.getAsiakaslista();
                Kohteet.remove(this);
                controlleri.tappelu(this, Kohteet.get(controlleri.getRandomInt(0, Kohteet.size())));
                if(controlleri.factor!=1)
                    i[1]++;
                return i;
            }
        }
        if(random<Humalatila/3.0){
            Juomaajaljella=0;
            if(controlleri.detect()){
                if(controlleri.getRandomInt(1, 101)<Humalatila*30.0){
                    controlleri.viesti(Nimi + " rikkoi lasin ja vaikuttaa olevan tarpeeksi sievässä että vietiin ulos.");
                    controlleri.delete(Nimi);
                }else{
                    controlleri.viesti(Nimi + " rikkoi lasin vahingossa");
                    Ulosheittoriski++;
                }
            }else{
                i[2]++;
            }
        }
        return i;
    }
}