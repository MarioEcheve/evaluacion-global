# evaluacion-global

# El proyecto fue creado con MAVEN no con GRADLE porque no lo manejo.

# Realice una prueba unitaria al servicio sign-up, esta fue creada con JUnit.

# en la carpeta diagramas encontrara los diagramas de actividades corrspondientes .

# en la carpeta postman esta la collecion de los endpoint.

# en la carpeta codigo encontrara todo el codigo de la aplicaccion, para poder ejecutarlo deje un archivo llamado docker-compose.

# este archivo sirve para poder desplegar en proyecto de manera rapida y sencilla solo es neceario tener instalado docker

# EJECUCION

# para levantar el proyecto ejecutar en la ruta src el comando : docker-compose up -d.

# Ejecutado el comando el proyecto se desplegara en la siguiente ruta : http://localhost:8080/

## otra forma de levantarlo es agregarlo a un IDE que soporte java y que tenga mvn configurado.

## pra el segundo endpoint : http://localhost:8080/api/user/login , colocar en los header el token que se genera en el endpoint : http://localhost:8080/api/user/sign-up. ejemplo : X-Auth-Token : token
