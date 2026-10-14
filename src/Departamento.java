public class Departamento {
    private Elarraylist<Estudiante> estudiantes = new Elarraylist<>();
    private int proximoId = 1;

    public void validarlista() throws NullPointerException {
        if (estudiantes.size() == 0) {
            throw new NullPointerException("Todavia no hay estudiantes registrados");
        }
    }

    public void agregar(double nota, int asist, String nombre) {
        Estudiante estudiante = new Estudiante(proximoId, nota, asist, nombre);
        estudiantes.add(estudiante);
        proximoId++;
    }

    // Leonardo
    public Elarraylist<String> estudiantesAprobados(double notaMinima) throws Exception {
        validarlista();
        if (notaMinima < 1 || notaMinima > 100) {
            throw new IllegalArgumentException("Nota Incorrecta, tiene que ser una nota entre 1 y 100");
        }
        Elarraylist<String> aprobados = new Elarraylist<>();
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getNota() >= notaMinima) {
                aprobados.add(estudiantes.get(i).getNombre());
            }
        }
        return aprobados;
    }

    //
    // Crisitian
    public double promedioGeneral() {
        validarlista();
        double suma = 0;
        for (int i = 0; i < estudiantes.size(); i++) {
            suma += estudiantes.get(i).getNota();
        }
        return suma / estudiantes.size();
    }

    public int ajustarNotasPorAsistencia() {
        validarlista();
        int ajustados = 0;
        for (int i = 0; i < estudiantes.size(); i++) {
            Estudiante e = estudiantes.get(i);
            if (e.getAsist() >= 90) {
                e.setNota(Math.min(e.getNota() + 5, 100));
                ajustados++;
            }
        }
        return ajustados;
    }

    //
    // Jesus Daniel
    public Estudiante estudianteMayorAsistencia() {
        validarlista();
        Estudiante mayor = estudiantes.get(0);
        for (int i = 1; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getAsist() > mayor.getAsist()) {
                mayor = estudiantes.get(i);
            }
        }
        return mayor;
    }

    // Caleb
    public int eliminarEstudiantesReprobados(double notaCorte) {
        validarlista();
        if (notaCorte > 100 || notaCorte < 1) {
            throw new IllegalArgumentException("Nota Incorrecta, tiene que ser una nota entre 1 y 100");
        }
        int total = 0;
        for (int i = estudiantes.size() - 1; i >= 0; i--) {
            if (estudiantes.get(i).getNota() < notaCorte) {
                estudiantes.remove(i);
                total++;
            }
        }
        return total;

    }
}
