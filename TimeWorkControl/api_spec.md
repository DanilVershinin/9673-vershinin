* [Общие положения](#общие-положения)
* [Типы данных](#типы-данных)
  * [Supervisor](#supervisor)
  * [Worker](#Worker)
  * [WorkTime](#WorkTime)
  * [Error](#error)
* [Методы](#методы)
  * [POST /supervisors](#post-supervisors)
  * [GET /supervisors](#get-supervisors)
  * [GET /supervisors/{id}](#get-supervisorsid)
  * [GET /supervisors/{id}/workers](#get-supervisorsidworkers)
  * [PUT /supervisors/{id}](#put-supervisorsid)
  * [POST /workers](#post-workers)
  * [GET /workers](#get-workers)
  * [GET /workers/{id}](#get-workersid)
  * [GET /workers/{id}/worktimes](#get-workersidworktimes)
  * [PUT /workers/{id}](#put-workersid)
  * [DELETE /workers/{id}](#delete-workersid)
  * [POST /worktimes](#post-worktimes)
  * [GET /worktimes](#get-worktimes)
  * [GET /worktimes/{id}](#get-worktimesid)
  * [PUT /worktimes/{id}](#put-workersid)
* [Список ошибок](#список-ошибок)

## Общие положения
Передача параметров в методы `POST`, `PATCH` и `PUT` осуществляется с использованием типа данных `application/json` в кодировке UTF-8.

Результаты выполнения запросов передаются с использованием типа данных `application/json` в кодировке UTF-8.

Ошибки возвращаются с использованием типа данных [Error](#error) и семантически соответствующим http-кодом.

## Типы данных
### Supervisor
Сущность, содержащая информацию о руководителе.

| Поле | Тип данных | Обязательное | Комментарий |
| --- | --- | --- | --- |
| id | number | да | Уникальный идентификатор руководителя. |
| name | string | да | Имя руководителя. |
| surname | string | да | Фамилия руководителя. |
| middleName | string | нет | Отчество руководителя. |
| position | string | да | Должность руководителя. |

### Worker
Сущность, содержащая информацию о работнике.

| Поле | Тип данных | Обязательное | Комментарий |
| --- | --- | --- | --- |
| id | number | да | Уникальный идентификатор работника. |
| name | string | да | Имя работника. |
| surname | string | да | Фамилия работника. |
| middleName | string | нет | Отчество работника. |
| position | string | да | Должность работника. |
| supervisorId | number | да | Идентификатор руководителя. |

### WorkTime
Сущность, содержащая информацию об отработанном времени.

| Поле | Тип данных | Обязательное | Комментарий |
| --- | --- | --- | --- |
| id | number | да | Уникальный идентификатор отработанного времени. |
| date | date | да | Дата в формате YYYY-MM-DD. Указывает в какой день работник работал |
| time | number | да | Время, которое отработал работник по стандартной ставке (без переработок) |
| overtime | number | нет | Переработка (т.е. время которое работник отработал по повышенной ставке) |
| workerId | number | да | Идентификатор руководителя. |

### Error
Сущность, содержащая информацию о ошибке.

| Поле | Тип данных | Обязательное | Комментарий |
| --- | --- | --- | --- |
| code | string | да | Код ошибки. |
| message | string | нет | Сообщение. |

## Методы
### POST /supervisors
Создает нового руководителя.
#### Тело запроса
Данные руководителя за исключением идентификатора.
#### Ответ
[Supervisor](#supervisor)
#### Ошибки
| Код  | Комментарий |
| --- | --- |
| 1 | Внутренняя ошибка. |
| 2 | Некорректные значения параметров. |
| 3 | Сервис недоступен. |

### GET /supervisors
Возвращает руководителей, удовлетворяющих условиям фильтра.
#### Параметры запроса
| Параметр | Тип данных | Обязательное | Комментарий |
| --- | --- | --- | --- |
| name | string | нет | Имя руководителя. |
| surname | string | нет | Фамилия руководителя. |
| middleName | string | нет | Отчество руководителя. |
| position | string | нет | Должность руководителя. |
#### Ответ
[ [Supervisor](#supervisor) ]
#### Ошибки
| Код  | Комментарий |
| --- | --- |
| 1 | Внутренняя ошибка. |
| 2 | Некорректные значения параметров. |
| 3 | Сервис недоступен. |

### GET /supervisors/{id}
Возвращает руководителя с заданным идентификатором.
#### Ответ
[Supervisor](#supervisor)
#### Ошибки
| Код  | Комментарий |
| --- | --- |
| 1 | Внутренняя ошибка. |
| 3 | Сервис недоступен. |
| 4 | Руководитель не найден. |

### GET /supervisors/{id}/workers
Возвращает всех работников, прикрепленных к руководителю с заданным идентификатором.
#### Ответ
[ [Worker](#worker) ]
#### Ошибки
| Код  | Комментарий |
| --- | --- |
| 1 | Внутренняя ошибка. |
| 3 | Сервис недоступен. |
| 4 | Руководитель не найден. |

### PUT /supervisors/{id}
Создает или изменяет руководителя с заданным идентификатором.
#### Тело запроса
Данные руководителя за исключением идентификатора.
#### Ответ
[Supervisor](#supervisor)
#### Ошибки
| Код  | Комментарий |
| --- | --- |
| 1 | Внутренняя ошибка. |
| 2 | Некорректные значения параметров. |
| 3 | Сервис недоступен. |

### POST /workers
Создает нового работника. 
#### Тело запроса
Данные работника за исключением идентификатора.
#### Ответ
[Worker](#worker)
#### Ошибки
| Код  | Комментарий |
| --- | --- |
| 1 | Внутренняя ошибка. |
| 2 | Некорректные значения параметров. |
| 3 | Сервис недоступен. |

### GET /workers
Возвращает работников, удовлетворяющие условиям фильтра.
#### Параметры запроса
| Параметр | Тип данных | Обязательное | Комментарий |
| --- | --- | --- | --- |
| name | string | нет | Имя работника. |
| surname | string | нет | Фамилия работника. |
| middleName | string | нет | Отчество работника. |
| position | string | нет | Должность работника. |
| nameSupervisor | string | нет | Имя руководителя. |
| surnameSupervisor | string | нет | Фамилия руководителя. |
| middleNameSupervisor | string | нет | Отчество руководителя. |
| positionSupervisor | string | нет | Должность руководителя. |
#### Ответ
[ [Worker](#worker) ]
#### Ошибки
| Код  | Комментарий |
| --- | --- |
| 1 | Внутренняя ошибка. |
| 2 | Некорректные значения параметров. |
| 3 | Сервис недоступен. |

### GET /workers/{id}
Возвращает работника с заданным идентификатором.
#### Ответ
[Worker](#worker)
#### Ошибки
| Код  | Комментарий |
| --- | --- |
| 1 | Внутренняя ошибка. |
| 3 | Сервис недоступен. |
| 4 | Работник не найден. |

### GET /workers/{id}/worktimes
Возвращает всё отработанное работником с заданным идентификатором время.
#### Ответ
[ [WorkTime](#worktime) ]
#### Ошибки
| Код  | Комментарий |
| --- | --- |
| 1 | Внутренняя ошибка. |
| 3 | Сервис недоступен. |
| 4 | Работник не найден. |

### PUT /workers/{id}
Создает или изменяет работника с заданным идентификатором.
#### Тело запроса
Данные работника за исключением идентификатора.
#### Ответ
[Worker](#worker)
#### Ошибки
| Код  | Комментарий |
| --- | --- |
| 1 | Внутренняя ошибка. |
| 2 | Некорректные значения параметров. |
| 3 | Сервис недоступен. |

### DELETE /workers/{id}
Удаляет работника с заданным идентификатором.
#### Ответ
\-
#### Ошибки
| Код  | Комментарий |
| --- | --- |
| 1 | Внутренняя ошибка. |
| 3 | Сервис недоступен. |
| 4 | Работник не найден. |

### POST /worktimes
Создает нового данные по отработанному времени. 
#### Тело запроса
Данные по отработанному времени за исключением идентификатора.
#### Ответ
[WorkTime](#worktime)
#### Ошибки
| Код  | Комментарий |
| --- | --- |
| 1 | Внутренняя ошибка. |
| 2 | Некорректные значения параметров. |
| 3 | Сервис недоступен. |

### GET /worktimes
Возвращает отработанное работником(-ами) время, удовлетворяющие условиям фильтра.
#### Параметры запроса
| Параметр | Тип данных | Обязательное | Комментарий |
| --- | --- | --- | --- |
| date | date | нет | День отработки |
| nameWorker | string | нет | Имя работника. |
| surnameWorker | string | нет | Фамилия работника. |
| middleNameWorker | string | нет | Отчество работника. |
| positionWorker | string | нет | Должность работника. |
#### Ответ
[ [WorkTime](#worktime) ]
#### Ошибки
| Код  | Комментарий |
| --- | --- |
| 1 | Внутренняя ошибка. |
| 2 | Некорректные значения параметров. |
| 3 | Сервис недоступен. |

### GET /worktimes/{id}
Возвращает отработанное время с заданным идентификатором.
#### Ответ
[WorkTime](#worktime)
#### Ошибки
| Код  | Комментарий |
| --- | --- |
| 1 | Внутренняя ошибка. |
| 3 | Сервис недоступен. |
| 4 | Отработанное время не найдено. |

### PUT /worktimes/{id}
Создает или изменяет отработанное время с заданным идентификатором.
#### Тело запроса
Данные по отработанному времени за исключением идентификатора.
#### Ответ
[WorkTime](#worktime)
#### Ошибки
| Код  | Комментарий |
| --- | --- |
| 1 | Внутренняя ошибка. |
| 2 | Некорректные значения параметров. |
| 3 | Сервис недоступен. |

## Список ошибок
| Код  | Комментарий | HTTP-код |
| --- | --- | --- |
| 1 | Внутренняя ошибка. | 500 Internal Server Error|
| 2 | Некорректные значения параметров. | 400 Bad Request |
| 3 | Сервис недоступен. | 503 Service Unavailable |
| 4 | Объект не найден. | 404 Not Found |