# Gestion Academica EduControl

## Integrantes:

- Pedro Antonio Palacios Gonzalez
- Jesus Daniel Bruguera Gallardo
- Leonardo Roque Moreno
- Caleb Blanco Morejon
- Chritian M. Febles Suarez


## Problematica:

El departamento de informatica tiene que procesar las notas de sus estudiantes registrados. Estos estudiantes tienen los siguientes atributos:

- Id 
- Nombre
- Nota 
- Porciento de asistencia 

El problema nos pide un programa que cumpla las siguientes tareas:

- Calcular el promedio general de notas del departamento
- Listar el nombre de los aprobados y eliminar los desaprobados (todo de acuerdo a una nota minima que el departamento determina) 
- Mostrar los datos del estudiante con mayor asistencia 
- Ajustar las notas de los estudiantes que tengan mas del 90% de asistencia sumandole 5 puntos en su nota no pasando la nota de 100 puntos.

## Métodos implementados

| Método | Qué hace | Casos especiales |
|---|---|---|
| `estudiantesAprobados(notaMinima)` | Devuelve los nombres con nota >= mínima | lista vacía, nadie aprueba |
| `promedioGeneral()` | Media aritmética de las notas | lista vacía |
| `ajustarNotasPorAsistencia()` | +5 si asistencia >= 90 %, tope 100 | asistencia 90 exacta, nota 95 |
| `estudianteMayorAsistencia()` | Estudiante con mayor % | empates, todos 0 %, lista vacía |
| `eliminarEstudiantesReprobados(corte)` | Borra notas < corte y devuelve cuántos | corte 0, nadie eliminado |

## Casos de prueba ejecutados

Las pruebas se encuentran dentro del propio programa, en la **opción 8 del menú** (`App.java`,
método `ejecutarPruebas()`). Al elegir la opción 8 se lanzan **24 pruebas** y se imprime un
resultado final. Cada prueba crea su **propio `Departamento`**, así que los datos introducidos por
el menú no se tocan ni se borran.

Las pruebas se apoyan únicamente en los métodos públicos de `Departamento`, es decir, se comportan
como lo haría un usuario externo del sistema.

### Datos de partida

Para respetar el rango que pide el enunciado (notas entre 40 y 95 y asistencia entre 70 % y 100 %),
la mayoría de las pruebas arrancan con estos cuatro estudiantes:

| Estudiante | Nota | Asistencia | ¿Aprueba con mínima 60? |
|------------|------|------------|--------------------------|
| Rita       | 40   | 70 %       | No                       |
| Luis       | 65   | 80 %       | Sí                       |
| Eva        | 95   | 95 %       | Sí                       |
| Ivan       | 50   | 100 %      | No                       |

### Qué comprueba cada bloque

| Bloque | Pruebas | Qué se comprueba |
|--------|---------|------------------|
| 1) Inserción | 3 | **Positivo:** se insertan los 4 estudiantes (se comprueba con el promedio 62,5). **Negativos:** una nota de 150 y un nombre en blanco deben rechazarse con `IllegalArgumentException`. |
| 2) `estudiantesAprobados` | 5 | **Positivo:** con mínima 60 aprueban solo Luis y Eva. **Borde:** quien tiene *exactamente* la nota mínima aprueba (se usa `>=`, no `>`). **Negativo:** con mínima 96 nadie aprueba y devuelve una lista vacía («elemento no encontrado»). **Excepciones:** departamento vacío y nota mínima fuera de rango. |
| 3) `promedioGeneral` | 3 | **Positivo:** la media de 40, 65, 95 y 50 es 62,5. **Borde:** con un solo estudiante, el promedio es su propia nota. **Excepción:** lista vacía. |
| 4) `ajustarNotasPorAsistencia` | 3 | **Positivo:** solo se bonifica a quien tiene 90 % o más y la nota 96 toca el techo de 100 (no puede quedar 101). **Borde:** la asistencia 90 exacta sí bonifica y la de 89 no. **Excepción:** lista vacía. |
| 5) `estudianteMayorAsistencia` | 4 | **Positivo:** devuelve a Ivan con el 100 %. **Borde:** con empate devuelve un estudiante con el máximo. **Regresión:** si todos tienen 0 % devuelve el primero y nunca `null`. **Excepción:** lista vacía. |
| 6) `eliminarEstudiantesReprobados` | 4 | **Positivo:** con corte 60 se eliminan 2 y sobreviven Luis y Eva. **Borde:** con corte 1 nadie se elimina. **Excepciones:** corte fuera de rango y lista vacía. |
| 7) Regresiones | 2 | Comprueban que los bugs ya corregidos no vuelvan: borrado con la lista exactamente llena (10 alumnos) y `id` que no se repite tras eliminar. |

### Resultado obtenido

```
Resultado: 24 de 24 pruebas correctas
TODO FUNCIONA CORRECTAMENTE
```

Si alguna comprobación fallara, la opción 8 imprime `[FALLO]` junto a la descripción y el resumen
final avisa con «HAY PRUEBAS FALLIDAS: revisa el codigo», de modo que es fácil saber exactamente
qué ha dejado de funcionar.

## Estructura de datos empleada

Utilizamos el **Arraylist** para la estructura de este trabajo, es una estructura de datos como un array normal lo que este tiene una capacidad dinamica porque funciona con un array interno de capacidad fija que cuando se va quedando sin espacio para añadir mas elementos cambia automaticamente su capacidad para ser mas grande y poder añadir mas alementos al final sin problema, dandole asi la ventaja de insertar y eliminar al final de forma rapida sin tener que ir ajustando la capacidad siempre, lo que le da la desventaja de consumir mas en memoria. Tiene ademas como ventaja el acceso rapido por indice y como desventaja la eliminacion o insercion al principio o en el intermedio es lenta porque tiene que ajustar todos los elementos restantes.  

## Estructura del proyecto

```text
.
├── src/
│   ├── App.java  
│   ├── Departamento.java
│   ├── Elarraylist.java   
│   ├── Estudiante.java         
├── presentacion/
│   └── 
└── README.md
```

## Uso de IA

En este proyecto el uso de inteligencia artificial solo se limito a utilizar el agente de IA OPENCODE con el LLM MiMo-V2.6-Flash Free para:

- **Testing:** Testear el codigo para detectar bugs, errores y excepciones.
- **Revisar la logica:**  Revisar la concordancia del codigo con la problematica
- **Revisar la calidad del codigo:** Revisar de principio a fin la limpieza del codigo para asi aconsejarnos que cambios hacer en el codigo para que se viera mas limpio y simple
- **Darnos las sugerencias necesarias:** Pedirle al agente que creara un archivo [Sugerencias.md] en el que nos aconsejaba que cambios hacer y que bugs existian que hay que solucionar.
- **Crear los test en el main** Se le pidio al agente nos creara para el main un case 8 con los test para el codigo.

Esto fue lo que el agente vio y nos sugirio cambiar en nuestro trabaja:

- **Detección de bugs:** Detecto un bug simple en `Elarraylist.remove()` y uno `estudianteMayorAsistencia()`.
- **Refactorización:** Propuesta de simplificación de `ajustarNotasPorAsistencia()`.
- **Cambio total del Main y creacion de Test:** Sugirio hacer cambios significativos en el main para tener un codigo mas limpio y menos repitivo, ademas nos creo el codigo para los test.  