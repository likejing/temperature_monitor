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
 * Servlet implementation class GetNewTemperature
 */
@WebServlet("/GetNewTemperature")
public class GetNewTemperature extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public GetNewTemperature() {
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
        //String enternum=request.getParameter("enternum");
        JsonArray array = new JsonArray();
        JsonObject objecthead = new JsonObject();
    	String[][] announceinfo;
    	//String[][] alarminfo;
    	// 从enternum+"announce"表中获取id,theme,text,time记录
    	try {
    		announceinfo=sql.select(" point_id,timestamp,data_value ","telemetry_data_rt"," ",64,3);
    		//alarminfo=sql.select("alarm_value","telemetry_data_alarm","", 64, 1);
    		objecthead.addProperty("states", "success");  //添加获取成功状态
        	array.add(objecthead);
        	int i=0;
        	while(i<announceinfo.length&&announceinfo[i][0]!=null){
        		JsonObject object = new JsonObject();
        		object.addProperty("point_id",announceinfo[i][0]);
        		object.addProperty("time",announceinfo[i][1]);
    			//int point_id=Integer.parseInt(announceinfo[i][0]);
    			int temperature=Integer.parseInt(announceinfo[i][2].substring(0, announceinfo[i][2].length()-3));
    			object.addProperty("temperature",temperature);
    			//int alarm_value=Integer.parseInt(alarminfo[point_id-1][0]);
    			int alarm_value=Integer.parseInt(announceinfo[63][2].substring(0,announceinfo[63][2].length()-3));
    			alarm_value+=25;
    			if(alarm_value>temperature){
    				object.addProperty("is_alarm","no");
    			}
    			else{
    				object.addProperty("is_alarm","yes");
    			}
    			//Timestamp ts = Timestamp.valueOf(announceinfo[i][2]);
    			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			array.add(object);
    			i++;
        	}
    		} catch (Exception e) {
    			e.printStackTrace();
    			objecthead.addProperty("states", "查询失败，请检查设备是否上传数据");
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
