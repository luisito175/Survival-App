# Documentación del Proyecto: Survival App

## 1. Visión General del Proyecto

**Survival App** es una aplicación para Android diseñada como una navaja suiza de utilidades rápidas, orientada a situaciones de emergencia o necesidad. La aplicación centraliza en una única pantalla el acceso a funciones críticas del dispositivo y a configuraciones personalizadas por el usuario.

- **Nombre del Paquete**: `com.example.sosphone`
- **Versión Mínima de Android (minSdk)**: 26 (Android 8.0 Oreo)
- **Versión de SDK de Compilación (compileSdk)**: 36 (Android 15)

## 2. Funcionalidades Implementadas

La pantalla principal (`MainActivity`) da acceso a todas las funciones clave:

### a. Llamada de Emergencia (`llamada.kt`)

- **Disparador**: Botón de llamada en la pantalla principal.
- **Funcionalidad**: Inicia una llamada directa al número guardado en la configuración.
- **Implementación Técnica**:
    - Se utiliza un `Intent` con la acción `Intent.ACTION_CALL`.
    - El número se lee desde `SharedPreferences` (fichero `"numero"`), guardado previamente en `ConfActivity`.
    - **Gestión de Permisos**: La aplicación solicita el permiso `android.permission.CALL_PHONE` en tiempo de ejecución. Si el usuario lo concede, se realiza la llamada. Si lo deniega, se muestra un `Toast` y se redirige a los ajustes de la app para activarlo manualmente.

### b. Acceso a Ajustes del Sistema y Configuración de la App (`ConfActivity.kt`)

- **Disparador**: Botón de ajustes.
- **Funcionalidad**: Permite al usuario introducir y guardar:
    - Número de teléfono personalizado para llamadas.
    - URL del manual de supervivencia web.
    - Preferencias adicionales (modo oscuro, opciones, radio buttons).
- **Implementación Técnica**:
    - Los datos se guardan usando `SharedPreferences`.
    - Al volver a entrar, la `Activity` carga los datos guardados y los muestra en los `EditText` o checkboxes correspondientes.
    - Se utiliza `ViewBinding` para acceder a las vistas de forma segura.

### c. Manual de Supervivencia Web

- **Disparador**: Botón de web.
- **Funcionalidad**: Abre el navegador por defecto del dispositivo con una URL configurable por el usuario.
- **Implementación Técnica**:
    - La URL se obtiene de `SharedPreferences` (fichero `"url"`, clave `"url"`).
    - Si no hay ninguna URL guardada, se usa una por defecto (`https://esupervivencia.com/wp-content/uploads/2012/05/curso-supervivencia-bosque.pdf`).
    - Se utiliza un `Intent` con la acción `Intent.ACTION_VIEW`.

### d. Dados Aleatorios (`DadosActivity.kt`)

- **Disparador**: Botón "Dados".
- **Funcionalidad**: Simula el lanzamiento de dos dados, muestra los resultados y muestra una carta asociada a la suma.
- **Implementación Técnica**:
    - Se generan dos números aleatorios entre 1 y 6.
    - Se muestran las imágenes de los dados correspondientes y una carta según la suma.
    - Se usa `Handler` y `ScheduledExecutor` para animación de lanzamiento.

### e. Chistes Aleatorios (`ChistesActivity.kt`)

- **Disparador**: Botón "Chistes".
- **Funcionalidad**: Muestra un chiste y lo lee en voz alta.
- **Implementación Técnica**:
    - Lista de chistes almacenados en la aplicación.
    - Se usa `TextToSpeech` con idioma español (`Locale("es", "ES")`) para la lectura.
    - Soporta un doble toque para leer el chiste; un toque describe el botón.

### f. Alarma Rápida

- **Disparador**: Botón de alarma.
- **Funcionalidad**: Programa una alarma en la aplicación de reloj del sistema para que suene 2 minutos después del momento actual.
- **Implementación Técnica**: 
    - Se usa un `Intent` con la acción `AlarmClock.ACTION_SET_ALARM`.
    - Se añaden los `extras` `EXTRA_MESSAGE`, `EXTRA_HOUR` y `EXTRA_MINUTES`.

## 3. Estructura del Código y Componentes Clave

### a. Actividades

- **`MainActivity.kt`**: Pantalla principal y centro de navegación. Contiene los `listeners` para todos los botones que lanzan las demás funcionalidades y `Activities`.
- **`llamada.kt`**: Actividad dedicada a la lógica de la llamada de emergencia y la gestión de permisos.
- **`ConfActivity.kt`**: Configuración de la app: número de teléfono, URL y preferencias de usuario.
- **`DadosActivity.kt`**: Simulación de lanzamiento de dados y cartas asociadas.
- **`ChistesActivity.kt`**: Muestra y lee chistes aleatorios.

### b. Layouts (Archivos XML)

- **`activity_main.xml`**: Interfaz de la pantalla principal, con una parrilla de `ImageButton` para cada función y botones adicionales de "Dados" y "Chistes".
- **`activity_llamada.xml`**: Layout para la pantalla de llamada.
- **`activity_conf.xml`**: Contiene `EditText` para número y URL, `CheckBox` y `RadioGroup`, con botón para guardar configuración.
- **`activity_dados.xml`**: Layout para la actividad de dados.
- **`activity_chistes.xml`**: Layout para mostrar chistes y botón de lectura.

### c. Componentes de Android Utilizados

- **`Intents`**: Para navegar entre `Activities` y comunicarse con aplicaciones del sistema (Teléfono, Navegador, Reloj, Ajustes).
- **`SharedPreferences`**: Para almacenar de forma persistente las configuraciones del usuario (número de teléfono, URL, opciones).
- **Permisos en Tiempo de Ejecución**: Implementación moderna para `CALL_PHONE`.
- **`TextToSpeech`**: Lectura de chistes en español.
- **`ViewBinding`**: Acceso seguro a vistas sin `findViewById`.
- **`Handler` y `ScheduledExecutorService`**: Animaciones en la actividad de dados.


[Video Demostrativo](https://youtu.be/1o-bbZqcaso)
