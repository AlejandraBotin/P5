# Práctica 5 - Alejandra Botín
Aplicación web que usa Spring JPA para persistir los datos de un API REST de gestión de usuarios.
El API permite el registro de nuevos usuarios y su identificación mediante email y password.
Una vez identificados, se emplea una cookie de sesión para autenticar las peticiones que permiten 
a los usuarios leer, modificar y borrar sus datos. También existe un endpoint para cerrar la sesión.  

## Endpoints

// TODO#1: rellena la tabla siguiente analizando el código del proyecto

| Método | Ruta       | Descripción                                 | Respuestas                                  |
|--------|------------|---------------------------------------------|---------------------------------------------|
| POST   | /register  | Registra un nuevo usuario                   | 200 OK, 400 Bad Request                     |
| POST   | /login     | Inicia sesión con email y password          | 200 OK (Token), 401 Unauthorized            |
| GET    | /profile   | Devuelve el perfil del usuario              | 200 OK, 401 Unauthorized                    |
| PUT    | /profile   | Modifica el nombre del usuario              | 200 OK, 400 Bad Request, 401 Unauthorized   |
| DELETE | /profile   | Elimina al usuario autenticado              | 204 No Content, 401 Unauthorized            |
| POST   | /logout    | Cierra la sesión actual (elimina el token)  | 200 OK, 401 Unauthorized                    |

## Comandos 

- Construcción: 
  ```sh
  ./mvnw clean package
  ```

- Ejecución: 
  ```sh
  ./mvnw spring-boot:run
  ```

- Tests:
  ```sh
  ./mvnw test
  ```
