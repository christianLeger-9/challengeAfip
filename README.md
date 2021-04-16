# Desafio AFIP

Descargando el repositorio:

-git clone git@github.com:christianLeger-9/compraVenta.git


Para ejecutar la aplicacion utilizando Maven desde STS:

clean install


Workflow:

- Descargar repositorio.

- Validar funcionamiento del proyecto descargado ejecutando la aplicacion via Maven.

- Desde el Eclipse o STS, importar el proyecto (importar como proyecto Maven existente).


Ambientes disponibles:

Los mismos se encuentran configurados en el archivo "application.yml"
- dev
- qa



End-Points:




Aclaraciones: 



En cada uno de los test, hay dos respuestas, cuando el resultado es ok(codigo 200) o cuando hay algun problema
se puede elegir la que se desee para forzar error en el test.

Se puede utilizar swagger para probar los endpoint con la siguiente url una vez levantado el proyecto, 
http://localhost:8080/monedas/swagger-ui.html#/

