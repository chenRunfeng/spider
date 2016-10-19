/**
 * 
 */
package pipeline;

import common.ResultItem;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.SQLException;
import java.util.Map;
/**
 * @author Mr.Chen RunFENG
 *
 */
public class MySQLPipeLine implements PipeLine {

	private String mysqlurl="jdbc:mysql://127.0.0.1:3306/spider?useSSL=true&verifyServerCertificate=false&user=root";
//	private String user="root";
//	private String strpassword="root";
	private Connection conn = null;  
    private PreparedStatement statement = null;  
	//jdbc:mysql://127.0.0.1:3306/?user=root
	
	public MySQLPipeLine(){}
//	public MySQLPipeLine(String strurl,String struser,String strpassword) {
//		// TODO 自动生成的构造函数存根
//		mysqlurl=strurl;
//		user=struser;
//		this.strpassword=strpassword;
//	}
	public void Connect(){
		try {   
            Class.forName("com.mysql.jdbc.Driver" );   
            conn = DriverManager.getConnection( mysqlurl);   
            }  
        //捕获加载驱动程序异常  
         catch ( ClassNotFoundException cnfex ) {  
             System.err.println(  
             "装载 JDBC/ODBC 驱动程序失败。" );  
             cnfex.printStackTrace();   
         }   
         //捕获连接数据库异常  
         catch ( SQLException sqlex ) {  
             System.err.println( "无法连接数据库" );             
            sqlex.printStackTrace();   
         }  
	}
    // disconnect to MySQL  
   public void deconnSQL() {  
        try {  
            if (conn != null)  
                conn.close();  
        } catch (Exception e) {  
            System.out.println("关闭数据库问题 ：");  
            e.printStackTrace();  
        }  
    }  
// execute insertion language  
  public boolean insertSQL(String sql) {  
       try {  
           statement = conn.prepareStatement(sql);  
           statement.executeUpdate();  
           return true;  
       } catch (SQLException e) {  
           System.out.println("插入数据库时出错：");  
           e.printStackTrace();  
       } catch (Exception e) {  
           System.out.println("插入时出错：");  
           e.printStackTrace();  
       }  
       return false;  
   }  
   //execute delete language  
  private  boolean deleteSQL(String sql) {  
       try {  
           statement = conn.prepareStatement(sql);  
           statement.executeUpdate();  
           return true;  
       } catch (SQLException e) {  
           System.out.println("插入数据库时出错：");  
           e.printStackTrace();  
       } catch (Exception e) {  
           System.out.println("插入时出错：");  
           e.printStackTrace();  
       }  
       return false;  
   }  
	/* （非 Javadoc）
	 * @see pipeline.PipeLine#process(us.codecraft.webmagic.ResultItems)
	 */
	@Override
	public void process(ResultItem resultItem) {
		// TODO 自动生成的方法存根
			if (conn==null) {
				Connect();
			}
		StringBuilder stringBuilder=new StringBuilder("insert into cnz(title,author,type,time,abstract) values(");
          Map<String, Object> m=resultItem.getAll();
          for (String key : m.keySet()) {
			stringBuilder.append("'").append(m.get(key)).append("'").append(",");
		}
          stringBuilder.deleteCharAt(stringBuilder.length()-1);
          stringBuilder.append(")");
          String str=stringBuilder.toString();
          boolean issuccessful= insertSQL(str);
          if (issuccessful) {
		    System.out.print(".");
		}
	}
	@Override
	public void close(){
		deconnSQL();
	}
    
}
