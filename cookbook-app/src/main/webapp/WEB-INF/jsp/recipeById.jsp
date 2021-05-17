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
    <div class="card">
        <h5 class="card-header bg-primary text-white">${recipe.name}</h5>
        <div class="card-body">
            <table class="table table-hover">
                <tr>
                    <th>Preparation</th>
                    <td>${recipe.preparation}</td>
                </tr>
                <tr>
                    <th>Ingredients</th>
                    <td>
                        <c:forEach items="${recipe.ingredients}" var="ingredient">
                            <span>${ingredient.amount}</span>
                            <span>${ingredient.unit.toString()}</span>
                            <span>${ingredient.name}</span><br />
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <th>Categoires</th>
                    <td><c:forEach items="${recipe.categories}" var="cat" varStatus="status">
                        ${cat.toString()}<c:if test="${status.count < recipe.categories.size()}">, </c:if>
                    </c:forEach></td>
                </tr>
                <tr>
                    <th>Servings</th>
                    <td>${recipe.servings}</td>
                </tr>
                <tr>
                    <th>Uploader</th>
                    <td>${recipe.uploader.username}</td>
                </tr>
            </table>
            <div class="card">
                <h5 class="card-header bg-primary text-white">Commets</h5>
                <div class="card-body">
                    <table class="table table-hover">
                        <tr>
                            <th>
                                Comment
                            </th>
                            <th>
                                Time
                            </th>
                        </tr>
                        <c:forEach items="${recipe.comments}" var="comment">
                            <tr>
                                <td>
                                    ${comment.description}
                                </td>
                                <td>
                                    ${comment.timestamp}
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <form class="row row-cols-lg-auto g-3 align-items-center" method="post" action="/addComment">
                        <input type="hidden" name="recipeId" value="${recipe.id}">
                        <div class="col-12">
                            <div class="input-group">
                                <div class="input-group-text">Comment</div>
                                <input type="text" name="description" class="form-control">
                            </div>
                        </div>
                        <div class="col-12">
                            <button type="submit" class="btn btn-primary">Add Comment</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
</body>
</html>