/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baari;

import javax.swing.JOptionPane;
import java.util.ArrayList;
/**
 *
 * @author wille
 */
public class Baari implements Runnable{
    //ExploitableVar
    private String vastaus;
    static private int paiva;
    static private int tunti;
    static private int minuutti;
    static private int factor;
    private boolean valid = false;
    boolean event = true;
    private double kassa=1000;
    private String auki;
    
    private ArrayList<Asiakas> asiakaslista = new ArrayList<Asiakas>();
    private ArrayList<Baarimikot> baarimikot = new ArrayList<Baarimikot>();
    private ArrayList<Poket> poket = new ArrayList<Poket>();
    private ArrayList<Asiakas> jono = new ArrayList<Asiakas>();
            
    public void run(){
        while(true){
            if(event){
                System.out.println("Event");
                event=false;
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                }
            }
        }
    }
    
    public Baari(){
        vastaus = JOptionPane.showInputDialog("Paljon kello on?\n(Anna vastaus hh:mm muodossa)");
        while(!valid){
            while(true){
                String[] array = vastaus.split(":");
                if(array.length!=2){
                    vastaus = JOptionPane.showInputDialog("Paljon kello on?\n(Anna vastaus hh:mm muodossa)");
                    break;
                }
                int[] ajat = new int[2];
                for(int i=0;i<2;i++){
                    ajat[i]=Integer.parseInt(array[i]);
                }
                if(ajat[0]<0||ajat[0]>23||ajat[1]<0||ajat[1]>=60){
                    vastaus = JOptionPane.showInputDialog("Paljon kello on?\n(Anna vastaus hh:mm muodossa)");
                    break;
                }
                tunti=ajat[0];
                minuutti=ajat[1];
                if(tunti<20&&tunti>4){
                    auki="Baari on kiinni";
                }else{
                    auki="Baari on auki";
                }
                valid = true;
                break;
            }
        }
        valid = false;
        vastaus = JOptionPane.showInputDialog("Mikä päivä tänään on?");
        while(!valid){
            while(true){
                char[] charArray = vastaus.toCharArray();
                charArray[0]=Character.toLowerCase(charArray[0]);
                switch(charArray[0]){
                    case 'm':
                        paiva = 0;
                        valid = true;
                        break;
                    case 't':
                        if(charArray[1]=='i'){
                            paiva = 1;
                            valid = true;
                        }else if(charArray[1]=='o'){
                            paiva = 3;
                            valid = true;
                        }else{
                            vastaus = JOptionPane.showInputDialog("Mikä päivä tänään on?\n(Anna kaksi ensimmäistä kirjainta)");
                        }
                        break;
                    case 'k':
                        paiva = 2;
                        valid = true;
                        break;
                    case 'p':
                        paiva = 4;
                        valid = true;
                        break;
                    case 'l':
                        paiva = 5;
                        valid = true;
                        break;
                    case 's':
                        paiva = 6;
                        valid = true;
                        break;
                    default:
                        vastaus = JOptionPane.showInputDialog("Mikä päivä tänään on?\n(Anna kaksi ensimmäistä kirjainta)");
                }
                if(valid)
                    break;
            }
        }
    }
    
    public int[] getTime(){
        int[] time = {paiva, tunti, minuutti};
        return time;
    }
    
    public void setTime(int day, int hour, int minute){
        paiva = day;
        tunti = hour;
        minuutti = minute;
        System.out.println("Baari set aika "+paiva+tunti+minuutti);
    }
    
    public double getMoney(){
        return kassa;
    }
    
    public void deductMoney(double maara){
        kassa -= maara;
    }
    
    public void setAuki(String teksti){
        auki=teksti;
    }
    
    public String getAuki(){
        return auki;
    }
    
    public void addJonoon(Asiakas asiakas){
        if(!jono.contains(asiakas))            
            jono.add(asiakas);
    }
    
    public void removeJonoon(Asiakas asiakas){
        if(jono.contains(asiakas))
            jono.remove(asiakas);
    }
    
    public ArrayList<Asiakas> getJono(){
        return jono;
    }
    
    public ArrayList<Asiakas> getAsiakaslista(){
        return asiakaslista;
    }
    
    public ArrayList<Baarimikot> getBaarimikot(){
        return baarimikot;
    }
    
    public ArrayList<Poket> getPoket(){
        return poket;
    }
    
    void emptyAsiakaslista(){
        asiakaslista.clear();
    }
    
    public void newAsiakas(Asiakas asiakas){
        asiakaslista.add(asiakas);
    }
    
    public void newBartender(Baarimikot a){
        baarimikot.add(a);
    }
    
    public void newBouncer(Poket a){
        poket.add(a);
    }
    
    public void removeBaarimikko(int index){
        try{
        baarimikot.remove(index);
        }catch(ArrayIndexOutOfBoundsException e){}
    }
    
    public void removePoke(int index){
        try{
        poket.remove(index);
        }catch(ArrayIndexOutOfBoundsException e){}
    }
    
    public void removeAsiakas(int index){
        try{
        asiakaslista.remove(index);
        }catch(ArrayIndexOutOfBoundsException e){
        }
    }
    
    public void addKassa(int money){
        kassa += money;
    }
    
    public String getTimeString(){
        vastaus="";
        if(tunti<10){
            vastaus+="0";
        }
        vastaus+=tunti+":";
        if(minuutti<10){
            vastaus+="0";
        }
        vastaus+=minuutti+", ";
        switch(paiva){
            case 0:
                vastaus+="Maanantai";
                break;
            case 1:
                vastaus+="Tiistai";
                break;
            case 2:
                vastaus+="Keskiviikko";
                break;
            case 3:
                vastaus+="Torstai";
                break;
            case 4:
                vastaus+="Perjantai";
                break;
            case 5:
                vastaus+="Lauantai";
                break;
            case 6:
                vastaus+="Sunnuntai";
                break;
            default:
                vastaus+="DayERROR";
                break;
        }
        return vastaus;
    }
    
    public void setFactor(int muutos){
        factor = muutos;
    }
    
    public void timePass(int multi){
        factor = multi;
        switch(multi){
            case 0:
                break;
            case 1:
                minuutti++;
                break;
            case 2:
                minuutti++;
                break;
            case 3:
                tunti++;
                break;
        }
        if(minuutti>=60){
            minuutti=0;
            tunti++;
        }
        if(tunti>=24){
            tunti=0;
            paiva++;
        }
        if(paiva>6){
            paiva=0;
        }
    }
}