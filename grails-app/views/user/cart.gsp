<%--
  Created by IntelliJ IDEA.
  User: sambatra
  Date: 2022-03-24
  Time: 11:32
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'category.label', default: 'Cart')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
    <a href="#cart-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
        </ul>
    </div>
    <div id="cart-user" class="content scaffold-show" role="main">
        <h1><g:message code="default.list.label" args="[entityName]" /></h1>
        <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
        </g:if>
        <div class= "container-fluid">
            <div class="row">

                <div class="col-md-6">
                    <h3 class="text-center">My cart</h3>


                    <g:if test="${cart}">
                        <b><i>Total Price : </i> ${cart.totalPrice}</b>
                        <g:form name="remove_item" url="[action:'removeItemFromCart']">
                            <g:hiddenField name="idUser" value="${id_user}"></g:hiddenField>
                            <g:select name="idProduct"
                                      from="${cart.items.product.id}"
                                      value="${cart.items.product.id}"
                                      noSelection="['':'-Choose the product to remove-']"/>
                            <g:submitButton name="submit" value="Remove"/>

                        </g:form>
                        <i><b>N.B : </b> isValid < 20 : non validé</i>
                        <f:table collection="${cart.items}" />
                    </g:if>
                </div>
                <div class="col-md-6">
                    <div class="row">
                        <g:form name="add_item" url="[action:'addItemIntoCart']">
                            <g:hiddenField name="idUser" value="${id_user}"></g:hiddenField>
                            <g:select name="idProduct"
                                      from="${products.id}"
                                      value="${idProduct}"
                                      noSelection="['':'-Choose the product-']"/>
                            <g:select name="quantity" from="${1..25}" value="${quantity}"
                                      noSelection="['':'-Choose the quantity of product-']"/>

                            <g:submitButton name="submit" value="Add"/>

                        </g:form>
                    </div>
                    <div class="row">
                        <h3 class="text-center">Products</h3>
                        <table style="overflow: scroll">
                            <tr>
                                <th>Value</th>
                                <th>Photo</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Price</th>
                            </tr>
                            <g:each var="product" in="${products}">
                                <tr>
                                    <td>${product}</td>
                                    <td>${product.photo}</td>
                                    <td>${product.name}</td>
                                    <td>${product.description}</td>
                                    <td>${product.price}</td>
                                </tr>
                            </g:each>
                        </table>
                    </div>

                </div>

            </div>
        </div>

    </div>
</body>
</html>