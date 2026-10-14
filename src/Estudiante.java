public class Estudiante {
    private int id, asist;
    private String nombre;
    private double nota;

    public Estudiante(int id, double nota, int asist, String nombre) {
        this.id = id;
        setNombre(nombre);
        setNota(nota);
        setAsist(asist);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        if (nota < 0 || nota > 100) {
            throw new IllegalArgumentException("Error la nota debe estar entre 0 y 100");
        }
        this.nota = nota;
    }

    public int getAsist() {
        return asist;
    }

    public void setAsist(int asist) {
        if (asist < 0 || asist > 100) {
            throw new IllegalArgumentException("Error el porciento de asistencia debe estar entre 0 y 100");
        }
        this.asist = asist;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Error: el nombre no puede estar vacio");
        }
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Estudiante: id = " + id + ", nombre = " + nombre + ", nota = " + nota + ", asistencia = " + asist
                + "%.";
    }
}
