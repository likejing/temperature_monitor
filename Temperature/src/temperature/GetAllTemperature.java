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
        // �����ַ�����ΪUTF-8, ����֧�ֺ�����ʾ  
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8"); 
        /** ������Ӧͷ����ajax������� **/  
        response.setHeader("Access-Control-Allow-Origin", "*");  
        /* �Ǻű�ʾ���е��������󶼿��Խ��ܣ� */  
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");  
        
        PrintWriter out = response.getWriter();
        String id=request.getParameter("id");
        JsonArray array = new JsonArray();
        JsonObject objecthead = new JsonObject();
    	String[][] announceinfo;
    	// �ӱ��л�ȡĳid��time��temperature��¼
    	try {
    		announceinfo=sql.select(" timestamp,data_value ","telemetry_data_his","where point_id = "+id+" order by timestamp desc",336, 2);
    		objecthead.addProperty("states", "success");  //��ӻ�ȡ�ɹ�״̬
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
    			objecthead.addProperty("states", "����ʧ��,���Ժ�����");
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
