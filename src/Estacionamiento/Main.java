package Estacionamiento;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Estacionamiento estacionamiento = new Estacionamiento();
        ArrayList<Coche> coches = new ArrayList<>(15);

        for (int i = 0; i <= 15; i++) {
            boolean vip = false;
            if(i % 3 == 0){
                vip = true;
            }
            coches.add(new Coche("Coche " + i, vip, estacionamiento));
        }

        for (Coche c : coches){
            c.start();
        }

        for (Coche c : coches){
            c.join();
        }

        System.out.println("Intentaron aparcar: " + coches.size());
        System.out.println("Consiguieron aparcar (alguna vez): " + estacionamiento.totalQueAparcaronAlgunaVez());
        System.out.println("Plazas ocupadas al final: " + estacionamiento.plazasOcupadas());
    }
}
