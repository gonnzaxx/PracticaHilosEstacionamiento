package Estacionamiento;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Estacionamiento {

    private static final int CAPACIDAD_MAXIMA = 5;
    private Semaphore semaforo =  new Semaphore(CAPACIDAD_MAXIMA, true);
    private final ArrayList<Coche> cochesAparcados = new ArrayList<Coche>();
    private final Set<String> historico = new HashSet<>(); // guardar quien ha aparcado

    public boolean entrar (Coche coche) {

        try{
            if(coche.esVip()){
                if(semaforo.tryAcquire()){
                    synchronized(cochesAparcados) {
                        cochesAparcados.add(coche);
                        historico.add(coche.getNombre());
                        System.out.println(coche + " ha entrado");
                    }
                    return true;
                }

                if(desalojarCocheNormal(coche)){
                    synchronized (cochesAparcados) {
                        cochesAparcados.add(coche);
                        historico.add(coche.getNombre());
                        System.out.println(coche +  " entra desalojando a un coche normal.");
                    }
                    return true;
                }

                //en el caso de que haya 5 VIP ya aparcados
                if (semaforo.tryAcquire(5, TimeUnit.SECONDS)) {
                    synchronized (cochesAparcados) {
                        cochesAparcados.add(coche);
                        historico.add(coche.getNombre());
                        System.out.println(coche + " entra tras esperar.");
                    }
                    return true;
                }
                return false;

            }else {
                if (semaforo.tryAcquire(5, TimeUnit.SECONDS)) {
                    synchronized (cochesAparcados) {
                        cochesAparcados.add(coche);
                        historico.add(coche.getNombre());
                        System.out.println(coche + " entra.");
                    }
                    return true;
                }
                return false;
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public void salir (Coche coche) {
        synchronized (cochesAparcados) {
            if (cochesAparcados.remove(coche)) {
                System.out.println(coche + " sale del parking.");
                semaforo.release();
            }
        }
    }

    public boolean desalojarCocheNormal(Coche cocheVip){

        synchronized (cochesAparcados) {
            for (int i = 0; i < cochesAparcados.size(); i++) {
                Coche c = cochesAparcados.get(i);
                if (!c.esVip()) {
                    System.out.println(c + " es desalojado por " + cocheVip + ".");
                    cochesAparcados.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public int plazasOcupadas() {
        synchronized (cochesAparcados) {
            return cochesAparcados.size();
        }
    }

    public int totalQueAparcaronAlgunaVez() {
        synchronized (cochesAparcados) {
            return historico.size();
        }
    }
}
