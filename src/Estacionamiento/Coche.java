package Estacionamiento;

public class Coche extends Thread{

    private final String nombre;
    private final boolean vip;
    private final Estacionamiento estacionamiento;

    public Coche(String nombre, boolean VIP, Estacionamiento estacionamiento) {
        this.nombre = nombre;
        this.vip = VIP;
        this.estacionamiento = estacionamiento;
    }

    public boolean esVip(){
        return vip;
    }

    public void run(){

        if(estacionamiento.entrar(this)){
            try{
                int tiempoEspera = (int) (Math.random() * 4000 + 2000);
                System.out.println(toString() + " aparcado durante " + tiempoEspera + " segundos.");
                Thread.sleep(tiempoEspera) ;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                estacionamiento.salir(this);
            }
        }else {
            System.out.println(toString() + " no pudo aparcar y se marcha.");
        }

    }

    @Override
    public String toString() {
        String tipo;
        if (vip) {
            tipo = "[VIP] ";
        } else {
            tipo = "[Normal] ";
        }
        return tipo + nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
