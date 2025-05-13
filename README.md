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
Es necessaria la instalacion de Java SDK 21 o superior enlace: https://www.oracle.com/java/technologies/downloads/

### JavaFX SDK 21 o superior
Descargar JavaFX SDK 21 o superior en la maquina desde el siguiente enlace: https://gluonhq.com/products/javafx/

Ubicar SDK en la carpeta de Java (C:\Program Files\Java\javafx-sdk-24.0.1)

### Launch.json

Modificar ruta de JavaFX dependiendo de la version instalada en el equipo
```json
"vmArgs": "--module-path \"C:/Program Files/Java/javafx-sdk-21.0.7/lib\" --add-modules javafx.controls,javafx.fxml"
```

## Instalación y ejecución

### 1. Clona el repositorio

```bash
git clone https://github.com/tuusuario/my-stock-tools.git
cd my-stock-tools
```