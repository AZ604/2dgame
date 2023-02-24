package com.mygdx.game;

public class BackgroundTimer implements Runnable{

    private long startTime; //Zeit, zu der der RaceTimer gestartet wird, wird hier gespeichert
    private long time; //Zeit, wie lange das Item wirkt
    private Tank tank; //Panzer, der das Item aufgehoben hat

    public BackgroundTimer(long startTime, long time, Tank tank){

        this.startTime = startTime;
        this.time = time;
        this.tank = tank;

    }

    public void run(){

        switch (tank.getItem().getId()){ //Switch nach ID, die angibt, welche Art von Item aufgehoben wurde

            case 0: //SPEEDITEM WAS PICKED UP

                System.out.println("Enable tank speed mode"); //Testausgabe auf Konsole
                tank.setSpeed(2); //doppelter speed für tank
                tank.getItem().setVisible(false); //Während Item wirkt, gibt es es nicht auf der Map zum aufsammeln


                while (true){ //stetiger loop, damit die Zeit permanent gerechnet wird

                    long now = System.currentTimeMillis() /1000; //Aktuelle Systemzeit in Sekunden
                    long elapsed = now - startTime; //Vergangene Zeit seit Beginn in Sekunden

                    if (elapsed >= time){ //Zeitfenster wird ab jetzt überschritten, Item wird deaktiviert

                        System.out.println("Speed mode over");
                        tank.setSpeed(1); //speed wieder normal
                        tank.getItem().setVisible(true); //Item erscheint wieder auf Map zum erneuten Aufsammeln
                        tank.setItem(null); //Tank wird das Item weggenommen
                        break;

                    }

                    try {

                        Thread.sleep(100);

                    } catch (InterruptedException e){

                        //egal
                    }

                }

                break;

            case 1: //HEALTHITEM WAS PICKED UP

                System.out.println("Restore tank health");
                tank.setHealth(tank.getMaxHealth()); //Leben restored
                tank.getItem().setVisible(false); //temporarily take item from the map
                tank.setItem(null);

                break;

            case 2: //FIREPOWERITEM WAS PICKED UP

                System.out.println("Enable tank firepower mode");
                int dummyFirepower = tank.getFirepower(); //alten firepower wert für gleich speichern
                tank.setFirepower(6000); //hulk modus
                tank.getItem().setVisible(false); //temporarily take item from the map


                while (true){

                    long now = System.currentTimeMillis() /1000;
                    long elapsed = now - startTime;

                    if (elapsed >= time){ //10 sekunden um

                        System.out.println("Firepower mode over");
                        tank.setFirepower(dummyFirepower); //restore old firepower after skill finished
                        tank.getItem().setVisible(true); //put item back on map for everyone
                        tank.setItem(null);
                        break;

                    }

                    try {

                        Thread.sleep(100);

                    } catch (InterruptedException e){

                        //egal
                    }

                }

                break;

            case 3: //SPECIALSHOTITEM WAS PICKED UP

                tank.setSchusstyp(6);
                break;

        }


    }

}
