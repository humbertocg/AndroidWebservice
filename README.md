# AndroidWebservice
Consumir un WebService dentro de una aplicación Android

Consumir un WebService SOAP en una aplicacion Android para consultar una base de datos dado unos parametros de entrada.

Usando el WebService del ejemplo https://github.com/humbertocg/WebServiceSOAP_MySQL Se crea un proyecto para consultar una base de datos ingresando como parametros un usuario y contraseña para asi obtener como resultado si esa combinacion de usuario-contraseña existe dentro de la base de datos.

Para ejecutar el ejemplo:
- Descargar y montar el proyecto https://github.com/humbertocg/WebServiceSOAP_MySQL en un servidor web.
- Ejecutar el script sql webservice.sql
- Modificar la cadena de conexión a la base de datos en MySQL en el archivo dbConexion.php
- En el proyecto AndroidWebservice, modificar la linea siguiente de la clase WebService:
    - private String URL = "http://192.148.7.100/WebServiceSOAP_MySQL/WebService.php?wsdl"; 
   *Colocando la ruta web del archivo WebService.php (ip de su servidor web).
- Ingrese los parametros que se le solicitan en la interfaz de la aplicación android (usuario y contraseña) y observe los resultados.

Permisos
- android.permission.INTERNET

Librerias:
- ksoap2
