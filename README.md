![](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxggb8JadPYyGNwx5oz4Ju5NFfl1SGJHhG13SfFdoPhKMGm0CpV9K3SinlfLPGHQ3TZg&usqp=CAU)
# Dispositivos móviles App
Esta es una aplicación que recopila una serie de funcionalidades estudiadas durante el semestre, con el fin de aprender la mayor cantidad de recursos que podemos utilizar para desarrollar una aplicación móvil con un fin específico. Estas funcionalidades se organizaron de manera secuencial para una mayor facilidad al momento de probarlas.
## Librería Usadas
- Retrofit
- Arquitectura de Componentes de Android (ViewModel, LiveData).
- Corrutinas.
- Picasso.
- Firebase:
  
Permite la autenticación de usuarios de diversas plataformas como Google y Facebook.

Por lo que para usar ese servicios es necesario vincular el proyecto con los servicio de Google y de Meta respectivamente.

  ![](https://github.com/GeoWillC/Dispositivos_moviles_proyecto_gc_es/blob/main/resultados/proveedor.PNG)
  
- Google Play SDK:

Permite el uso de sus servicios con usuarios ya existentes y brinda una gran libertad al momento de usar sus servicios como herramientas de autenticacion.

 ![](https://github.com/GeoWillC/Dispositivos_moviles_proyecto_gc_es/blob/main/resultados/Google%20developer.PNG)

Este proceso requiere de una configuración para lograr implementar mas funcionalidades como la de recuperacion de contraseña. Esta debe realizarse entre el servicio de Google mediante firebase y la aplicacion que estemos desarrollando.

![](resultados/restablecer.PNG)

Deberemos obtener una Huella digital del certificado SHA que vincularemos al proyecto.

![](resultados/vincular.PNG)

Esta se podra obtener ejecutando el comando ./gradlew signingReport que nos mostrara el siguiente resultado

![](resultados/sha.PNG)

Una vez hecho esto ingresaremos la huella en la configuracion del SDK, descargaremos el archivo Json lo pegamos en nuestro proyecto y estará listo para agregar las funcionalidades de autentificar y recuperar contraseñas.

- Facebook SDK:

Facebook impone el uso de usuarios de prueba en aplicacion no verificadas, ademas de exigir a quienes lo implementen tener pruebas con casos de uso para usar su servicios para el desarrollo de aplicaciones.
 ![](https://github.com/GeoWillC/Dispositivos_moviles_proyecto_gc_es/blob/main/resultados/Meta%20Developer.png)

- Glide:

![](https://i.ytimg.com/vi/4UFNT6MhIlA/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLDZL9oeLHepu7iRBtqMuInXlehb6g)

Es una Framework que facilita el manejo de elementos como imagenes, videos y gifs. Estos elementos pueden provenir de una URL lo que facilita su uso y alijera la carga que puede suponer tener muchos recursos en el proyecto.
Para su uso en Android Studio requiere de un contenedor el cual puede ser un ImageView, este debera estar vacio para su uso.

(Ejemplo de elemento sopotado por Glide)

![](https://www.despiertaymira.com/wp-content/uploads/2018/06/etienne_jacob-necesary-disorder13.gif)
  

## Funcionalidades de la aplicación.
- Registro e ingreso al aplicativo mediante el uso de Google, Facebook y Firebase.
- Recuperación de contraseña mediante correo.
- Conexión con Marvel API para búsqueda y consulta de personajes.
- Búsqueda simple y con comando de voz a través de Google.
- Localización del usuario en tiempo real con alta precisión en Google Maps. 
- Alarma con notificaciones. 
- Cámara que permite compartir las imágenes capturadas.


## Resultados
|![](resultados/main.png)|![](resultados/log_in_facebook.png)|![](resultados/logi_in_google.png)|![](resultados/registro.png) |![](resultados/recuperar.png)|
|----------|:-------------:|:-------------:|:-------------:|:-------------:|

|![](resultados/biometric.png)|![](resultados/Caratula.png)|![](resultados/herramientas.png)|![](https://github.com/GeoWillC/Dispositivos_moviles_proyecto_gc_es/blob/main/resultados/Marvel%20Api.png)|![](resultados/busqueda.png) |
|----------|:-------------:|:-------------:|:-------------:|:-------------:|

|![](resultados/location.png)|![](resultados/notification.png)|![](resultados/camara.png)|
|----------|:-------------:|:-------------:|



## Checkout
[Dispositivos móviles App](https://github.com/GeoWillC/Dispositivos_moviles_proyecto_gc_es)

## Colaboradores.
- Conlago George - Desarrollador de Android -[Perfil de GitHub](https://github.com/GeoWillC)
- Solano Erick - Desarrollador de Android -[Perfil de GitHub](https://github.com/easolano98)

## Licencia

Licencia MIT

Derechos de Autor (c) 2023 Conlago George & Solano Erick

Por la presente se otorga permiso, de forma gratuita, a cualquier persona que obtenga una copia de este software y los archivos de documentación asociados (el "Software"), para tratar con el Software sin restricciones, incluidos, entre otros, los derechos de usar, copiar, modificar, fusionar, publicar, distribuir, sublicenciar y / o vender copias del Software, y permitir a las personas a las que se les proporcione el Software hacer lo mismo, sujeto a las siguientes condiciones:

El aviso de derechos de autor anterior y este aviso de permiso deben incluirse en todas las copias o porciones sustanciales del Software.

EL SOFTWARE SE PROPORCIONA "TAL CUAL", SIN GARANTÍA DE NINGÚN TIPO, EXPRESA O IMPLÍCITA, INCLUYENDO PERO NO LIMITADO A LAS GARANTÍAS DE COMERCIABILIDAD, APTITUD PARA UN PROPÓSITO PARTICULAR Y NO INFRACCIÓN. EN NINGÚN CASO LOS AUTORES O TITULARES DE LOS DERECHOS DE AUTOR SERÁN RESPONSABLES DE CUALQUIER RECLAMO, DAÑO U OTRA RESPONSABILIDAD, YA SEA EN UNA ACCIÓN DE CONTRATO, AGRAVIO U OTRO MOTIVO, DERIVADA DE, FUERA DE O EN CONEXIÓN CON EL SOFTWARE O EL USO U OTRAS ACCIONES EN EL SOFTWARE.pero no se limita a, la venta del Software, servicios relacionados o productos que incorporen el Software de manera directa o indirecta.
El aviso de derechos de autor anterior y este aviso de permiso deberán incluirse en todas las copias o porciones sustanciales del Software.
EL SOFTWARE SE PROPORCIONA "TAL CUAL", SIN GARANTÍA DE NINGÚN TIPO, EXPRESA O IMPLÍCITA, INCLUYENDO, PERO NO LIMITADO A, LAS GARANTÍAS DE COMERCIABILIDAD, APTITUD PARA UN PROPÓSITO PARTICULAR Y NO INFRACCIÓN. EN NINGÚN CASO LOS AUTORES O TITULARES DE LOS DERECHOS DE AUTOR SERÁN RESPONSABLES DE CUALQUIER RECLAMO, DAÑO U OTRA RESPONSABILIDAD, YA SEA EN UNA ACCIÓN DE CONTRATO, AGRAVIO O DE OTRO MODO, DERIVADA DE, FUERA DE O EN CONEXIÓN CON EL SOFTWARE O EL USO U OTRAS ACCIONES EN EL SOFTWARE.

