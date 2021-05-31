package servlet;
import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.ChatMessage;
import entity.ChatUser;
public class NewMessageServlet extends ChatServlet {
	
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// По умолчанию используется кодировка ISO-8859. Так как мы
    	// передаѐм данные в кодировке UTF-8
    	// то необходимо установить соответствующую кодировку HTTP-запроса
        request.setCharacterEncoding("UTF-8");
        String pname=null;
        // Извлечь из HTTP-запроса параметр 'message'
        String message = (String)request.getParameter("message");
        if (message!=null && !"".equals(message)) {
            String privatem=(String)request.getSession().getAttribute("privatem");
            if(privatem != null && !"toall".equals(privatem))
                pname=privatem;
            // По имени из сессии получить ссылку на объект ChatUser
            ChatUser author = activeUsers.get((String) request.getSession().getAttribute("name"));
            synchronized (messages) {
            	// Добавить в список сообщений новое
                messages.add(new ChatMessage(message, author,
                        Calendar.getInstance().getTimeInMillis(),pname));
            }
        }
        // Перенаправить пользователя на страницу с формой сообщения
        response.sendRedirect("/Lab8/messages.html");
    }
}