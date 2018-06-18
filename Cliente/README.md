## Antes de ejecutar esta aplicacion debe estar corriendo el servidor socket
Es necesario instalar lo siguiente en su raspberry
instalar java 
```
sudo apt-get install oracle-java8-jdk
```
driver de la webcam
```
sudo apt-get install fswebcam
sudo apt-get install qv4l2
sudo apt-get install libjpeg8-dev 
sudo apt-get install libv4l-dev
```
ejecutar el jar que se encuentra en la carpeta dist
```
 java -jar Cliente.jar
 ```
 al ejecutar introducir el host del servidor socket ejemplo
 ```
 192.168.43.105
 ```
 luego nos pedira el puerto este puerto es el 
 ```
 8888
 ```
 
