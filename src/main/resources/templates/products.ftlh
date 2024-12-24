<!DOCTYPE html>
<html>
<head>
    <title>BUYSELL</title>
</head>
<body>
<h1>BUYSELL</h1>
<h3>
    <#if user.email??>
        Имя пользователя: ${user.name}
        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Выйти">
        </form>
    <#else>
        <a href="/login">Войти</a>
    </#if>
</h3>

<h3>Товары со всей России</h3>
<form action="/" method="get">
    Поиск по названию объявления: <input type="text" name="title"><br>
    <input type="submit" value="Поиск">
</form>

<#if products?? && (products?size > 0)>
    <#list products as product>
        <div>
            <p>${product.title} - ${product.price} руб. | <a href="/product/${product.id}">Подробнее...</a></p>
        </div>
    </#list>
<#else>
    <p>Товаров нет</p>
</#if>

<#if user.email??>
    <h3>Создать новый товар</h3>
    <form action="/product/create" method="post" enctype="multipart/form-data">
        Название объявления: <input type="text" name="title"><br>
        Описание: <input type="text" name="description"><br>
        Цена: <input type="text" name="price"><br>
        Город: <input type="text" name="city"><br>
        Первая фотография: <input type="file" name="file1"><br>
        Вторая фотография: <input type="file" name="file2"><br>
        Третья фотография: <input type="file" name="file3"><br>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Добавить товар">
    </form>
</#if>
</body>
</html>

