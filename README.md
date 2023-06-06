# Weather forecast

Проект "Прогноз погоды" является приложением для получения прогноза погоды на основе текущего местоположения пользователя или на основе запроса населённого пункта.

## Структура проекта
Проект "Прогноз погоды" построен с использованием архитектуры **Clean Architecture** и паттерна **MVVM**. Вот основные компоненты проекта:

- *data* - содержит классы и интерфейсы для работы с данными, включая сетевые запросы, локальное хранилище и модели данных.
- *domain* - определяет бизнес-логику и включает интеракторы, репозитории и модели предметной области.
- *presentation* - отвечает за представление данных пользователю и содержит активности, фрагменты, адаптеры и ViewModel.
- *di* - содержит файлы конфигурации для внедрения зависимостей с использованием библиотеки Koin.
- *utils* - вспомогательные классы и утилиты.

## Используемые инструменты
В проекте использованы следующие инструменты и библиотеки (основные):

- Koin
- Kotlin Coroutines
- Retrofit
- Glide
- Gson
- Lottie
- Google Play Services Location

# Weather forecast

The "Weather Forecast" project is an application for obtaining weather forecasts based on the user's current location or a specific location query.

## Project Structure
The "Weather Forecast" project is built using the **Clean Architecture** and **MVVM** pattern. Here are the main components of the project:

- *data* - contains classes and interfaces for working with data, including network requests, local storage, and data models.
- *domain* - defines the business logic and includes interactors, repositories, and domain models.
- *presentation* - handles data presentation to the user and includes activities, fragments, adapters, and ViewModels.
- *di* - contains configuration files for dependency injection using the Koin library.
- *utils* - auxiliary classes and utilities.

## Used Tools
The project utilizes the following tools and libraries (main ones):

- Koin
- Kotlin Coroutines
- Retrofit
- Glide
- Gson
- Lottie
- Google Play Services Location
