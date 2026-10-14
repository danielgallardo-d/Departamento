import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Departamento depa = new Departamento();
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        System.out.println("Departamento de Informatica - Gestion de Notas");
        System.out.println("=============================================");

        while (!salir) {
            mostrarMenu();
            if (!sc.hasNextInt()) {
                System.out.println("Tienes que introducir un numero entre 1 y 8");
                sc.next();
                continue;
            }
            int opc = sc.nextInt();
            sc.nextLine();

            switch (opc) {
                case 1:
                    agregarEstudiante(depa, sc);
                    break;
                case 2:
                    verAprobados(depa, sc);
                    break;
                case 3:
                    verPromedio(depa);
                    break;
                case 4:
                    ajustarNotas(depa);
                    break;
                case 5:
                    verMayorAsistencia(depa);
                    break;
                case 6:
                    eliminarReprobados(depa, sc);
                    break;
                case 7:
                    salir = true;
                    break;
                case 8:
                    ejecutarPruebas();
                    break;
                default:
                    System.out.println("Numero incorrecto, tiene que ser un numero entre 1 y 8");
            }
        }
        System.out.println("Nos vemos luego.");
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\nElija la opcion que desee:");
        System.out.println("1: Agregar estudiante");
        System.out.println("2: Ver estudiantes aprobados");
        System.out.println("3: Ver promedio general");
        System.out.println("4: Ajustar notas de los estudiantes por asistencia");
        System.out.println("5: Ver estudiante con mayor asistencia");
        System.out.println("6: Eliminar estudiantes reprobados");
        System.out.println("7: Salir");
        System.out.println("8: Ejecutar pruebas del sistema");
    }

    private static double leerDouble(Scanner sc, String mensaje, double minimo, double maximo) {
        double valor;
        while (true) {
            System.out.println(mensaje);
            if (sc.hasNextDouble()) {
                valor = sc.nextDouble();
                sc.nextLine();
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
                System.out.println("Tiene que ser un numero entre " + formatear(minimo) + " y " + formatear(maximo));
            } else {
                System.out.println("Tiene que introducir un numero");
                sc.next();
            }
        }
    }

    private static String leerTexto(Scanner sc, String mensaje) {
        System.out.println(mensaje);
        String texto = sc.nextLine().trim();
        while (texto.isEmpty()) {
            System.out.println("El texto no puede estar vacio");
            texto = sc.nextLine().trim();
        }
        return texto;
    }

    private static String formatear(double valor) {
        if (valor == (int) valor) {
            return String.valueOf((int) valor);
        }
        return String.valueOf(valor);
    }

    private static void agregarEstudiante(Departamento depa, Scanner sc) {
        String nombre = leerTexto(sc, "Vamos a agregar un estudiante\nIntroduce el nombre del estudiante");
        double nota = leerDouble(sc, "Introduce la nota del estudiante", 0, 100);
        int asistencia = (int) leerDouble(sc, "Introduce el porciento de asistencia del estudiante", 0, 100);
        try {
            depa.agregar(nota, asistencia, nombre);
            System.out.println("Estudiante agregado con exito");
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo agregar el estudiante: " + e.getMessage());
        }
    }

    private static void verAprobados(Departamento depa, Scanner sc) {
        double notaMinima = leerDouble(sc, "Introduzca la nota minima para aprobar", 1, 100);
        try {
            Elarraylist<String> aprobados = depa.estudiantesAprobados(notaMinima);
            if (aprobados.size() == 0) {
                System.out.println("No hay estudiantes que superen la nota minima");
            } else {
                System.out.println("Lista de aprobados:");
                for (int i = 0; i < aprobados.size(); i++) {
                    System.out.println("- " + aprobados.get(i));
                }
            }
        } catch (Exception e) {
            // la firma del metodo declara "throws Exception"; ademas puede lanzar
            // NullPointerException (lista vacia) o IllegalArgumentException (nota fuera de
            // rango)
            System.out.println(e.getMessage());
        }
    }

    private static void verPromedio(Departamento depa) {
        try {
            System.out.println("El promedio general es: " + depa.promedioGeneral());
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void ajustarNotas(Departamento depa) {
        try {
            int ajustados = depa.ajustarNotasPorAsistencia();
            System.out.println("Se le ha ajustado la nota a " + ajustados + " estudiantes.");
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void verMayorAsistencia(Departamento depa) {
        try {
            Estudiante mayor = depa.estudianteMayorAsistencia();
            System.out.println("El estudiante con mayor asistencia es: " + mayor);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void eliminarReprobados(Departamento depa, Scanner sc) {
        double notaCorte = leerDouble(sc, "Introduzca la nota minima para reprobar", 1, 100);
        try {
            int eliminados = depa.eliminarEstudiantesReprobados(notaCorte);
            System.out.println("Se ha eliminado a " + eliminados + " estudiantes.");
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // ==================== OPCION 8: PRUEBAS DEL SISTEMA ====================
    // Pruebas funcionales sencillas: comprueban los 5 metodos del enunciado
    // con flujos positivos, casos negativos y escenarios de borde.

    private static int pruebasCorrectas;
    private static int pruebasTotales;

    private static void ejecutarPruebas() {
        pruebasCorrectas = 0;
        pruebasTotales = 0;

        System.out.println("\n================= PRUEBAS DEL SISTEMA =================");
        System.out.println("Cada prueba crea su propio Departamento: tus datos no se tocan.");
        System.out.println("Rango del enunciado: notas entre 40 y 95, asistencia entre 70 y 100.\n");

        probarInsercion();
        probarEstudiantesAprobados();
        probarPromedioGeneral();
        probarAjustePorAsistencia();
        probarEstudianteMayorAsistencia();
        probarEliminarReprobados();
        probarRegresiones();

        System.out.println("\n=======================================================");
        System.out.println("Resultado: " + pruebasCorrectas + " de " + pruebasTotales + " pruebas correctas");
        if (pruebasCorrectas == pruebasTotales) {
            System.out.println("TODO FUNCIONA CORRECTAMENTE");
        } else {
            System.out.println("HAY PRUEBAS FALLIDAS: revisa el codigo");
        }
        System.out.println("=======================================================\n");
    }

    private static void comprobar(String descripcion, boolean correcto) {
        pruebasTotales++;
        if (correcto) {
            pruebasCorrectas++;
            System.out.println("  [CORRECTO] " + descripcion);
        } else {
            System.out.println("  [FALLO]    " + descripcion);
        }
    }

    /**
     * Departamento con los datos que pide el enunciado:
     * notas entre 40 y 95 y asistencia entre 70 y 100.
     */
    private static Departamento departamentoDePrueba() {
        Departamento d = new Departamento();
        d.agregar(40, 70, "Rita"); // no aprueba
        d.agregar(65, 80, "Luis"); // aprueba
        d.agregar(95, 95, "Eva"); // aprueba
        d.agregar(50, 100, "Ivan"); // no aprueba
        return d;
    }

    private static void probarInsercion() {
        System.out.println("1) Insercion de estudiantes");

        Departamento d = departamentoDePrueba();
        // 40 + 65 + 95 + 50 = 250 ; 250 / 4 = 62.5
        comprobar("Se insertan 4 estudiantes y quedan registrados",
                Math.abs(d.promedioGeneral() - 62.5) < 0.0001);

        boolean notaMala = false;
        try {
            new Departamento().agregar(150, 80, "Ramiro"); // nota fuera de 0-100
        } catch (IllegalArgumentException e) {
            notaMala = true;
        }
        comprobar("NEGATIVO: una nota fuera de 0-100 lanza IllegalArgumentException", notaMala);

        boolean nombreVacio = false;
        try {
            new Departamento().agregar(50, 80, "   "); // nombre en blanco
        } catch (IllegalArgumentException e) {
            nombreVacio = true;
        }
        comprobar("NEGATIVO: un nombre vacio lanza IllegalArgumentException", nombreVacio);
    }

    private static void probarEstudiantesAprobados() {
        System.out.println("\n2) EstudiantesAprobados(notaMinima)");

        Departamento d = departamentoDePrueba();

        try {
            Elarraylist<String> res = d.estudiantesAprobados(60);
            comprobar("POSITIVO: con nota minima 60 aprueban Luis y Eva",
                    res.size() == 2 && res.get(0).equals("Luis") && res.get(1).equals("Eva"));
        } catch (Exception e) {
            comprobar("POSITIVO: con nota minima 60 aprueban Luis y Eva", false);
        }

        try {
            Elarraylist<String> res = d.estudiantesAprobados(65);
            comprobar("BORDE: quien tiene EXACTAMENTE la nota minima aprueba (>=)",
                    res.size() == 2 && res.get(0).equals("Luis"));
        } catch (Exception e) {
            comprobar("BORDE: quien tiene EXACTAMENTE la nota minima aprueba (>=)", false);
        }

        try {
            Elarraylist<String> res = d.estudiantesAprobados(96);
            comprobar("NEGATIVO: nadie llega a 96 -> lista vacia (elemento no encontrado)",
                    res.size() == 0);
        } catch (Exception e) {
            comprobar("NEGATIVO: nadie llega a 96 -> lista vacia (elemento no encontrado)", false);
        }

        boolean excepcionVacia = false;
        try {
            new Departamento().estudiantesAprobados(60); // departamento sin estudiantes
        } catch (NullPointerException e) {
            excepcionVacia = true;
        } catch (Exception e) {
            excepcionVacia = false;
        }
        comprobar("EXCEPCION: departamento vacio lanza NullPointerException", excepcionVacia);

        boolean excepcionNota = false;
        try {
            d.estudiantesAprobados(150); // nota fuera del rango admitido
        } catch (IllegalArgumentException e) {
            excepcionNota = true;
        } catch (Exception e) {
            excepcionNota = false;
        }
        comprobar("EXCEPCION: nota minima fuera de rango lanza IllegalArgumentException", excepcionNota);
    }

    private static void probarPromedioGeneral() {
        System.out.println("\n3) PromedioGeneral()");

        Departamento d = departamentoDePrueba();
        comprobar("POSITIVO: media aritmetica de 40, 65, 95 y 50 -> 62.5",
                Math.abs(d.promedioGeneral() - 62.5) < 0.0001);

        Departamento d2 = new Departamento();
        d2.agregar(80, 70, "Sola");
        comprobar("BORDE: con un unico estudiante el promedio es su nota",
                Math.abs(d2.promedioGeneral() - 80.0) < 0.0001);

        boolean excepcion = false;
        try {
            new Departamento().promedioGeneral(); // lista vacia
        } catch (NullPointerException e) {
            excepcion = true;
        }
        comprobar("EXCEPCION: promedio con lista vacia lanza NullPointerException", excepcion);
    }

    private static void probarAjustePorAsistencia() {
        System.out.println("\n4) AjustarNotasPorAsistencia()");

        Departamento d = new Departamento();
        d.agregar(96, 95, "Eva"); // 96 + 5 = 101 -> debe quedar en 100
        d.agregar(80, 80, "Rita"); // asistencia < 90 -> no cambia
        int ajustados = d.ajustarNotasPorAsistencia();
        // si todo va bien: (100 + 80) / 2 = 90
        comprobar("POSITIVO: suma 5 a asistencia>=90 y la nota 96 toca el techo 100",
                ajustados == 1 && Math.abs(d.promedioGeneral() - 90.0) < 0.0001);

        Departamento d2 = new Departamento();
        d2.agregar(50, 90, "Ana"); // exactamente 90 -> si bonifica (55)
        d2.agregar(50, 89, "Luis"); // 89 -> no bonifica (50)
        int ajustados2 = d2.ajustarNotasPorAsistencia();
        comprobar("BORDE: asistencia 90 exacta si bonifica y 89 no",
                ajustados2 == 1 && Math.abs(d2.promedioGeneral() - 52.5) < 0.0001);

        boolean excepcion = false;
        try {
            new Departamento().ajustarNotasPorAsistencia(); // lista vacia
        } catch (NullPointerException e) {
            excepcion = true;
        }
        comprobar("EXCEPCION: ajuste con lista vacia lanza NullPointerException", excepcion);
    }

    private static void probarEstudianteMayorAsistencia() {
        System.out.println("\n5) BuscarEstudianteConMayorAsistencia()");

        Departamento d = departamentoDePrueba();
        Estudiante mayor = d.estudianteMayorAsistencia();
        comprobar("POSITIVO: devuelve a Ivan, que tiene el 100% de asistencia",
                mayor != null && mayor.getNombre().equals("Ivan") && mayor.getAsist() == 100);

        Departamento d2 = new Departamento();
        d2.agregar(60, 90, "Ana");
        d2.agregar(70, 90, "Luis");
        Estudiante mayor2 = d2.estudianteMayorAsistencia();
        comprobar("BORDE: con empate devuelve un estudiante con el maximo (90%)",
                mayor2 != null && mayor2.getAsist() == 90);

        Departamento d3 = new Departamento();
        d3.agregar(50, 0, "Ana");
        d3.agregar(60, 0, "Luis");
        Estudiante mayor3 = d3.estudianteMayorAsistencia();
        comprobar("REGRESION: todos con 0% devuelve el primero y nunca null",
                mayor3 != null && mayor3.getAsist() == 0 && mayor3.getNombre().equals("Ana"));

        boolean excepcion = false;
        try {
            new Departamento().estudianteMayorAsistencia(); // lista vacia
        } catch (NullPointerException e) {
            excepcion = true;
        }
        comprobar("EXCEPCION: lista vacia lanza NullPointerException", excepcion);
    }

    private static void probarEliminarReprobados() {
        System.out.println("\n6) EliminarEstudiantesReprobados(notaCorte)");

        Departamento d = departamentoDePrueba(); // notas: 40, 65, 95, 50
        int eliminados = d.eliminarEstudiantesReprobados(60);
        // se van Rita(40) e Ivan(50); quedan Luis(65) y Eva(95) -> media 80
        comprobar("POSITIVO: con corte 60 se eliminan 2 y sobreviven Luis y Eva",
                eliminados == 2 && Math.abs(d.promedioGeneral() - 80.0) < 0.0001);

        Departamento d2 = departamentoDePrueba();
        int eliminados2 = d2.eliminarEstudiantesReprobados(1);
        comprobar("BORDE: con corte 1 nadie se elimina y el promedio no cambia",
                eliminados2 == 0 && Math.abs(d2.promedioGeneral() - 62.5) < 0.0001);

        boolean excepcionRango = false;
        try {
            d2.eliminarEstudiantesReprobados(150); // fuera del rango admitido
        } catch (IllegalArgumentException e) {
            excepcionRango = true;
        }
        comprobar("EXCEPCION: nota de corte fuera de rango lanza IllegalArgumentException", excepcionRango);

        boolean excepcionVacia = false;
        try {
            new Departamento().eliminarEstudiantesReprobados(50); // lista vacia
        } catch (NullPointerException e) {
            excepcionVacia = true;
        }
        comprobar("EXCEPCION: lista vacia lanza NullPointerException", excepcionVacia);
    }

    /** Comprueba que los bugs ya corregidos no vuelvan a aparecer. */
    private static void probarRegresiones() {
        System.out.println("\n7) Regresiones (los bugs corregidos no deben volver)");

        // Bug 1: remove() reventaba con ArrayIndexOutOfBoundsException si la lista
        // estaba exactamente llena. La capacidad inicial es 10, asi que con 10
        // alumnos la lista queda llena y el primer remove pone a prueba el arreglo.
        Departamento d = new Departamento();
        for (int i = 0; i < 10; i++) {
            d.agregar(40 + i * 5, 70 + i * 3, "Estudiante " + i);
        }
        // notas: 40, 45, ... 85 asistencias: 70, 73, ... 97
        boolean correcto = false;
        try {
            int borrados = d.eliminarEstudiantesReprobados(50); // se van los de 40 y 45
            correcto = (borrados == 2);
        } catch (Exception e) {
            correcto = false;
        }
        comprobar("REGRESION: eliminar con la lista exactamente llena (10 alumnos) no falla", correcto);

        // Bug 3: el id se repetia al agregar un alumno despues de eliminar.
        Departamento d2 = new Departamento();
        d2.agregar(40, 70, "Ana"); // id 1
        d2.agregar(95, 80, "Luis"); // id 2
        d2.eliminarEstudiantesReprobados(50); // se va Ana
        d2.agregar(70, 100, "Eva"); // id 3 (con el bug seria 2, repetido con Luis)
        Estudiante nuevo = d2.estudianteMayorAsistencia(); // Eva tiene el 100%
        comprobar("REGRESION: el id del alumno nuevo es 3 y no se repite con el de Luis",
                nuevo.getNombre().equals("Eva") && nuevo.getId() == 3);
    }
}
