import java.util.Arrays;

public class Elarraylist<T> {
    private int capacidad = 10;
    private int tamano;
    private Object[] elements;

    public Elarraylist() {
        this.elements = new Object[capacidad];
        this.tamano = 0;
    }

    public int size() {
        return tamano;
    }

    public void add(T o) {
        if (o == null) {
            throw new NullPointerException("Objeto vacio");
        }
        verificarcapacidad();
        elements[tamano] = o;
        tamano++;
    }

    public void verificarcapacidad() {
        if (tamano == elements.length) {
            int newcapacidad = tamano + tamano / 2;
            elements = Arrays.copyOf(elements, newcapacidad);
        }
    }

    @SuppressWarnings("unchecked")
    public T get(int indice) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("Indice fuera del tamano del arraylist.");
        }
        return (T) elements[indice];
    }

    public void remove(int indice) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("Indice fuera del tamano del arraylist.");
        }
        for (int i = indice; i < tamano - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[tamano - 1] = null;
        tamano--;
    }

    @Override
    public String toString() {
        if (tamano == 0) {
            return "[]";
        }
        StringBuilder fin = new StringBuilder();
        for (int i = 0; i < tamano; i++) {
            fin.append(elements[i]);
            fin.append("\n");
        }
        return fin.toString();
    }
}
