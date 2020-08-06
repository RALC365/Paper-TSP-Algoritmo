# TSP_CRC

### Proyecto Final de Análisis de Algoritmos

Autores:

- Crysthel Aparicio (@CrysthelAparicio)
- Richardson Cárcamo (@RALC365)
- César Figueroa (@cesarabelfigueroa)



 Se aborda el problema del agente viajero o TSP (por sus siglas en inglés “Travelling Salesperson Problem”) que busca en su versión de optimazación dar como resultado la ruta más óptima en costo basándose en el peso de las aristas o caminos, visitando todas las ciudades destinadas una tan sola vez. Se abordó con la solución con el heurístico del algoritmo codicioso, el cual basa en la regla del vecino más cercano, se posiciona sobre una ciudad, luego ve cuál de las ciudades adyacentes están más cercanas o tienen menos costos, y la añade a la trayectoria si esta no ha sido visitada; funciona de esa manera hasta regresar a la ciudad de origen. 


Se escribió un programa en el lenguaje de programación JAVA de Oracle, con la versión 8 actualización 111, con una interfaz de usuario relativamente sencilla para el usuario generada en su mayoría con el IDE Netbeans 8.2. Se utilizaron grafos implementados con una matriz de adyacencia que se genera a partir de los datos ingresados por el usuario en una mini interfaz gráfica de coordenadas en X y Y, que simulan un mapa con las locaciones de las ciudades a visitar, de tamaño 10 x 10.