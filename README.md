# Стройка

<hr>

## TODO

- <details>
  <summary>Механики</summary>

    - Ребитхи - новые города, бустеры статистики
    - Фриланс система -> доп доход (сроки, +- репутации)
        - Чем больше репутация - тем больше можешь потерять
        - Структуры: не здания (машины и т.д., чтобы транспортировать на заказ)
    - Починка зданий: механика починки - принос блоков
    - Прокачка зданий

</details>

- <details>
  <summary>Основные</summary>

    - Меню здания
    - Интегрировать линии в гайд
    - Инфа про характеристики работника в список работников
    - Кастомный клиент для бустеров
    - Обновить visual driver
    - Добавить тг бота
    - Интегрировать линии в гайд
    - Снос заглушек для постройки
    - Статистика плохо робит (kensuke)
    - Доделать сохранение всех полей в kensuke
    - Не работает ставка блоков
    - Взятие кредитов (переработать)
    - Донат: Автомат починка зданий(Здания не ломаются)
    - Глобальный бустер - меньше блоков для починки зданий
    - Изменить меню доната (меньше разных цветов)
    - Обучение (пройтись по всем механикам, рассказать)
    - <details>
      <summary>Глобальная карта мира</summary>

      ![image](https://i.imgur.com/t3I3Brf.jpg)
      </details>
    - <details>
      <summary>Информация про постройку</summary>

      ![image](https://i.imgur.com/GRSM5XF.png)
      </details>
    - Прокачка мэрии по внешнему миру
    - Переделать систему кейсов - 1 кейс, из него падает обычный, редкий, легендарный, указать какие работники могу
      выпасть
    - Меню след блоков (инфо про постройку)
    - Ежедневные задания
    - Связывание всего в экономику
    - Настройка экономики
    - Кастомные сервис (db)
    - Мультисерверность (автоматический запуск серверов)
    - В меню магазина показывать инфу про обновление цен
    - Кнопки инфо про меню
    - Меню зданий (состояние)
    - Кд на кнопки в менюшках
    - Стрелочки к поломанным зданиям

</details>

- <details>
  <summary>Кастомные менюшки</summary>

    - Прокачка рабочего
    - Взятие блоков со склада
    - Прокачка склада
    - Покупка блоков в магазине
    - Круг следующих блоков

</details>

- <details>
  <summary>Нужные карты</summary>

    - Структуры
    - Локации (перестройка в один город)
    - Здание мэрии

</details>

<hr>

<details>
  <summary>Побочные</summary>

- Переделать покупку локаций на ability с dependencies (зависимыми локациями)

</details>

<hr>

<details>
  <summary>Готово</summary>

- ✔Взятие денег в долг -> Банк
- ✔Работники + Покупка - в одно меню
- ✔Взятие блоков со склада (ЛКМ - 64, ПКМ - всё)
- ✔Склад: нет места в инвентаре - ...
- ✔Реактивный склад (изменяется без закрытия)
- ✔Тп по локам не работает
- ✔Можно было положить на склад предметы меню и доната (fix)
- ✔Сортировка работников по редкости
- ✔Здание мэрии - пассивный доход, улучшать
- ✔Афк зона
- ✔Донат: Игровая валюта
- <details>
  <summary>✔Русские символы в intelij терминале</summary>

  <h5>Settings/Preferences | Editor | File Encodings</h5>
  ```
  global encoding -> UTF-8
  project encoding -> UTF-8
  ```
  <h5>Help | Edit Custom VM Options</h5>
  ```
  -Dconsole.encoding=UTF-8
  -Dfile.encoding=UTF-8
  ```
  </details>
- ✔Оптимизация мира(gameRules: tickSpeed...)
- ✔Информация по работникам норм - инфо какие поля чё значат
- ✔[Теги в чате](https://colordesigner.io/gradient-generator) ([Готовые градиенты](https://uigradients.com/))
- ✔Ломание зданий улучшить (уведомление)
- ✔Поменять местами покупка - список
- ✔Наводишь на работника - показывается инфа
- ✔Показывать всех игроков в табе
- ✔Показывать, что можно положить на склад блоки, когда входишь в необходимую зону
- ✔Эмоджи денег отображается не по центру -> убрать эмоджи
- ✔Текстуры блоков проподали на складе (fix)
- ✔Статистика плохо робит (fix)
- ✔Теги в табе
- ✔Локации (покупка, перемещение)
- ✔Переходы между локациями
- ✔Рефактор менюшек доната
- ✔Вывод инфы о поломке
- ✔Всё зависало когда рабочий строил давольно быстро (fix)
- ✔Обучение отбрасывало на начальный шаг (fix)
- ✔Ключи в покупке рабочих изменены на 1, 5, 10
- ✔Блокировка перехода между реалмами мини режима (избегание потери статистики)
- ✔Меню настроек (вкл/выкл тега...)
- ✔Применение бустеров (доход)
- ✔Не работает ставка блоков, улучшить (fix)
- ✔Сохранение донатов
- ✔Сохранение работников
- ✔Можно ставить диагональные блоки (блок не соприкасается ни с одним другим)
- ✔Использование локализированных названий предметов
- ✔Мульти чат между серверами
- ✔Снос заглушек для постройки
- ✔Добавить тг бота
- ✔Добавить доп звуки
- ✔Улучшить меню постройки (клавиша M внутри постройки)
- ✔Выводить список блоков и их количество для постройки при выборе проекта
- ✔Группировка при выборе построек и покупки блоков по группам
- ✔Обновить visual driver
- ❌Ежедневные задания
- ❌Перенести всё из statistics в data
- ❌Сохранение построек после выхода
- ❌Цвета в showcase и storage меняются (fix)
- ❌Теги
    - ✔Меню покупки/выбора активного
    - ❌Донатные
    - ❌Реактивное меню
- ❌Механика: рабочим необходимо приносить нужные блоки для постройки
    - ✔Механика доставки блоков из инвентаря рабочим
    - ❌Механика доставки блоков из хранилища рабочим
    - ❌Прокачка склада на скорость передачи блоков
- ❌Ограничить склад по вместимости и добавить прокачку
- ❌Не подгружается полупостроенное здание при перезаходе (fix)

</details>

<hr>
