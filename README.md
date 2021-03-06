# TeleHlam Messanger
## Open Source
[![image](https://opensource.org/files/osi_keyhole_300X300_90ppi_0.png)](https://opensource.org/licenses/MIT)


> Отсутствуют зависимости с закрытом исходным кодом

У мессенджера открытый исходный код:
* [Приложение](https://github.com/xDiaym/hlam) - MIT License
* [Сервер](https://github.com/xDiaym/TeleHlamServer) - MIT License

В отличии от самых популярный мессенджеров
| Название  | Открытый протокол | Открытое приложение | Открытый сервер |
| :-------- | :---------------- | :------------------ | :-------------- |
| TeleHlam  | ✔  Yes            | ✔  Yes             | ✔  Yes          |
| Telegram  | ✔  Yes            | ✔  Yes             | ❌ No           |
| Whatsup   | ❌ No             | ❌ No              | ❌ No           |
| Messanger | ❌ No             | ❌ No              | ❌ No           |


## Прост в использовании
### Для пользователей

Мы старались сделать приложения максимально понятным.
Material Design помог нам с этим.  
#TODO: скрин нашего приложения


### Для разработчиков

API сервиса очень прост. Он логичен и не содержит ничего лишнего.  
Пример запроса:
```
GET /user/byLogin?login=john HTTP/1.0
```
Пример ответа:
```
HTTP/1.0 200 OK

[
    {
        "id": 1,
        "login": "johndoe",
        "name": "John",
        "surname": "Doe"
    },
    {
        "id": 42,
        "login": "johnsmith",
        "name": "John",
        "surname": "Smith"
    },
]
```


## Скорость и экономия

Обмен сообщениями основан на [socket.io](https://socket.io/), которая базируется на WebSockets. Это позволяет:
1. Увеличить скорость обмена сообщениями
2. Экономить трафик, что актуально на мобильных платформах

[Источник](https://developerinsider.co/difference-between-http-and-http-2-0-websocket/)


## Современность
Приложение использует современные билиотеки
* Room (2018, Public Domain)
* Retrofit2 (2016, Apache License 2.0)
* Socket.io (2015, MIT License)
* MaterialDesign (2014, Apache License 2.0)

Что позволяет приложению оставаться актуальным


## Анонимность и свобода слова

### Анонимность
После скандала о массовой слежки в 2013 году мир взбудоражился.
Сейчас мы не видим никаких изменений.  

Приложение не требует никаких данных о пользователе. Его разрешения минимальны.  
Ни UUID, ни IP/MAC, ни номер телефона, ни электронная почта не могут быть скомпроментированны, т.к. они просто не нужны :)

Сервер так же не хранит сообщений пользователя, а просто перенаправляет сообщения. Все хранится на устройстве пользователя.

### Свобода слова
Мы понимаем важность свободы слова.
Никто не может ограничить или заблокривать аккаунт пользователя.

### Подсматривания
Никто не любит, когда за ним подсматривают.  
Просто закрой данные на замок. Биометрический замок.
