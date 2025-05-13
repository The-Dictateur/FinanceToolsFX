# My-Stock Tools

**My-Stock Tools** es una aplicación de escritorio desarrollada en **Java** con **JavaFX**, diseñada para consultar información bursátil en tiempo real utilizando la API REST de [Finnhub](https://finnhub.io/). Esta herramienta permite buscar acciones por su nombre o símbolo y visualizar sugerencias dinámicas mientras se escribe, ofreciendo una experiencia de usuario fluida y profesional.

---

## Características

- 🔍 **Búsqueda dinámica de acciones** (autocompletado)
- 📊 Consulta de datos bursátiles usando **API REST de Finnhub**
- 🖥️ Interfaz de usuario moderna con **JavaFX**
- 🔐 Uso seguro de claves API (a través de constantes o configuración)
- 💡 Preparado para expandirse con más funcionalidades como:
  - Gráficas de evolución
  - Visualización de noticias financieras
  - Favoritos o watchlist

---

## Tecnologías utilizadas

- **Java 17+**
- **JavaFX** (FXML + SceneBuilder)
- **HTTP Client** (`java.net.http.HttpClient`)
- **org.json** para el parseo de respuestas JSON

---

## Requisitos

### Java SDK 21 o superior
- Es necesario tener instalado Java SDK 21 o superior. Puedes descargarlo desde: https://www.oracle.com/java/technologies/downloads/

### JavaFX SDK 21 o superior
- Descarga e instala JavaFX SDK 21 o superior desde el siguiente enlace: https://gluonhq.com/products/javafx/

- Ubicar SDK en la carpeta de Java '(C:\Program Files\Java\javafx-sdk-24.0.1)'

### Modificar launch.json

- Ajusta la ruta del SDK de JavaFX según la ubicación en tu equipo:
```json
"vmArgs": "--module-path \"C:/Program Files/Java/javafx-sdk-21.0.7/lib\" --add-modules javafx.controls,javafx.fxml"
```

## Instalación y ejecución

### 1. Clona el repositorio

```bash
git clone https://github.com/tuusuario/my-stock-tools.git
cd my-stock-tools
```

### 2. Ejecuta el proyecto

- El proyecto está preparado para ejecutarse en el entorno de JavaFX si se ha configurado previamente el archivo launch.json