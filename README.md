# Registro de Incidencias

## Descripción

Aplicación móvil desarrollada como proyecto académico para el curso de Técnicas de Producción Industrial de Software I.

El prototipo permite registrar información básica de una incidencia relacionada con la recolección de basura y mostrar una confirmación visual al crear el reporte.

## Funcionalidades actuales

- Ingreso de un título para el reporte.
- Ingreso de una descripción.
- Almacenamiento temporal de los datos mediante estado.
- Botón para crear el reporte.
- Mensaje de confirmación utilizando el título ingresado.
- Interfaz desarrollada con Jetpack Compose y Material 3.

## Tecnologías utilizadas

- Android Studio
- Kotlin
- Jetpack Compose
- Material 3
- Git
- GitHub

## Necesidad o problema

La aplicación busca facilitar el registro de problemas relacionados con la recolección de basura. Cuando la basura no es recolectada a tiempo, puede acumularse y generar molestias para los habitantes. El prototipo permite registrar de forma sencilla un título y una descripción para preparar un reporte.

## Usuario principal

La aplicación está dirigida principalmente a personas que necesiten reportar problemas relacionados con la recolección de basura en su comunidad. El usuario puede ingresar la información del problema y recibir una confirmación visual del reporte preparado.

## Requerimientos iniciales

### Requerimientos funcionales

- El sistema debe permitir ingresar un título para el reporte.
- El sistema debe permitir ingresar una descripción y preparar el reporte mediante el botón "Crear reporte".

### Requerimientos de interfaz y uso

- La pantalla debe mostrar claramente los campos de título y descripción.
- La aplicación debe mostrar una retroalimentación visible después de presionar el botón "Crear reporte".

## Estado del proyecto

**Prototipo inicial — Unidad 1**

Actualmente se cuenta con una interfaz funcional que permite ingresar información y visualizar una respuesta al realizar la acción de crear reporte.

## Ejecución

El proyecto puede abrirse desde Android Studio y ejecutarse en un emulador o dispositivo físico Android.

## Autor

Eduardo Carranza

Proyecto académico — UTEC

Semana 10 — Teclado y pantalla táctil

En esta semana se mejoró la aplicación RegistroIncidencias para adaptar la entrada de texto y la interacción táctil al contexto de uso.

Cambios realizados

Se configuró KeyboardOptions en los campos de Título y Descripción.
Se agregó capitalización de oraciones para facilitar la escritura.
Se configuró ImeAction.Next en el campo Título.
Se configuró ImeAction.Done en el campo Descripción.
Se agregó la selección de prioridad mediante las opciones Baja, Media y Alta.
Se utilizó clickable para implementar la interacción táctil.
Se agregó retroalimentación visible mostrando la prioridad seleccionada.
Se verificó el funcionamiento de la aplicación en un dispositivo físico.

Commit de la Semana 10: Semana 10: teclado contextual e interacción táctil


## Semana 11 — Integración de sensor

### Objetivo
Integrar el acelerómetro del dispositivo en la aplicación RegistroIncidencias para mostrar lecturas de movimiento en los ejes X, Y y Z.

### Implementación
Se utilizó SensorManager para obtener el acelerómetro y SensorEventListener para recibir sus lecturas. Los valores se muestran en la pantalla en metros por segundo al cuadrado (m/s²).

La aplicación también contempla el caso en que el dispositivo no tenga un acelerómetro disponible o no se pueda iniciar la lectura.

### Pruebas realizadas
- Se ejecutó la aplicación en un dispositivo físico.
- Se verificó la visualización de los valores X, Y y Z.
- Se movió el teléfono para comprobar que las lecturas cambiaran.
- Se verificó que el formulario, la selección de prioridad y el botón de crear reporte siguieran funcionando.

### Limitaciones
Las lecturas pueden presentar pequeñas variaciones incluso cuando el teléfono permanece quieto, debido a la gravedad y a las fluctuaciones del sensor.

### Mejora futura
Como mejora futura, se podría aplicar un promedio de varias lecturas para reducir las variaciones y presentar valores más estables.
