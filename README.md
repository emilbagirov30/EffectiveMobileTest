## Тестовое задание на позицию андроид-разработчика

**Приложение по поиску авиабилетов**

Разработайте приложение для поиска авиабилетов с использованием следующих функциональных возможностей:

1. **Главный экран:**
   - При входе в приложение открывается страница с формой для поиска билетов.
   - На главной странице также отображаются предложения от компании, которые загружаются с сервера.

2. **Выбор даты и места прибытия:**
   - При нажатии на поле для ввода открывается модальное окно, в котором можно изменить дату прибытия.
   - После ввода места прибытия происходит загрузка данных о авиакомпаниях и времени с сервера.

3. **Просмотр билетов:**
   - В режиме просмотра всех билетов отображается список доступных билетов с информацией о времени вылета, времени прилета, общем времени в полете, аэропорте и других деталях.

**Используемый стек:**
- Язык программирования: Kotlin
- Архитектурные подходы: Многомодульность, MVVM
- Компоненты: Fragment, RecyclerView
- Сетевые библиотеки: Retrofit, Moshi
- Асинхронность: Coroutines
- Внедрение зависимостей: Dagger
- Реактивные данные: LiveData

---

## Technical Test Assignment for Android Developer Position

**Flight Ticket Search Application**

Develop an application for searching flight tickets with the following features:

1. **Main Screen:**
   - Upon entering the app, the main page with a ticket search form is displayed.
   - The main page also shows company offers, which are loaded from the server.

2. **Date and Arrival Location Selection:**
   - Tapping on the input field opens a modal window where users can change the arrival date.
   - After entering the arrival location, the app fetches airlines and times from the server.

3. **Viewing Tickets:**
   - In the ticket list view, all available tickets are displayed with information on departure time, arrival time, total flight time, airport, and other details.

**Tech Stack:**
- Programming Language: Kotlin
- Architectural Approaches: Multi-module architecture, MVVM
- Components: Fragment, RecyclerView
- Networking Libraries: Retrofit, Moshi
- Asynchronous Programming: Coroutines
- Dependency Injection: Dagger
- Reactive Data: LiveData
