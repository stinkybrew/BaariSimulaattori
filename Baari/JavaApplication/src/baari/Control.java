
package baari;

//@author Wille

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Control implements Runnable{
    volatile boolean keepRunning = true;
    boolean forceClose=false;
    Random satunnainen = new Random();
    Baari Actual = new Baari();
    Thread b = new Thread(Actual);
    UI kaytto = new UI();
    Nimet nimilista = new Nimet();
    int factor=1;//Relocate
    int[] randomValues=new int[4];
    int[] muutos = randomValues;
    boolean FF = false;
    public Control(){
        kaytto.Link(this);
    }
    
    public void run() {
        int sec = 0;
        String report="";
        int ruuhka=0;
        int suosio=1;
        boolean add=false;
        int[] time;
        
        System.out.println("Starting to loop.");
        while (keepRunning) {
            try {
                if(sec>60||factor==2||factor==3){
                    if(Actual.getAuki().equals("Baari on auki")){
                        if(satunnainen.getInt(1, 100)<33&&satunnainen.getInt(1, 100)>Math.log(Actual.getAsiakaslista().size())*25){
                            time = Actual.getTime();
                            if(time[0]>=4)
                                ruuhka+=ruuhka+1;
                            if(time[1]>=20&&time[1]<24)
                                ruuhka+=ruuhka+2;
                            if(time[1]<6)
                                ruuhka--;
                            ruuhka= (int)(ruuhka*Math.random());
                            while(ruuhka>0){
                                luoAsiakas();
                                ruuhka--;
                                add=true;
                            }
                        }
                        /*
                        time = Actual.getTime();
                        if(time[0]>=4)
                            ruuhka+=ruuhka+1;
                        if(time[1]>=20&&time[1]<23)
                            ruuhka+=ruuhka+1;
                        if(time[1]<6)
                            ruuhka--;
                        ruuhka= (int)(ruuhka*Math.random());
                        while(ruuhka>0){
                            luoAsiakas();
                            ruuhka--;
                            add=true;
                        }
                        */
                    }
                    Actual.timePass(factor);
                    System.out.println(Actual.getTime()[1]);
                    if(Actual.getTime()[1]<20&&Actual.getTime()[1]>3){
                        Actual.setAuki("Baari on kiinni");
                        Actual.emptyAsiakaslista();
                        for(int i:randomValues){
                            i=0;
                        }
                        for(Asiakas asiakas: Actual.getAsiakaslista()){
                            nimilista.returnNimi(asiakas.getNimi());
                        }
                    }else{
                        Actual.setAuki("Baari on auki");
                    }
                    System.out.println(Actual.getAuki());
                    String asiakkaat="";
                    for(Asiakas asiakas: Actual.getAsiakaslista()){
                        asiakkaat+=asiakas.getNimi()+", tila: "+asiakas.getTila()+"\n";
                    }
                    kaytto.asiakkaat.setText(asiakkaat);
                    //Action();
                    for(int i=0;i<Actual.getAsiakaslista().size();i++){
                        Asiakas asiakas=Actual.getAsiakaslista().get(i);
                        asiakas.action();
                        asiakas.getJuomaajaljella();
                    }
                    for(Baarimikot mikko:Actual.getBaarimikot()){
                        mikko.tyoskentele();
                    }
                    if(Actual.getJono().size()>0)
                        kaytto.Output(Actual.getJono().size()+" asiakasta jonossa");
                    //ERROR
                    //for(Asiakas asiakas:Actual.getAsiakaslista()){
                    for(int j=0;j<Actual.getAsiakaslista().size();j++){
                        Asiakas asiakas = Actual.getAsiakaslista().get(j);
                        int[] events = asiakas.randomEvent();
                        //int[] lol={1,2,3};
                        for (int i = 0; i < 4; i++) {
                            randomValues[i]+=events[i];
                        }
                    }
                    //ERROR
                    if(randomValues[0]>0)
                        report+=randomValues[0]+" oksennusta ";
                    if(randomValues[1]>0)
                        report+=randomValues[1]+" tappelua ";
                    if(randomValues[2]>0)
                        report+=randomValues[2]+" pulloa rikki";
                    if(randomValues[3]>0)
                        report+=randomValues[3]+" tyhjää pulloa";
                    if((randomValues[0]>0||randomValues[1]>0||randomValues[2]>0||randomValues[3]>0)&&muutos!=randomValues){
                        muutos = randomValues;
                        kaytto.Output(report);
                    }
                    report="";
                    randomValues[1]=0;
                    sec=0;
                }
                sec++;
                if(FF)
                    sec+=9;
                kaytto.aika.setText(Actual.getTimeString()+" "+Actual.getMoney()+"€   "+Actual.getAuki());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Done looping.");
    }
    
    public void setFactor(int fac){
        Actual.setFactor(fac);
    }
    
    public void viesti(String viesti){//UNUSED
        kaytto.Output(viesti);
    }
    
    public int[] getAika(){
        return Actual.getTime();
    }
    
    public void setAika(int paiva, int tunti, int minuutti){
        Actual.setTime(paiva, tunti, minuutti);
        System.out.println("Control set aika");
    }
    
    public int getTunti(){
        return Actual.getTime()[1];
    }
    
    public void luoAsiakas(){
        Asiakas asiakas = new Asiakas(this);
        Actual.newAsiakas(asiakas);
        kaytto.Output(asiakas.getNimi() + " astui baariin");
    }
    
    public void palkkaaBM(String nimi, int raha){
        Baarimikot mikko = new Baarimikot(this, nimi, raha);
        Actual.newBartender(mikko);
        kaytto.Output(nimi+" sai juuri työpaikan baarimikkona!");
    }
    
    public void palkkaaPoke(String nimi, int raha){
        Poket poke = new Poket(this, nimi, raha);
        Actual.newBouncer(poke);
        kaytto.Output(nimi+" sai juuri työpaikan pokena!");
    }
    
    public void maksaPalkka(double raha){
        Actual.deductMoney(raha);
    }
    
    public void delete(String nimi){
        int i=-1;
        for(Asiakas asiakas:getAsiakaslista()){
            //System.out.println(mikko.getNimi()+"="+nimi);
            if(asiakas.getNimi().equals(nimi)){
                i=getAsiakaslista().indexOf(asiakas);
                System.out.println("MATCH");
            }
        }
        Actual.removeAsiakas(i);
        nimilista.returnNimi(nimi);
    }
    
    public void jono(Asiakas asiakas){
        Actual.addJonoon(asiakas);
    }
    
    public void tappelu(Asiakas tappelija1, Asiakas tappelija2){
        if(tappelija2.getMielentila().equals("agressiivinen")||tappelija2.getHumalatila()>1.5){
            double voima1, voima2;
            voima1 = tappelija1.getHumalatila();
            voima1-=2.5;
            if(voima1<0)
                voima1*=-1;
            voima2 = tappelija2.getHumalatila();
            voima2-=2.5;
            if(voima2<0)
                voima2*=-1;
            if(voima1<voima2){
                kaytto.Output(tappelija1.getNimi()+" ja "+tappelija2.getNimi() + "tappelivat. "+tappelija1.getNimi()+" voitti, mutta kummatkin heitettiin ulos.");
            }
            if(voima1>voima2){
                kaytto.Output(tappelija1.getNimi()+" ja "+tappelija2.getNimi() + " tappelivat. "+tappelija2.getNimi()+" voitti, mutta kummatkin heitettiin ulos.");
            }
            if(voima1==voima2){
                kaytto.Output(tappelija1.getNimi()+" ja "+tappelija2.getNimi() + " tappelivat ilman selvää voittajaa. Kummatkin heitettiin ulos.");
            }
            delete(tappelija1.getNimi());
            delete(tappelija2.getNimi());
        }else if(detect()){
            kaytto.Output(tappelija1.getNimi()+" yritti haastaa riitaa "+tappelija2.getNimi()+" kanssa, mutta hän ei tullut leikkiin mukaan. "+tappelija1.getNimi()+" heitettiin ulos.");
            delete(tappelija1.getNimi());
        }
    }
    
    public boolean tilaaJuoma(double kateinen){
        boolean saatavilla=false;
        for(Baarimikot mikko:Actual.getBaarimikot()){
            if(mikko.Tarjoile(kateinen))
                saatavilla=true;
        }
        return saatavilla;
    }
    
    public void tarjoile(Asiakas asiakas){
        asiakas.getJuomaajaljella();
        Actual.removeJonoon(asiakas);
    }
    
    public void lisaaRahaa(int kateinen){
        Actual.addKassa(kateinen);
    }
    
    public boolean siivoa(){
        if(randomValues[0]!=0||randomValues[2]!=0||randomValues[3]>10){
            if(randomValues[0]>0){
                randomValues[0]--;
                return true;
            }
            if(randomValues[2]>0){
                randomValues[2]--;
                return true;
            }
            if(randomValues[3]>10){
                randomValues[3]=0;
                return true;
            }
            return true;
        }else{
            return false;
        }
    }
    
    public boolean detect(){
        boolean det=false;
        for(Baarimikot mikko:Actual.getBaarimikot()){
            if(mikko.tarkkaile())
                det=true;
        }
        for(Poket poke:Actual.getPoket()){
            if(poke.tarkkaile())
                det=true;
        }
        return det;
    }
    
    public String annaNimi(){
        return nimilista.getNimi();
    }
    
    public void palautaNimi(String nimi){
        nimilista.returnNimi(nimi);
    }
    
    public ArrayList<Asiakas> getAsiakaslista(){
        return Actual.getAsiakaslista();
    }
    
    public String getAsiakaslistaString(){
        return Actual.getAsiakaslista().toString();
    }
    
    public ArrayList<Asiakas> Jonottavat(){
        return Actual.getJono();
    }
    
    public ArrayList<Baarimikot> getBaarimikot(){
        return Actual.getBaarimikot();
    }
    
    public ArrayList<Poket> getPoket(){
        return Actual.getPoket();
    }
    
    public void erotaBaarimikko(String nimi){
        int i=-1;
        for(Baarimikot mikko:getBaarimikot()){
            System.out.println(mikko.getNimi()+"="+nimi);
            if(mikko.getNimi().equals(nimi)){
                i=getBaarimikot().indexOf(mikko);
                System.out.println("MATCH");
            }
        }
        Actual.removeBaarimikko(i);
        nimilista.returnNimi(nimi);
    }
    
    public void erotaPoke(String nimi){
        int i=-1;
        for(Poket poke:getPoket()){
            System.out.println(poke.getNimi()+"="+nimi);
            if(poke.getNimi().equals(nimi)){
                i=getBaarimikot().indexOf(poke);
                System.out.println("MATCH");
            }
        }
        Actual.removePoke(i);
        nimilista.returnNimi(nimi);
    }
    
    public int getRandomInt(int min, int max){
        return satunnainen.getInt(min, max);
    }
    
    public void hire(){
        String tempNimi;
        int vastausInt=1;
        while(vastausInt==1){
            tempNimi=annaNimi();
            int palkka=getRandomInt(2000,3000);
            vastausInt=JOptionPane.showConfirmDialog(null, "Hakemus\nNimi: "+tempNimi+"\nPalkka: "+palkka+"€/kk");
            if(vastausInt==0){
                palkkaaBM(tempNimi, palkka);
            }
            if(vastausInt>0)
                palautaNimi(tempNimi);
        }
    }

    public static void main(String[] args) {
        Control control = new Control();
        Thread t = new Thread(control);
        t.start();
        //DELETE
        Scanner s = new Scanner(System.in);
        while (!s.next().equals("stop")){
            
        }
        //DELETE
        control.keepRunning = false;
        t.interrupt();
    }
}