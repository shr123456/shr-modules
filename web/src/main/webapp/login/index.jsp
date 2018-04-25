<%--
  Created by IntelliJ IDEA.
  User: rui
  Date: 2017/10/13
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>

<%
    try{
        String ua = request.getHeader("User-Agent") ;
        if (ua != null) {
            if (ua.indexOf("iPhone") >-1 || ua.indexOf("iphone") >-1 || ua.indexOf("iPad")>-1 || ua.indexOf("ipad") >-1 || (ua.indexOf("ndroid") >-1 ||ua.indexOf("micro") >-1)) {
                request.getRequestDispatcher("/login/app/login.jsp").forward(request, response);
            }else{
                request.getRequestDispatcher("/login/web/login.jsp").forward(request, response);
            }
        }
    }catch(Throwable e){
    }
%>