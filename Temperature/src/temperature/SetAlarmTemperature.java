package temperature;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class SetAlarmTemperature
 */
@WebServlet("/SetAlarmTemperature")
public class SetAlarmTemperature extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetAlarmTemperature() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub  
        // 设置字符编码为UTF-8, 这样支持汉字显示  
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8"); 
        /** 设置响应头允许ajax跨域访问 **/  
        response.setHeader("Access-Control-Allow-Origin", "*");  
        /* 星号表示所有的异域请求都可以接受， */  
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");  
        
        PrintWriter out = response.getWriter();
        String id=request.getParameter("id");
        String alarm_value=request.getParameter("alarm_value");
        JsonArray array = new JsonArray();
        JsonObject objecthead = new JsonObject();
    	//String[][] announceinfo;
    	// 从enternum+"announce"表中获取id,theme,text,time记录
    	try {
    		//announceinfo=sql.select(" time,temperature ","temperature","order by id desc limit 100",10, 4);
    		int update_states=sql.update("telemetry_data_alarm","alarm_value="+alarm_value," where point_id = "+id);
    		if(update_states!=0){
    			objecthead.addProperty("states", "success");  //添加获取成功状态
    		}
    		else{
    			objecthead.addProperty("states", "设置失败,请稍后再试");
    		}
        	array.add(objecthead);
    		} catch (Exception e) {
    			e.printStackTrace();
    			objecthead.addProperty("states", "设置失败,请稍后再试");
    			array.add(objecthead);
    		} 
        out.print(array);
        out.flush();
        out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
