package baari;

//@author Wille

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UI extends JFrame{
    private Control control;
    JPanel container = new JPanel();
    JPanel paneeli= new JPanel();
    JPanel paneeli2= new JPanel();
    JPanel paneeli3= new JPanel();
    JPanel timePaneeli= new JPanel();
    JPanel tyontekijaPaneeli = new JPanel();
    JPanel asiakasPaneeli = new JPanel();
    JButton stopTime = new JButton("Stop");
    JButton startTime = new JButton("Play");
    JButton fastTime = new JButton("10x");
    JButton minuteTime = new JButton("60x");
    JButton hourTime = new JButton("3600x");
    JButton hireBartender = new JButton("Palkkaa baarimikko");
    JButton fireBartender = new JButton("Erota baarimikko");
    JButton hireBouncer = new JButton("Palkkaa poke");
    JButton fireBouncer = new JButton("Erota poke");
    JButton fight = new JButton("Tappelut");
    boolean togglefight = true;
    //JButton open = new JButton("Avaa baari");
    //JButton close = new JButton("Sulje baari");
    //JButton getCustomers = new JButton("Paikallaolijoiden määrä");
    JTextField aika = new JTextField(24);
    JTextArea field = new JTextArea(21,48);
    JTextArea asiakkaat = new JTextArea(21,24);
    JScrollPane scrollPane = new JScrollPane(field);
    JScrollPane asiakasPane = new JScrollPane(asiakkaat);
    public UI(){
        aika.setEditable(false);
        field.setEditable(false);
        asiakkaat.setEditable(false);
        scrollPane.setPreferredSize(new Dimension(590, 350));
        asiakasPane.setPreferredSize(new Dimension(375, 370));
        field.setText("Aika kuluu normaalisti");
        stopTime.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Output("Aika pysäytetty");
                //control.setFactor(0);
                control.factor=0;
            }
        });
        startTime.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Output("Aika kuluu normaalisti");
                control.factor=1;
                control.FF=false;
            }
        });
        fastTime.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Output("Aika kuluu 10x nopeammin");
                control.factor=1;
                control.FF=true;
            }
        });
        minuteTime.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Output("Yksi sekuntti vastaa yhtä minuuttia");
                control.factor=2;
            }
        });
        hourTime.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Output("Yksi sekuntti vastaa yhtä tuntia");
                control.factor=3;
            }
        });
        hireBartender.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String tempNimi;
                int vastausInt=1;
                while(vastausInt==1){
                    tempNimi=control.annaNimi();
                    int palkka=(int)(685+Math.random()*1300);
                    vastausInt=JOptionPane.showConfirmDialog(null, "Hakemus\nNimi: "+tempNimi+"\nPalkka: "+palkka+"€/kk");
                    if(vastausInt==0){
                        control.palkkaaBM(tempNimi, palkka);
                    }
                    if(vastausInt>0)
                        control.palautaNimi(tempNimi);
                }
            }
        });
        fireBartender.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String a ="Kenet haluat erottaa?\nSinulla on töissä seuraavat baarimikot:";
                for(Baarimikot mikko:control.getBaarimikot()){
                    a+="\n"+mikko.getNimi();
                }
                control.erotaBaarimikko(JOptionPane.showInputDialog(a));
            }
        });
        hireBouncer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String tempNimi;
                int vastausInt=1;
                while(vastausInt==1){
                    tempNimi=control.annaNimi();
                    int palkka=(int)(Math.random()*3000);
                    vastausInt=JOptionPane.showConfirmDialog(null, "Hakemus\nNimi: "+tempNimi+"\nPalkka: "+palkka+"€/kk");
                    if(vastausInt==0){
                        control.palkkaaPoke(tempNimi, palkka);
                    }
                    if(vastausInt>0)
                        control.palautaNimi(tempNimi);
                }
            }
        });
        fireBouncer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String a ="Kenet haluat erottaa?\nSinulla on töissä seuraavat poket:";
                for(Poket poke:control.getPoket()){
                    a+="\n"+poke.getNimi();
                }
                control.erotaBaarimikko(JOptionPane.showInputDialog(a));
            }
        });
        fight.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(togglefight){
                    fight.setContentAreaFilled(false);
                    togglefight=false;
                }else{
                    fight.setContentAreaFilled(true);
                    togglefight=true;
                }
            }
        });/*
        open.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(control.getAika()[1]>=16||control.getAika()[1]<=4){
                    if(control.forceClose){
                        control.forceClose=false;
                        Output("Baari avattiin");
                    }else{
                        Output("Baari on jo auki!");
                    }
                }else{
                    control.forceClose=false;
                    control.setAika(control.getAika()[0]+1, 16, 0);
                    System.out.println("UI setAika");
                    Output("Baari avattiin");
                }
            }
        });
        close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Output("Baari pakotettiin kiinni");
                control.forceClose=true;
                control.forceClosed();
            }
        });
        getCustomers.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Output(control.getAsiakaslista());
            }
        });*/
        //stopTime.setBounds(60,400,220,30);
        //container.setBounds(500, 100, 200, 100);
        //container.setBorder(BorderFactory.createLineBorder(Color.black));
        paneeli.setBounds(5, 5, 300, 400);
        paneeli.setBorder(BorderFactory.createLineBorder(Color.black));
        paneeli2.setBounds(310, 45, 600, 360);
        paneeli2.setBorder(BorderFactory.createLineBorder(Color.black));
        paneeli3.setBounds(310, 5, 300, 35);
        paneeli3.setBorder(BorderFactory.createLineBorder(Color.black));
        timePaneeli.setBounds(10, 10, 290, 110);
        timePaneeli.setBorder(BorderFactory.createTitledBorder("Aika"));
        timePaneeli.add(stopTime);
        timePaneeli.add(startTime);
        timePaneeli.add(fastTime);
        timePaneeli.add(minuteTime);
        timePaneeli.add(hourTime);
        tyontekijaPaneeli.setBounds(10, 120, 290, 110);
        tyontekijaPaneeli.setBorder(BorderFactory.createTitledBorder("Työntekijät"));
        tyontekijaPaneeli.add(hireBartender);
        tyontekijaPaneeli.add(fireBartender);
        tyontekijaPaneeli.add(hireBouncer);
        tyontekijaPaneeli.add(fireBouncer);
        asiakasPaneeli.setBounds(915, 5, 390, 400);
        asiakasPaneeli.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Asiakkaat"));
        asiakasPaneeli.add(asiakasPane,BorderLayout.CENTER);
        //paneeli.add(fight);
        //paneeli.add(open);
        //paneeli.add(close);
        //paneeli.add(getCustomers);
        paneeli2.add(scrollPane,BorderLayout.CENTER);
        paneeli3.add(aika);
        add(timePaneeli);
        add(tyontekijaPaneeli);
        add(asiakasPaneeli);
        add(paneeli);
        add(paneeli2);
        add(paneeli3);
        add(container);
        //JFrame ominaisuudet
        setSize(1330,460);
        setTitle("Baari");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    void Link(Control bar){
        this.control= bar;
    }
    
    void Output(String output){
        field.setText(field.getText()+"\n"+control.getAika()[1]+":"+control.getAika()[2]+", "+output);
        field.setCaretPosition(field.getDocument().getLength());
    }
}