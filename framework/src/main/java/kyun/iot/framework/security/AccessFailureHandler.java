package kyun.iot.framework.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class AccessFailureHandler implements AccessDeniedHandler {

    private String errorPage;

    public AccessFailureHandler() {
    }

    public AccessFailureHandler(String errorPage) {
        this.errorPage = errorPage;
    }

    public String getErrorPage() {
        return errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
            throws IOException, ServletException {

        String error = "true";
        String message = exception.getMessage();

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("UTF-8");

        String data = StringUtils.join(new String[] {
                " { \"response\" : {",
                " \"error\" : ", error, ", ",
                " \"message\" : \"", message, "\" ",
                " } } "
        });

        PrintWriter out = response.getWriter();
        out.print(data);
        out.flush();
        out.close();
    }
}
