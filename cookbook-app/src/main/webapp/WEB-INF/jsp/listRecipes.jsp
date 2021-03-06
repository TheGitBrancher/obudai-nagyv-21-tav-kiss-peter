<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">

    <title>CookBook WebApp</title>
</head>
<body>
<jsp:include page="navbar.jsp" />
<div class="container mt-3">
    <form action="/" method="get">
        <label class="form-label" for="search">Search</label>
        <input type="text" class="form-control" name="search" id="search">
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="checkbox" id="name" value="name" name="filter">
            <label class="form-check-label" for="name">name</label>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="checkbox" id="category" value="category" name="filter">
            <label class="form-check-label" for="category">category</label>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="checkbox" id="ingredient" value="ingredient" name="filter">
            <label class="form-check-label" for="ingredient">ingredient</label>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="checkbox" id="uploader" value="uploader" name="filter">
            <label class="form-check-label" for="uploader">uploader</label>
        </div>
    </form>
</div>
<div class="container mt-3">
    <div class="card">
        <h5 class="card-header bg-primary text-white">Recipes</h5>
        <div class="card-body">
            <table class="table table-hover">
                <tr>
                    <th>Name</th>
                    <th>Categories</th>
                    <th>Servings</th>
                    <th>Uploader</th>
                </tr>
                <c:forEach items="${recipes}" var="recipe">
                    <tr>
                        <td><a href="/recipe/${recipe.id}">${recipe.name}</a></td>
                        <td><c:forEach items="${recipe.categories}" var="cat" varStatus="status">
                            ${cat.toString()}<c:if test="${status.count < recipe.categories.size()}">, </c:if>
                        </c:forEach></td>

                        <td>${recipe.servings}</td>
                        <td>${recipe.uploader.username}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
</body>
</html>