# NotesApp - POC: Jetpack Compose

<br><p align="center"><img width="296" alt="Screen Shot 2023-06-28 at 00 27 50" src="https://github.com/hernanRuiz/NotesApp_JetPackComposePOC/assets/1894700/ff734e27-c8b8-484f-98e4-9c2f39b5ff08"></p><br>

Esta app es una prueba de concepto de Jetpack Compose. Cuenta con las siguientes funcionalidades:

- Crear una nota.
- Marcarla con importante (primera nota en la foto) o no (segunda nota) al momento de crearla.
- Editar una nota.
- Eliminar una nota.
- Las notas se persisten y se recuperan al abrir la app.

<br><h3>Conceptos aplicados</h3>

- Arquitectura de desarrollo MVVM.
- Base de datos ROOM (para persistencia de las notas).
- Inyección de dependencias con hilt/dagger.

<br><h3>¿Qué aprendí con Jetpack Compose?</h3>

Si bien el lenguaje de programación sigue siendo Kotlin, la forma de diseñar cambia totalmente. Ya no hay xml de diseño aparte, sino que se arma toda la visual por código y en el mismo archivo se le da funcionalidad (aunque, componentes visuales que se repitan, se pueden extraer en archivos aparte y customizarlos. Luego referenciarlos en la vista en la que los necesitamos y ahí darles la funcionalidad necesaria para nuestra app). 
