


package buscaminas;
import java.util.Random;
import java.util.Scanner;

public class Buscaminas {

    
    //creacion del tablero de una mediante constantes
    private static final int FILAS = 5;
    private static final int COLUMNAS = 5;
    private static final int MINAS = 5;

    //creacion de la matriz del juego
    private char[][] tableroVisible;
    private char[][] tableroReal;
    //variable de falso y verdadero el cual determina si se acabo el juego
    private boolean gameOver;

    //clase para crear matriz del buscaminas
    public Buscaminas() {
        
        //asignando las varibles a la matriz de caracteres
        tableroVisible = new char[FILAS][COLUMNAS];
        tableroReal = new char[FILAS][COLUMNAS];
        gameOver = false;

        inicializarTablero();
        colocarMinas();
        actualizarTableroVisible();
    }

    
    //aqui se dibuja la matriz y es void por que va retornar un valor
    private void inicializarTablero() {
        // primer for dibuja las filas
        for (int i = 0; i < FILAS; i++) {
            //dibuja las columnas
            for (int j = 0; j < COLUMNAS; j++) {
                //es lo que quiere que dibuje
                tableroVisible[i][j] = '-';
                tableroReal[i][j] = 'X';
            }
        }
    }

    //colocar las minas de manera aleatoria
    private void colocarMinas() {
        Random random = new Random();//se generara las minas de manera aleatoria
        int minasColocadas =0; 

        //se realizara un ciclo el cual pondra las minas de forma aleatoria
        while (minasColocadas < MINAS) {
            int fila = random.nextInt(FILAS);//se guarda una mina en las filas
            int columna = random.nextInt(COLUMNAS);//se guarda una mina en las columnas

            if (tableroReal[fila][columna] != '*') {
                tableroReal[fila][columna] = '*';
                minasColocadas++;
            }
        }
    }

    //dependiendo del tamaño de la matriz se pondra una mina
    private void actualizarTableroVisible() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                System.out.print(tableroVisible[i][j] + " ");
            }
            System.out.println();
        }
    }

    //revela el tablero, ya con las minas puestas
    private void revelarTablero() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                System.out.print(tableroReal[i][j] + " ");
            }
            System.out.println();
        }
    }

    //aqui inicia el juego, y usamos la libreria scanner para ingresar los datos
    private void jugar() {
        Scanner scanner = new Scanner(System.in);
        
        
        while (!gameOver) {
            System.out.print("Ingrese fila: ");
            int fila = scanner.nextInt();

            System.out.print("Ingrese columna: ");
            int columna = scanner.nextInt();

            //se evaluan diferentes condiciones , en el cual detecta si sobrepasa la matriz 
            if (fila < 0 || fila >= FILAS || columna < 0 || columna >= COLUMNAS) {
                System.out.println("Entrada inválida. Inténtelo de nuevo.");
                continue;
            }

             //entra la coordenada que digita el usuario, y si es una mina KBOOOM explota   
            if (tableroReal[fila][columna] == '*') {
                System.out.println("¡Has golpeado una mina! Fin del juego.");
                revelarTablero();
                gameOver = true;
            } else {
                int minasCercanas = contarMinasCercanas(fila, columna);
                tableroVisible[fila][columna] = (char) (minasCercanas + '0');
                actualizarTableroVisible();

                if (minasCercanas == 0) {
                    revelarCasillasVacias(fila, columna);
                }

                if (esVictoria()) {
                    System.out.println("¡Felicidades! Has ganado el juego.");
                    gameOver = true;
                }
            }
        }
    }

    //
    private int contarMinasCercanas(int fila, int columna) {
        //se crea un contador para registrar cuantas minas hay cercanas
        int contador = 0;
            //marca cuantas minas tienes alrededor
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int nuevaFila = fila + i;
                int nuevaColumna = columna + j;
                
                if (nuevaFila >= 0 && nuevaFila < FILAS && nuevaColumna >= 0 && nuevaColumna < COLUMNAS) {
                    if (tableroReal[nuevaFila][nuevaColumna] == '*') {
                        contador++;
                    }
                }
            }
        }

        return contador;//para regresar el valor
    }

    private void revelarCasillasVacias(int fila, int columna) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int nuevaFila = fila + i;
                int nuevaColumna = columna + j;

                if (nuevaFila >= 0 && nuevaFila < FILAS && nuevaColumna >= 0 && nuevaColumna < COLUMNAS) {
                    if (tableroVisible[nuevaFila][nuevaColumna] == '-') {
                        int minasCercanas = contarMinasCercanas(nuevaFila, nuevaColumna);
                        tableroVisible[nuevaFila][nuevaColumna] = (char) (minasCercanas + '0');
                        if (minasCercanas == 0) {
                            revelarCasillasVacias(nuevaFila, nuevaColumna);
                        }
                    }
                }
            }
        }
    }

    private boolean esVictoria() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (tableroReal[i][j] != '*' && tableroVisible[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Buscaminas juego = new Buscaminas();//instancia dela clase buscaminas
        juego.jugar();//manda a llamar a la clase jugar para mostrarnos los mensajes en panatall
    }
}