#### Pasos a seguir para levantar la aplicacion
##### Genero el JAR
* Run -> clean install

##### Dentro de la carpeta del proyecto y al altura del Dockerfile, abrir una shell
* Para construir la imagen
`docker build -t test .`

* Para levantar el contenedor
`docker run –-name testmerlin -p 8081:8080 test`

##### En el navegador

* http://localhost:8081/swagger-ui/index.html