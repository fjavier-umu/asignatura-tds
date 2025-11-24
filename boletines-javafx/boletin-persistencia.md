
# Patrón Repositorio y Persistencia en JSON

En esta práctica se pretende estudiar cómo gestionar la persistencia en la arquitectura MVC propuesta. Para ello primero se presentará el patrón repositorio el cúal permite abstraernos de la tecnología de acceso persitente a datos y ofrece una colección de operaciones CRUD (Create - Recovery - Update - Delete) para cada tipo de dato persistente. A continuación se verá como persistir la información completa de un modelo objetual usando la librería Java Jackson para almacenar los datos en formato JSON. 

## Patrón Repositorio

El Patrón Repositorio es un patrón de diseño usado principalmente en aplicaciones que siguen una arquitectura por capas. Su propósito es:

- Abstraer el acceso a datos

- Centralizar las operaciones CRUD (Crear, Leer, Actualizar y Borrar)

- Evitar que la lógica de negocio dependa de detalles de almacenamiento (SQL, NoSQL, archivos, API…)

El Repositorio no es solo una capa CRUD: es un patrón de arquitectura del dominio que surge del Domain-Driven Design (DDD), con el objetivo de representar una colección en memoria de entidades del dominio, aunque realmente estén en una base de datos o un servicio externo.

El patrón permite abstraer a nivel de **infraestructura** (ORMs, drivers JDBC, protocolos, detalles del almacenamiento), persistencia (sentencias SQL, manejo de transacciones) y ubicación de los datos (DB local, microservicios, cache, archivos, API externas), permitiendo que la lógica de negocio puede trabajar como si los objetos ya estuvieran en memoria (el repositorio pretende aparecer como una lista o colección de objetos del dominio).

Sin el patrón repositorio:

- La lógica de negocio tendría que llamar directamente a la base de datos

- Se produciría código duplicado

- Cambiar la implementación del almacenamiento sería costoso y arriesgado

- Se reduce la capacidad de testear con facilidad

El patrón repositorio permite crear una capa intermediaria, los **repositorios**, simplificando la arquitecura en capas y permitiendo que el código de acceso a datos queda encapsulado en clase(s) repositorio.

```
 Capa de Negocio
        ↓
   Repositorio
        ↓
 Fuente de Datos (DB, API, Archivos, etc.)
```

Los componentes que definen la arquitectura de este patrón son:
- **Interfaz del Repositorio**: Define las operaciones (interfaz o contrato)
- **Implementación**: Contiene el acceso real a datos (la clase de implementación que accede a los datos mediante el uso de la tecnología oportuna, en nuestro caso la librería Jackson).

De este modo, las interfaces del repositorio abstraen de las clases concretas de implementación que acceden a los datos, generando (desde el punto de vista del cleinte del repositorio) un acceso transparente e independiente de la tecnología.

Un ejemplo de Repositorio de Productos:

```java
import java.util.List;

public interface RepositorioProducto {
    Producto findById(int id);
    List<Producto> findAll();
    void save(Producto producto);
    void delete(int id);
}
```

```java
import java.util.*;

public class RepositorioProductoEnMemoria implements RepositorioProducto {

    private final Map<Integer, Producto> database = new HashMap<>();

    @Override
    public Producto findById(int id) {
        return database.get(id);
    }

    @Override
    public List<Producto> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public void save(Producto producto) {
        database.put(producto.getId(), producto);
    }

    @Override
    public void delete(int id) {
        database.remove(id);
    }
}
```

Los objetos del dominio manejados mediante repositorios se crean en el dominio (```new Producto(...)```), se persisten mediante el repositorio (es decir, no es la entidad la que se guarda sola), y se recuperan sin exponer detalles técnicos. Con esto, el dominio no conoce ni SQL, ni conexiones, ni frameworks o librerías de persistencia (JPA, Hibernate…).

Una de las principales razones para el uso del patrón Repositorio es que mediante el enfoque de la inversión de la dependencia se consigue que la capa de dominio dependa solo de abstracciones (las interfaces de los repositorios), no de implementaciones concretas.

Los beneficios de usar el patrón Repositorio son:

- **Bajo acoplamiento** :	La lógica de negocio no depende del ORM o de la base de datos concreta.
- **Testeo más sencillo**	: Resultan sencillo reemplazar con repositorios simulados/mock.
- **Reutilización**	: Se centralizan todas las consultas comunes en un solo lugar.
- **Evolución del sistema** :	Facilita el cambio de la tecnología de datos sin afectar a las capas superiores.

A la hora de aplicar el patron Repositorio debemos **evitar meter la lógica compleja dentro del repositorio**. Esta debería permanecer en las clases del dominio, no en las clases del acceso a los datos.

Por otro lado, un error común a la hora de definir las interfaces de los repositorios es hacer repositorios genéricos y solo CRUD. De acuerdo a la metodología DDD (Domain-Driven Design) el repositorio debe expresar el lenguaje del dominio, haciendo explícito lo que es importante en el negocio. Por ejemplo:

```Java
List<Product> findByPriceGreaterThan(double price);

List<Product> findScarceProducts(); 
//define qué significa 'productos escasos'
```

En resumen, el patrón se utiliza para introducir independencia con la tecnología de almacenamienta en una arquitectura organizada en capas (como MVC, arquitectura limpia o hexagonal). Permite además abstraerse de un origen de datos concreto, facilitando tener incluso varios orígenes de datos configurados en el sistema. Por último permite tener centralizada la lógica referente a la persistencia en una capa mediante una interfaz de operaciones CRUD simple y clara. 


## Persistencia en JSON (usando librería Jackson)

Jackson es una librería de Java para Serializar objetos Java → JSON y 
Deserializar JSON → objetos Java. Es actualmente el estándar de facto en Java gracias su facilidad de uso. Además presenta un alto rendimiento y se integra fácilmente con frameworks como Spring o librería como JAX-RS.

Para incluirlo en un proyecto Maven, añadir la dependencia en el ```pom.xml```

```xml
	   <!-- Jackson core -->
	    <dependency>
	        <groupId>com.fasterxml.jackson.core</groupId>
	        <artifactId>jackson-core</artifactId>
	        <version>2.19.4</version>
	    </dependency>
	
	    <!-- Jackson annotations -->
	    <dependency>
	        <groupId>com.fasterxml.jackson.core</groupId>
	        <artifactId>jackson-annotations</artifactId>
	        <version>2.19.4</version>
	    </dependency>
	
	    <!-- Jackson databind -->
	    <dependency>
	        <groupId>com.fasterxml.jackson.core</groupId>
	        <artifactId>jackson-databind</artifactId>
	        <version>2.19.4</version>
	    </dependency>
```

Se puede usar un enfoque simple para serializar y deserializar objetos de un modelo de clases Java **sin usar anotaciones de Jackson**. Simplemente se serializarán/deserializarán las propiedades accesibles mediante los métodos get/set de la clase. Por ejemplo:

```java
public class User {
    private String name;
    private int age;

    public User() { } // Constructor por defecto necesario
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters y Setters
}
```

En este ejemplo, un objeto JSON de tipo ```User``` tendrá la estructura ```{ "name" : "Ana", "age" : 25 }```

Cuestiones a considerar para la implementación:
- Las clases a serializar/deserializar deben tener al menos un cosntructor por defecto (obligatorio).
- Las propiedades con métodos accesores serán las usadas para serializar/deserializar. En el contenido JSON se creará un atributo por cada propiedad

Para serializar/deserializar es necesario instanciar un objeto de tipo ```ObjectMapper```. Este mapper contendrá métodos de serizalización (```writeValue wrtiteValueAsString ..```) y deserialización (```readValue ..```):

```java
ObjectMapper mapper = new ObjectMapper();
User user = new User("Ana", 25);

// Serializando...
String json = mapper.writeValueAsString(user);

String json = "{\"name\":\"Ana\",\"age\":25}";
// DEserializando
User user = mapper.readValue(json, User.class);

```

Con ```writeValue``` podemos serializar y persistir los datos serializados en un fichero:

```
mapper.writeValue(new File("usuario.json"), u);
```

Con ```writeValueAsString``` podemos serializar en una cadena:
```
String json = mapper.writeValueAsString(user);
```

El API contiene más métodos **write** de escritura (serialización) de datos en JSON. Algo similar sucede con los métodos **read**.


Si queremos serializar con formato tabulado/indentado, haremos uso de ```writerWithDefaultPrettyPrinter()```:

```java
String prettyJson = mapper
    .writerWithDefaultPrettyPrinter()
    .writeValueAsString(user);
```

### Uso de anotaciones en Jackson

La librería Jackson incluye varias anotaciones con diferentes propósitos:

| Anotación               | Uso                              |
| ----------------------- | -------------------------------- |
| `@JsonProperty`         | Definir nombre del campo en JSON |
| `@JsonIgnore`           | Ignorar propiedad                |
| `@JsonInclude`          | Excluir valores nulos/vacíos     |
| `@JsonFormat`           | Formatos de fecha                |
| `@JsonCreator`          | Construcción personalizada       |


Por ejemplo, para la clase Producto:

public class Producto {

    @JsonProperty("product_name")
    private String name;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonIgnore
    private double internalCost;
}

### Trabajando con datos de tipo lista

Para la deserialización de listas haremos uso de ```new TypeReference<List<T>>()``` en la declaración del tipo en el método read:

```
String lista = "[{\"name\":\"Ana\",\"age\":25}]";

List<User> users = mapper.readValue(lista, new TypeReference<List<User>>() {}
);
```


### Configurando el mapper de Jackson

- Evitar fallos si los datos JSON contienen propiedades extra no contempladas en la clase del modelo ```DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES```:

Configurar el mapper con el método ```configure```:
```mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);```


Por ejemplo, ```{"name": "Laptop", "price": 1500, "discount": true}``` cuando en la clase producto no está definido ni ```price``` ni ```discount```.

- Si solo queremos serializar valores no nulos usaremos ```JsonInclude.Include.NON_NULL```:

```
mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

```

### Ejercicio 1

Dado el siguiente JSON:

``` json
{
  "title": "El Hobbit",
  "author": "J.R.R. Tolkien",
  "year": 1937
}
```

1. Crea una clase Book.java
2. Lee el JSON y crear el objeto
3. Imprime los datos del libro
4. Serializa nuevamente a JSON en formato tabulado “pretty print”

### Uso de Referencias por ID

Jackson tiene una anotación muy útil, ```JsonIdentityInfo```, la cual permite que objeto pueda ser identificado por su propiedad **id**, y si en otro lugar aparece ese mismo **id**, entonces lo traata como una referencia al mismo objeto. Es decir, no insertar los datos completos del objeto (como agregado) si no que usa la referencia a us ID.

```
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
```

Jackson puede resolver las referencias e incluir solo los IDs esto si los objetos referenciados ya fueron definidos antes:

```java
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
)
public class Categoria {
    private int id;
    private String nombre;

    // Getters y Setters
}
```

```java
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

public class Producto {
    private int codigo;
    private String nombre;
    private Categoria categoria;

    // Getters y Setters
}
```

```json
[
  { "id": 10, "nombre": "Electrónica" },
  { "codigo": 1, "nombre": "Televisor", "categoria": 10 }
]
```
Es decir, Jackson reconstruirá los objetos correctamente y vinculará el Producto con su Categoria.

Se debe tener en cuenta en **@JsonIdentityInfo** que la propiedad usada como ID debe existir y ser única.
Por otro lado, se debe también tener en cuenta que cuando un atributo de una clase se define como ```final```, Jackson siempre lo trata como composición (Agregación), no como referencia independiente.


### Manejo de las relaciones bidireccionales entre clases del modelo

Para el manejo de relaciones bidireccionales, evitando la recursión infinita a la hora de serializar/deserializar, tenemos dos posibles opciones:

#### Opción 1

Uso de referencias a IDs con la notación ```@JsonIdentityInfo``` tal y como se ha explicado previamente.

``` xml
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idAutor"
)
public class Autor { ...
```

``` xml
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idLibro"
)
public class Libro { ...
```


#### Opción 2

Uso de las anotaciones ```@JsonManagedReference``` / ```@JsonBackReference``` :

```java
class Categoria {
    private int id;
    private String nombre;

    @JsonManagedReference
    private List<Producto> productos; ...
}

class Producto {
    private int id;
    private String nombre;

    @JsonBackReference
    private Categoria categoria; ...
}
```

Esta configuración permite que Jackson sepa que:

**@JsonManagedReference** → este es el lado principal (se serializa normalmente).

**@JsonBackReference** → este es el lado inverso (no se vuelve a serializar, evitando el bucle).

### Ejercicio 2

Implementar un modelo en Java donde:

- Un **Autor** puede tener varios **Libros**
- Cada **Libro** referencia a su **Autor**
- **Guardar** y **cargar** los datos en JSON usando Jackson
- Probar a gestionar correctamente la relación bidireccional mediante las dos alternativas: 

	- (1) ```@JsonIdentityInfo``` 
	- (2) ```@JsonManagedReference + @JsonBackReference```




