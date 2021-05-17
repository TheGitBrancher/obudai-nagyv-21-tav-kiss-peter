<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">

    <title>CookBook WebApp</title>
</head>
<body>
<jsp:include page="navbar.jsp" />
<div class="container text-danger mt-3">
    <form:form modelAttribute="addRecipeDto">
        <div>
            <form:errors path="name">
            </form:errors>
        </div>
        <div>
            <form:errors path="servings">
            </form:errors>
        </div>
        <div>
            <form:errors path="ingredients">
            </form:errors>
        </div>
        <div>
            <form:errors path="preparation">
            </form:errors>
        </div>
        <div>
            <form:errors path="categories">
            </form:errors>
        </div>
    </form:form>
</div>
<div class="container mt-3">
    <div class="card">
        <h5 class="card-header bg-primary text-white">New Recipe</h5>
        <div class="card-body">
            <form class="row row-cols-lg-auto" method="post" action="/addRecipe">
                <div class="input-group mb-3">
                    <div class="input-group-text">Name</div>
                    <input type="text" name="name" class="form-control">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-text">Servings</div>
                    <input type="number" name="servings" class="form-control">
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-text">Ingredients</div>
                    <textarea name="ingredients" class="form-control"></textarea>
                </div>
                <div class="input-group mb-3">
                    <span class="input-group-text">Preparation</span>
                    <textarea class="form-control" name="preparation"></textarea>
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-text">Categories</div>
                    <select name="categories" class="form-control" multiple>
                        <c:forEach items="${categoryOptions}" var="cat">
                            <option value="${cat}">${cat}</option>
                        </c:forEach>
                    </select>
                </div>
                <div>
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
</body>
</html>