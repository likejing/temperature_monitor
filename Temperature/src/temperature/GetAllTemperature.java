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
 * Servlet implementation class GetAllTemperature
 */
@WebServlet("/GetAllTemperature")
public class GetAllTemperature extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllTemperature() {
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
        JsonArray array = new JsonArray();
        JsonObject objecthead = new JsonObject();
    	String[][] announceinfo;
    	// 从表中获取某id的time，temperature记录
    	try {
    		announceinfo=sql.select(" timestamp,data_value ","telemetry_data_his","where point_id = "+id+" order by timestamp desc",336, 2);
    		objecthead.addProperty("states", "success");  //添加获取成功状态
        	array.add(objecthead);
        	int i=0;
        	while(i<announceinfo.length&&announceinfo[i][0]!=null){
        		JsonObject object = new JsonObject();
        		object.addProperty("time",announceinfo[i][0]);
    			object.addProperty("temperature",announceinfo[i][1]);
    			array.add(object);
    			i++;
        	}
    		} catch (Exception e) {
    			e.printStackTrace();
    			objecthead.addProperty("states", "下载失败,请稍后再试");
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
