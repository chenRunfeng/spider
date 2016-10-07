import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;  
import org.apache.http.NameValuePair;  
import org.apache.http.client.ClientProtocolException;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.CloseableHttpResponse;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;  
import org.apache.http.conn.ssl.SSLContexts;  
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;  
import org.apache.http.entity.ContentType;  
import org.apache.http.entity.mime.MultipartEntityBuilder;  
import org.apache.http.entity.mime.content.FileBody;  
import org.apache.http.entity.mime.content.StringBody;  
import org.apache.http.impl.client.CloseableHttpClient;  
import org.apache.http.impl.client.HttpClients;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils;  
import org.junit.Test;  


public class Hellohttpclient {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO 自动生成的方法存根
		Hellohttpclient hellohttpclient=new Hellohttpclient();
       String url0="http://epub.cnki.net/KNS/brief/result.aspx?dbprefix=scdb&action=scdbsearch&db_opt=SCDB";
       final String Dbcatalog=URLEncoder.encode("中国学术文献网络出版总库", "utf-8");
		String searchurl="http://epub.cnki.net/KNS/request/SearchHandler.ashx?action=&NaviCode=*&ua=1.21&PageName=ASP.brief_result_aspx&DbPrefix=SCDB&DbCatalog="+Dbcatalog+"&ConfigFile=SCDB.xml&db_opt=CJFQ,CJFN,CDFD,CMFD,CPFD,IPFD,CCND,CCJD,HBRD&base_special1=%&magazine_special1=%&txt_1_sel=SU&txt_1_value1=护理&txt_1_relation=#CNKI_AND&txt_1_special1==&his=0&__=";
       HttpGet httpget = null;// GET鏂规硶
		HttpPost httpPost = null;// post鏂规硶
		Header location = null;// 閲嶅畾鍚戝湴鍧�
		Header[] cookie = null;// cookie鍊�
		CloseableHttpResponse response = null;// 鍝嶅簲
		int statusCode = 0;// 鐘舵�鍚�
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
		httpget = new HttpGet(url0);// GET鏂规硶璇锋眰
		response = httpclient.execute(httpget);	
		statusCode = response.getStatusLine().getStatusCode();
		cookie = response.getHeaders("Set-Cookie");
		httpget.abort();
		response.close();
		String s="http://epub.cnki.net/KNS/request/SearchHandler.ashx?action=undefined&NaviCode=*&PageName=ASP.brief_result_aspx&DbPrefix=SCDB&DbCatalog=%25E4%25B8%25AD%25E5%259B%25BD%25E5%25AD%25A6%25E6%259C%25AF%25E6%2596%2587%25E7%258C%25AE%25E7%25BD%2591%25E7%25BB%259C%25E5%2587%25BA%25E7%2589%2588%25E6%2580%25BB%25E5%25BA%2593&ConfigFile=SCDB.xml&db_opt=CJFQ,CJFN,CDFD,CMFD,CPFD,IPFD,CCND,CCJD,HBRD&base_special1=%&magazine_special1=%&txt_1_sel=SU&txt_1_value1=%E5%93%88%E5%93%88&txt_1_relation=%23CNKI_AND&txt_1_special1==&his=0&__=Fri%20Oct%2007%2022%3A28%3A03%20GMT%2B0800";
		httpget = new HttpGet(s);// GET鏂规硶璇锋眰
		httpget.setHeaders(cookie);
		httpget.setHeader("Referer", url0);
		httpclient.execute(httpget);// 鎵ц璇锋眰锛屽苟鐩稿簲
		statusCode = response.getStatusLine().getStatusCode();
		cookie = response.getHeaders("Set-Cookie");
		httpget.abort();
		response.close();
		} catch (ClientProtocolException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}// 鎵ц璇锋眰锛屽苟鐩稿簲
	}
	private static String getsdate() {
		Date date=new Date();
        String sdate=date.toString();
        String sData=sdate.substring(0, sdate.lastIndexOf('C'))+"GMT+0800";
        return sData;
	}
	/** 
     * 发送 get请求 
     */  
    public void get(String url ) {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            // 创建httpget.    
            HttpGet httpget = new HttpGet(url);  
            System.out.println("executing request " + httpget.getURI());  
            // 执行get请求.    
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                // 获取响应实体    
                HttpEntity entity = response.getEntity();  
                System.out.println("--------------------------------------");  
                // 打印响应状态    
                System.out.println(response.getStatusLine());  
                if (entity != null) {  
                    // 打印响应内容长度    
                    System.out.println("Response content length: " + entity.getContentLength());  
                    // 打印响应内容    
                    System.out.println("Response content: " + EntityUtils.toString(entity));  
                }  
                System.out.println("------------------------------------");  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (org.apache.http.ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  

}
