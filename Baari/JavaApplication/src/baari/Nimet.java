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
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.HashMap;
public class Nimet {
    private ArrayList<String> etunimi = new ArrayList<String>(){{
    add("Aapo");
    add("Pertti");
    add("Kullervo");
    add("Oskari");
    add("Seppo");
    add("Roosa");
    add("Urpo");
    add("Liisa");
    add("Matti");
    add("Olavi");
    add("Jonne");
    add("Make");
    add("Viljami");
    add("Joni");
    add("Harri");
    add("Marianne");
    add("Johanna");
    add("Emilia");
    add("Mikael");
    add("Wille");
    add("Mika");
    }};
    private ArrayList<String> sukunimi = new ArrayList<String>(){{
    add("Asikainen");
    add("Suomalainen");
    add("Venäläinen");
    add("Korhonen");
    add("Meikäläinen");
    add("Muukalainen");
    add("Virtanen");
    add("Uponen");
    add("Mäkinen");
    add("Nieminen");
    add("Mäkelä");
    add("Hämäläinen");
    add("Laine");
    add("Järvinen");
    add("Lehtonen");
    add("Lehtinen");
    add("Saarinen");
    add("Salminen");
    add("Heinonen");
    add("Norppa");
    add("Niemi");
    add("Salo");
    add("Mattila");
    add("Ojala");
    add("Kallio");
    add("Anttila");
    add("Koivisto");
    add("Aalto");
    add("Tikka");
    add("Tuovinen");
    add("Uponen");
    }};
    
    private ArrayList<String> usedNimet = new ArrayList<String>();
    //HashMap<String, Integer> etunimiCheck = new HashMap<String, Integer>();
    //HashMap<String, Integer> sukunimiCheck = new HashMap<String, Integer>();
    /*
    ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
    List<List<String>> ls2d = new ArrayList<List<String>>();
    List<String> x = new ArrayList<String>();
    */
    
    String getNimi(){
        String etun;
        String sukun;
        String kokonimi="";
        boolean valid = true;
        while(valid){
            etun=etunimi.get(ThreadLocalRandom.current().nextInt(0, etunimi.size()));
            sukun=sukunimi.get(ThreadLocalRandom.current().nextInt(0, sukunimi.size()));
            kokonimi=etun+" "+sukun;
            if(!usedNimet.contains(kokonimi)){
                usedNimet.add(kokonimi);
                valid = false;
            }
        }
        return kokonimi;
    }
    
    void returnNimi(String nimi){
        usedNimet.remove(nimi);
    }
}