import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        // Crear armamentos

        ArmamentoEspecial arma1 = new ArmamentoEspecial();
        arma1.setNombre("Lanzallamas");
        arma1.setEfecto("ataque");
        arma1.setValor(15);
        arma1.setProbabilidad(30);

        ArmamentoEspecial arma2 = new ArmamentoEspecial();
        arma2.setNombre("Escudo simbiotico");
        arma2.setEfecto("defensa");
        arma2.setValor(20);
        arma2.setProbabilidad(40);

        ArmamentoEspecial arma3 = new ArmamentoEspecial();
        arma3.setNombre("Coraza venenosa");
        arma3.setEfecto("daño");
        arma3.setValor(10);
        arma3.setProbabilidad(30);


        // crear Pals
        Pal pal1 = new Pal();
        pal1.setNombre("Flarion");
        pal1.setTipo("Fuego");
        pal1.setAtaque(50);
        pal1.setDefensa(30);
        pal1.setArmamentoEspecial(arma1);

        Pal pal2 = new Pal();
        pal2.setNombre("Aquos");
        pal2.setTipo("Agua");
        pal2.setAtaque(45);
        pal2.setDefensa(35);
        pal2.setArmamentoEspecial(arma2);

        Pal pal3 = new Pal();
        pal3.setNombre("Sylven");
        pal3.setTipo("Planta");
        pal3.setAtaque(40);
        pal3.setDefensa(40);
        pal3.setArmamentoEspecial(arma3);


        Pal pal4 = new Pal();
        pal4.setNombre("Voltix");
        pal4.setTipo("Electrico");
        pal4.setAtaque(50);
        pal4.setDefensa(25);
        pal4.setArmamentoEspecial(arma1);

        Pal pal5 = new Pal();
        pal5.setNombre("Hydron");
        pal5.setTipo("Agua");
        pal5.setAtaque(45);
        pal5.setDefensa(30);
        pal5.setArmamentoEspecial(arma2);

        Pal pal6 = new Pal();
        pal6.setNombre("Bloom");
        pal6.setTipo("Planta");
        pal6.setAtaque(40);
        pal6.setDefensa(35);
        pal6.setArmamentoEspecial(arma3);


        // Crear entrenadores

        Entrenador entrenador1 = new Entrenador();
        Entrenador entrenador2 = new Entrenador();

        System.out.println("=== BATALLA DE PAL ===");

        System.out.print("Nombre del entrenador 1: ");
        entrenador1.setNombre(teclado.nextLine());

        System.out.print("Nombre del entrenador 2: ");
        entrenador2.setNombre(teclado.nextLine());


        // Asignar los Pal

        entrenador1.setPal1(pal1);
        entrenador1.setPal2(pal2);
        entrenador1.setPal3(pal3);

        entrenador2.setPal1(pal4);
        entrenador2.setPal2(pal5);
        entrenador2.setPal3(pal6);


        // crear el combate

        Combate combate = new Combate();

        combate.setEntrenador1(entrenador1);
        combate.setEntrenador2(entrenador2);


        // mostrar Pals

        System.out.println();
        System.out.println(
                "Pal de " + entrenador1.getNombre());

        System.out.println(
                "1. " + entrenador1.getPal1().getNombre());

        System.out.println(
                "2. " + entrenador1.getPal2().getNombre());

        System.out.println(
                "3. " + entrenador1.getPal3().getNombre());


        System.out.println();

        System.out.println(
                "Pal de " + entrenador2.getNombre());

        System.out.println(
                "1. " + entrenador2.getPal1().getNombre());

        System.out.println(
                "2. " + entrenador2.getPal2().getNombre());

        System.out.println(
                "3. " + entrenador2.getPal3().getNombre());


        // 3 rondas

        for (int ronda = 1; ronda <= 3; ronda++) {

            System.out.println();
            System.out.println("-----------");
            System.out.println("RONDA " + ronda);
            System.out.println("-----------");


            // Entrenador 1

            System.out.println(
                    entrenador1.getNombre()
                    + ", seleccione su Pal:");

            System.out.println("1. "
                    + entrenador1.getPal1().getNombre());

            System.out.println("2. "
                    + entrenador1.getPal2().getNombre());

            System.out.println("3. "
                    + entrenador1.getPal3().getNombre());

            int opcion1 = teclado.nextInt();

            Pal seleccionado1 =
                    entrenador1.seleccionarPal(opcion1);


            // Entrenador 2

            System.out.println(
                    entrenador2.getNombre()
                    + ", seleccione su Pal:");

            System.out.println("1. "
                    + entrenador2.getPal1().getNombre());

            System.out.println("2. "
                    + entrenador2.getPal2().getNombre());

            System.out.println("3. "
                    + entrenador2.getPal3().getNombre());

            int opcion2 = teclado.nextInt();

            Pal seleccionado2 =
                    entrenador2.seleccionarPal(opcion2);


            // Decidir la acción

            System.out.println();
            System.out.println(
                    entrenador1.getNombre()
                    + ":");

            System.out.println("1. Atacar");
            System.out.println("2. Usar armamento especial");

            int accion1 = teclado.nextInt();


            System.out.println();
            System.out.println(
                    entrenador2.getNombre()
                    + ":");

            System.out.println("1. Atacar");
            System.out.println("2. Usar armamento especial");

            int accion2 = teclado.nextInt();


            // Usar habilidades

            if (accion1 == 2) {
                seleccionado1.usarArmamento(seleccionado2);
            }

            if (accion2 == 2) {
                seleccionado2.usarArmamento(seleccionado1);
            }


            // Calculo de ataques

            int ataque1 =
                    seleccionado1.calcularAtaque(seleccionado2);

            int ataque2 =
                    seleccionado2.calcularAtaque(seleccionado1);


            System.out.println();
            System.out.println(
                    seleccionado1.getNombre()
                    + " obtuvo un ataque total de: "
                    + ataque1);

            System.out.println(
                    seleccionado2.getNombre()
                    + " obtuvo un ataque total de: "
                    + ataque2);

			
			// Resultado

            int resultado =
                    combate.jugarRonda(
                            seleccionado1,
                            seleccionado2);

            if (resultado == 1) {

                System.out.println(
                        "Gana la ronda "
                        + entrenador1.getNombre());

            } else if (resultado == 2) {

                System.out.println(
                        "Gana la ronda "
                        + entrenador2.getNombre());

            } else {

                System.out.println(
                        "La ronda termino en empate.");
            }
        }

        System.out.println();
        System.out.println("-------------------");
        System.out.println("RESULTADO FINAL");
        System.out.println("-------------------");

        System.out.println(
                entrenador1.getNombre()
                + ": "
                + entrenador1.getRondasGanadas()
                + " rondas ganadas.");

        System.out.println(
                entrenador2.getNombre()
                + ": "
                + entrenador2.getRondasGanadas()
                + " rondas ganadas.");


        int ganadorFinal =
                combate.mostrarResultadoFinal();


        if (ganadorFinal == 1) {

            System.out.println(
                    "El ganador es "
                    + entrenador1.getNombre());

        } else if (ganadorFinal == 2) {

            System.out.println(
                    "El ganador es "
                    + entrenador2.getNombre());

        } else {

            System.out.println(
                    "El combate termino en empate.");
        }


        teclado.close();
    }
}