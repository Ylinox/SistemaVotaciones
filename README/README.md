# SistemaVotaciones
Prueba tecnica para desarrollador web Sebastian Montero Ramirez (Sistema de Votaciones)

Cambios y validaciones

Desde la Db se hace la validacion para que el email solo reciba el formato de email, esta misma validacion tambien se encuentra en el codigo
Se le añade a la tabla de candidatos la columna de email unico, esto para poder validar que un candidato no sea tambien un votante y se aprovecha para hacer el login desde los candidatos tambien, esto con el fin de no modificar tanto la estructura tecnica dada.

Instrucciones para ejecutar el proyecto

Requisitos para su funcionamiento:
1. Java JDK (Version 17 o superior)
2. Maven
3. PostgreSQL

Opcion 1 (Visual Studio Code)

Abir la carpeta SistemaVotaciones generada por el git y:
1. Abrir la carpeta apirestful
2. Abrir la carpeta src
3. Abrir la carpeta main
4. Configura el archivo application.properties que se encuentra en la carpeta resources

Se configura segun tu perfil en postgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/Prueba_Votes
spring.datasource.username=postgres
spring.datasource.password=1234

5. Abrir la carpeta java\com\prueba\apirestful
6. Abrir el archivo ApirestfulApplication.java 
7. Ejecutar el main

Opcion 2 (Terminal)

1. Clona el repositorio

git clone https://github.com/usuario/SistemaVotaciones.git
cd proyecto

2. Configura el archivo application.properties que se encuentra en la carpeta resources

Se configura segun tu perfil en postgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/Prueba_Votes
spring.datasource.username=postgres
spring.datasource.password=1234

3. Ejecuta la aplicacion desde la terminal

./mvnw spring-boot:run
o
mvn spring-boot:run

Documentacion de la APIrestful

1. Introduccion

Esta APIrestful creada en java 17 y usando Springboot framework permite crear votantes, candidatos, registrar votos y mostrar las estadisticas de las votaciones; la informacion se guarda en PostgreSQL; se valida que cada votante solo vote una vez y que los candidatos no puedan ser votantes.

Ademas cuenta con un sistema de seguridad Jwt para proteger sus endpoints, siendo asi solo accesible por medio de el correo unico,
tambien cuenta con un sistema de paginacion y de filtrado por medio del nombre ya sea del candidato o del votante.

2. Autenticacion

Para acceder a los endpoints que estan protegidos se debe primero de realizar el login con un correo existente en la base de datos, llamando al endpoint 

Postman
(POST) (http://localhost:8080/auth/login)
Body, Raw
{
    "email": "carlos.mendoza@example.com"
}
Respuesta
{
    "type": "voter",
    "token": "eyalasdjn..."
}

Lo que hace el sistema es validar que el correo ingresado exista en la base de datos y verficar si es un correo de un votante o de un candidato, si es de un votante devuelve el token y el tipo que seria votante, y en el caso del candidato el token y tipo que seria candidato.

Una vez tengamos el token en el header de Postman se le debe de ingresar lo siguiente

Key             Value
Authorization   Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjYXJsb3Mu...

Y ya con esto se pueden acceder a todos los endpoints durante 1 hora, a no ser que se cierre el servicio, en ese caso toca volver a loguearse

3. Endpoints

AuthController

Postman
(POST) (http://localhost:8080/auth/login) 
Autentica y genera el token
Body, Raw
{
    "email": "carlos.mendoza@example.com"
}
Respuesta
{
    "type": "voter",
    "token": "eyalasdjn..."
}

CandidateController

Postman
(POST) (http://localhost:8080/candidates) 
Crea un nuevo candidato
name string no null
email string no null unico
party string null
Body, Raw
{
    "name": "Sebastian Montero",
    "email": "sebas@example.com",
    "party": "Democracia"
}
Respuesta
{
    "id": 4,
    "name": "Sebastian Montero",
    "email": "sebas@example.com",
    "party": "Democracia",
    "votes": 0
}

(GET) (http://localhost:8080/candidates) 
Obtiene la lista de todos los registros
Body, Raw
{}
Respuesta
"content": [
        {
            "id": 3,
            "name": "Sofía Herrera",
            "email": "sofia.herrera@unionlibre.org",
            "party": "Unión Libre",
            "votes": 0
        },
        {
            "id": 2,
            "name": "Daniel Ortega",
            "email": "daniel.ortega@avanceciudadano.org",
            "party": "Avance Ciudadano",
            "votes": 1
        },
        {
            "id": 1,
            "name": "Laura López",
            "email": "laura.lopez@futuroverde.org",
            "party": "Futuro Verde",
            "votes": 2
        }
    ],
(Contenido generado por pageable)...

(GET) (http://localhost:8080/candidates?page=0) 
Obtiene la lista de todos los registros en la primera pagina (20 registros)
Body, Raw
{}
Respuesta
"content": [
        {
            "id": 3,
            "name": "Sofía Herrera",
            "email": "sofia.herrera@unionlibre.org",
            "party": "Unión Libre",
            "votes": 0
        },
        {
            "id": 2,
            "name": "Daniel Ortega",
            "email": "daniel.ortega@avanceciudadano.org",
            "party": "Avance Ciudadano",
            "votes": 1
        },
        {
            "id": 1,
            "name": "Laura López",
            "email": "laura.lopez@futuroverde.org",
            "party": "Futuro Verde",
            "votes": 2
        }
    ],
(Contenido generado por pageable)...

(GET) (http://localhost:8080/candidates?page=0&size=2) 
Obtiene los dos primeros registros en la primera pagina, si se cambia el numero de page pasa a la siguiente pagina y muestra dos registros diferentes y asi sucesivamente (si se cambia el size aumenta la cantidad de registros)
Body, Raw
{}
Respuesta
"content": [
        {
            "id": 2,
            "name": "Daniel Ortega",
            "email": "daniel.ortega@avanceciudadano.org",
            "party": "Avance Ciudadano",
            "votes": 1
        },
        {
            "id": 1,
            "name": "Laura López",
            "email": "laura.lopez@futuroverde.org",
            "party": "Futuro Verde",
            "votes": 2
        }
    ],
(Contenido generado por pageable)...

(GET) (http://localhost:8080/candidates?name=Daniel&page=0&size=2)
 Obtiene los dos primeros registros en la primera pagina con el nombre de Daniel, si se cambia el numero de page pasa a la siguiente pagina y muestra dos registros diferentes y asi sucesivamente (si se cambia el size aumenta la cantidad de registros) y si solo se deja name (http://localhost:8080/candidates?name=Daniel) muestra 20 registros por pagina
Body, Raw
{}
Respuesta
"content": [
        {
            "id": 2,
            "name": "Daniel Ortega",
            "email": "daniel.ortega@avanceciudadano.org",
            "party": "Avance Ciudadano",
            "votes": 1
        }
    ],
(Contenido generado por pageable)...

(GET) (http://localhost:8080/candidates/1) 
Obtiene el registro que tenga el id 1
Body, Raw
{}
Respuesta
{
    "id": 1,
    "name": "Laura López",
    "email": "laura.lopez@futuroverde.org",
    "party": "Futuro Verde",
    "votes": 2
}

(DELETE) (http://localhost:8080/candidates/1) 
Elimina el registro que tenga el id 1
Body, Raw
{}
Respuesta (status 204 No content)
{}

VoteController

(GET) (http://localhost:8080/votes) 
Obtiene todos los registros de los votos
Body, Raw
{}
Respuesta
[
    {
        "id": 1,
        "voterId": 1,
        "candidateId": 1
    },
    {
        "id": 2,
        "voterId": 2,
        "candidateId": 2
    }
]

(POST) (http://localhost:8080/votes)
Crea un nuevo registro de votacion, a no ser que el candidato no se encuentre en la base de datos o el votante ya haya votado
Body, Raw
{
        "voterId": 3,
        "candidateId": 1
}
Respuesta
{
        "id": 3,
        "voterId": 3,
        "candidateId": 1
}

(GET) (http://localhost:8080/votes/statistics) 
Obtiene las estadisticas de la votacion
Body, Raw
{}
Respuesta
{
    "totalVotes": 3,
    "candidateStats": [
        {
            "name": "Sofía Herrera",
            "votes": 0,
            "percentage": 0.0
        },
        {
            "name": "Daniel Ortega",
            "votes": 1,
            "percentage": 33.333333333333336
        },
        {
            "name": "Laura López",
            "votes": 2,
            "percentage": 66.66666666666667
        }
    ]
}

VoterController

Postman
(POST) (http://localhost:8080/voters) 
Crea un nuevo votante
name string no null
email string no null unico
Body, Raw
{
    "name": "Sebastian Montero Ramirez",
    "email": "sebas@example2.com"
}
Respuesta
{
    "id": 4,
    "name": "Sebastian Montero Ramirez",
    "email": "sebas@example2.com",
    "hasVoted": false
}

(GET) (http://localhost:8080/voters) 
Obtiene la lista de todos los registros
Body, Raw
{}
Respuesta
{
    "content": [
        {
            "id": 4,
            "name": "Sebastian Montero Ramirez",
            "email": "sebas@example2.com",
            "hasVoted": false
        },
        {
            "id": 1,
            "name": "Carlos Mendoza",
            "email": "carlos.mendoza@example.com",
            "hasVoted": true
        }
    ]
}
(Contenido generado por pageable)...

(GET) (http://localhost:8080/voters?page=0) 
Obtiene la lista de todos los registros en la primera pagina (20 registros)
Body, Raw
{}
Respuesta
"content": [
        {
            "id": 4,
            "name": "Sebastian Montero Ramirez",
            "email": "sebas@example2.com",
            "hasVoted": false
        },
        {
            "id": 1,
            "name": "Carlos Mendoza",
            "email": "carlos.mendoza@example.com",
            "hasVoted": true
        }
    ],
(Contenido generado por pageable)...

(GET) (http://localhost:8080/voters?page=0&size=2) 
Obtiene los dos primeros registros en la primera pagina, si se cambia el numero de page pasa a la siguiente pagina y muestra dos registros diferentes y asi sucesivamente (si se cambia el size aumenta la cantidad de registros)
Body, Raw
{}
Respuesta
"content": [
        {
            "id": 4,
            "name": "Sebastian Montero Ramirez",
            "email": "sebas@example2.com",
            "hasVoted": false
        },
        {
            "id": 1,
            "name": "Carlos Mendoza",
            "email": "carlos.mendoza@example.com",
            "hasVoted": true
        }
    ],
(Contenido generado por pageable)...

(GET) (http://localhost:8080/voters?name=Sebastian&page=0&size=2)
 Obtiene los dos primeros registros en la primera pagina con el nombre de Sebastian, si se cambia el numero de page pasa a la siguiente pagina y muestra dos registros diferentes y asi sucesivamente (si se cambia el size aumenta la cantidad de registros) y si solo se deja name (http://localhost:8080/voters?name=Sebastian) muestra 20 registros por pagina
Body, Raw
{}
Respuesta
"content": [
        {
            "id": 4,
            "name": "Sebastian Montero Ramirez",
            "email": "sebas@example2.com",
            "hasVoted": false
        }
    ],
(Contenido generado por pageable)...

(GET) (http://localhost:8080/voters/1) 
Obtiene el registro que tenga el id 1
Body, Raw
{}
Respuesta
{
    "id": 1,
    "name": "Carlos Mendoza",
    "email": "carlos.mendoza@example.com",
    "hasVoted": true
},

(DELETE) (http://localhost:8080/voters/1) 
Elimina el registro que tenga el id 1
Body, Raw
{}
Respuesta (status 204 No content)
{}

4. Estados de HTTP

200	OK
201	Recurso creado
400	Solicitud incorrecta
401	No autorizado
403	Prohibido (Requiere token)
404	No encontrado
500	Error interno del servidor (Envio incompleto de la informacion)

5. Dependencias usadas en el sistema 

spring-boot-starter-web	        Crea aplicaciones web con Spring MVC, incluye Tomcat embebido.
spring-boot-starter-data-jpa	Proporciona soporte para JPA y Hibernate (persistencia de datos).
spring-boot-starter-security	Añade seguridad y autenticación con Spring Security.
spring-boot-starter-validation	Permite validación de datos con anotaciones como @Valid.
spring-boot-starter-actuator	Expone endpoints para monitoreo (salud, métricas, etc.).

io.jsonwebtoken:jjwt-api	    API principal para manejo de JWT.
io.jsonwebtoken:jjwt-impl	    Implementación de la API de JWT (runtime).
io.jsonwebtoken:jjwt-jackson	Soporte para usar Jackson con JWT.

spring-boot-devtools	Hot reload y herramientas para desarrollo.
lombok	                Reduce código repetitivo usando anotaciones como @Getter, @Setter, etc.

spring-boot-starter-test	Frameworks para pruebas unitarias e integrales (JUnit, Mockito, etc.).

postgresql	            Driver JDBC para conectarse a PostgreSQL.

springdoc-openapi-starter-webmvc-ui	    Genera documentación Swagger/OpenAPI automática. (La url esta bloqueada por la autenticacion Jwt)