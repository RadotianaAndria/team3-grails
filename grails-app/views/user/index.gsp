<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-user" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>

            <table style="overflow: scroll">
                <tr>
                    <th>User</th>
                    <th>Cart</th>
                    <th>Cart status</th>
                </tr>
                <g:each var="user" in="${userList}">
                    <tr>
                        <td><g:link action="user/show" id="${user.id}">${user.username}</g:link></td>
                        <td>
                            <g:if test="${user.cart != null}">
                                <g:link action="cart" id="${user.id}">${user.cart}</g:link>
                            </g:if>
                            <g:else>
                                <g:link action="cart" id="${user.id}">Add</g:link>
                            </g:else>
                        </td>
                        <td>
                            <g:if test="${user.cart != null}">
                                <g:if test="${user.cart.isValid < 20}">
                                    <p class="text-danger">Cart Not Valid</p>
                                </g:if>
                                <g:else>
                                    <p class="text-success">Cart Valid</p>
                                </g:else>
                            </g:if>
                            <g:else>
                                <p class="text-info">No cart yet</p>
                            </g:else>
                        </td>
                        <td>
                            <g:if test="${user.cart != null}">
                                <g:form name="validate_cart" url="[action:'validateCart']">
                                    <g:hiddenField name="idUser" value="${user.id}"></g:hiddenField>
                                    <g:submitButton name="submit" value="Validate cart"/>
                                </g:form>
                            </g:if>
                        </td>
                    </tr>
                </g:each>
            </table>
            <div class="pagination">
                <g:paginate total="${userCount ?: 0}" />
            </div>
        </div>
    </body>
</html>