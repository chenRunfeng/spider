import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlBody;
import com.gargoylesoftware.htmlunit.util.Cookie;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.jetty.util.UrlEncoded;

public class HelloHtmlunit {

	public static void main(String[] args) throws Exception {
		// TODO 自动生成的方法存根
		String url0="http://epub.cnki.net/kns/brief/result.aspx?dbprefix=scdb&action=scdbsearch&db_opt=SCDB";//进入检索首页
		final String Dbcatalog=URLEncoder.encode("中国学术文献网络出版总库", "utf-8");
		String searchurl="http://epub.cnki.net/KNS/request/SearchHandler.ashx?action=&NaviCode=*&ua=1.21&PageName=ASP.brief_result_aspx&DbPrefix=SCDB&DbCatalog="+Dbcatalog+"&ConfigFile=SCDB.xml&db_opt=CJFQ,CJFN,CDFD,CMFD,CPFD,IPFD,CCND,CCJD,HBRD&base_special1=%&magazine_special1=%&txt_1_sel=SU&txt_1_value1=护理&txt_1_relation=#CNKI_AND&txt_1_special1==&his=0&__=";
		String searchurl1="http://epub.cnki.net/kns/brief/brief.aspx?pagename=ASP.brief_result_aspx&dbPrefix=SCDB&dbCatalog=中国学术文献网络出版总库&ConfigFile=SCDB.xml&research=off&t=";
		String str;
        //创建一个webclient
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //htmlunit 对css和javascript的支持不好，所以请关闭之
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        CookieManager CM = webClient.getCookieManager(); //获得cookie管理
        //获取页面
        String Ltimemillis=String.valueOf(System.currentTimeMillis());
        HtmlPage page0= webClient.getPage("http://epub.cnki.net/kns/Request/login.aspx?pt=1&p=/kns&td="+Ltimemillis);
        str=page0.asXml();
        System.out.print(str);
        webClient.getPage(url0);
//        Set<Cookie> cookies_ret0 = CM.getCookies();//返回的Cookie在这里，下次请求的时候可能可以用上啦。
//        System.out.println(cookies_ret0.toString());  
      //  Map<String,String> searchheaders=new HashMap<String,String>();
        //searchheaders.put(key, value)
        //SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        String sdate=date.toString();
        String sData=sdate.substring(0, sdate.lastIndexOf('C'))+"GMT+0800";
        System.out.print(sData);
        //URL surl=new URL("http://epub.cnki.net/KNS/request/SearchHandler.ashx?action=&NaviCode=*&ua=1.21&PageName=ASP.brief_result_aspx&DbPrefix=SCDB&DbCatalog=中国学术文献网络出版总库&ConfigFile=SCDB.xml&db_opt=CJFQ,CJFN,CDFD,CMFD,CPFD,IPFD,CCND,CCJD,HBRD&base_special1=%&magazine_special1=%&txt_1_sel=SU&txt_1_value1=护理&txt_1_relation=#CNKI_AND&txt_1_special1==&his=0&__="+sData);
       // searchheaders.put("DbCatalog", "中国学术文献网络出版总库");
        //String s=URLEncoder.encode(, "utf-8") ;
       webClient.getPage(getwebrequest(CM, getSearchURL(Dbcatalog, "哈哈"), url0));
        String Stimemillis=String.valueOf(System.currentTimeMillis());
        WebRequest webRequestList =getwebrequest(CM, searchurl1+Stimemillis+"&keyValue=&S=1", url0);
       HtmlPage htBody= webClient.getPage(webRequestList);
        System.out.print(htBody.asText());
//		WebRequest webRequestList = getwebrequest(CM,);
//		//get the content of the whole page  
//				final HtmlPage resultPage = webClient.getPage(webRequestList);
        //HtmlPage page0=(HtmlPage)
        		//webClient.getPage(new URL("http://epub.cnki.net/KNS/request/SearchHandler.ashx?action=undefined&NaviCode=*&PageName=ASP.brief_result_aspx&DbPrefix=SCDB&DbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDB.xml&db_opt=CJFQ%2CCJFN%2CCDFD%2CCMFD%2CCPFD%2CIPFD%2CCCND%2CCCJD%2CHBRD&base_special1=%25&magazine_special1=%25&txt_1_sel=SU&txt_1_value1=%E5%8C%BB%E9%99%A2&txt_1_relation=%23CNKI_AND&txt_1_special1=%3D&his=0&__=Sat%20Oct%2001%202016%2023%3A05%3A32%20GMT%2B0800"));
//        		 //设置cookie。如果你有cookie，可以在这里设置
//                Set<Cookie> cookies=null;
//                Iterator<Cookie> i = cookies.iterator();
//                while (i.hasNext()) 
//                {
//                	webClient.getCookieManager().addCookie(i.next());
//                }
//                //准备工作已经做好了
//                HtmlPage page=null;
//                page = wc.getPage(request);
//                if(page==null)
//                {
//                    System.out.println("采集 "+url+" 失败!!!");
//                    return ;
//                }
//                String content=page.asText();//网页内容保存在content里
//                if(content==null)
//                {
//                    System.out.println("采集 "+url+" 失败!!!");
//                    return ;
//                }
//                //搞定了
//                   Set<Cookie> cookies_ret = CM.getCookies();//返回的Cookie在这里，下次请求的时候可能可以用上啦。
//                   System.out.println(cookies_ret.toString());                 
//        HtmlPage page2=webClient.getPage("http://epub.cnki.net/kns/brief/brief.aspx?pagename=ASP.brief_result_aspx&dbPrefix=SCDB&dbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDB.xml&research=off&t=1475334333082&keyValue=%E5%8C%BB%E9%99%A2&S=1");         
//        HtmlPage page1=webClient.getPage("http://epub.cnki.net/kns/brief/brief.aspx?curpage=3&RecordsPerPage=20&QueryID=35&ID=&turnpage=1&tpagemode=L&dbPrefix=SCDB&Fields=&DisplayMode=listmode&PageName=ASP.brief_default_result_aspx");
        //获取页面的TITLE
//        str = page.asXml();
//        System.out.println(str);
        //获取页面的XML代码
//        str = resultPage.asXml();
//        System.out.println(str);
//        //获取页面的文本
//        str = page.asText();
//        System.out.println(str);
        //关闭webclient
        webClient.closeAllWindows();
	}

	private static WebRequest getwebrequest(CookieManager cm,String url,String refererString)
			throws MalformedURLException {
		StringBuilder stringBuilder = new StringBuilder();
		for (Cookie c : cm.getCookies()) {
			stringBuilder.append(c.getName()).append("=").append(c.getValue()).append(";");
		}
		stringBuilder.append("RsPerPage").append("=").append("20");
		//System.out.print(stringBuilder.toString());
		//webClient.getOptions().setJavaScriptEnabled(false);
//		WebRequest webRequestList = new WebRequest(new URL("http://epub.cnki.net/kns/brief/brief.aspx?curpage=1&RecordsPerPage=20&QueryID=0&ID=&turnpage=1&tpagemode=L&dbPrefix=SCDB&Fields=&DisplayMode=listmode&PageName=ASP.brief_result_aspx#J_ORDER"));
		URL Url=new URL(url);
		WebRequest webRequestList = new WebRequest(Url);
		webRequestList.setAdditionalHeader("Host", "epub.cnki.net");
		webRequestList.setAdditionalHeader("Cookie", stringBuilder.toString());
		webRequestList.setAdditionalHeader("Connection", "keep-alive");
		webRequestList.setAdditionalHeader("Accept","textml,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		webRequestList.setAdditionalHeader("Referer",refererString);
		//webRequestList.setAdditionalHeader("Referer","http://epub.cnki.net/kns/brief/brief.aspx?pagename=ASP.brief_result_aspx&dbPrefix=SCDB&dbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDB.xml&research=off&t="+Rtimemillis+"&keyValue=&S=1");
		return webRequestList;
	}
	public static String  getSearchURL(String DbCatalog,String txt_1_value1) throws UnsupportedEncodingException {
    	Date date=new Date();
        String sdate=date.toString();
        //String sData=URLEncoder.encode(sdate.substring(0, sdate.lastIndexOf('C'))+"GMT+0800", "utf-8") ;
        String sData=URLEncoder.encode(sdate.substring(0, sdate.lastIndexOf('C'))+"GMT+0800","utf-8");
        String sDAte=sData.replaceAll("[+]", "%20");
        String txt_1_relation=URLEncoder.encode("#CNKI_AND", "utf-8");
    	String searchurl0="http://epub.cnki.net/KNS/request/SearchHandler.ashx?action=undefined&NaviCode=*&PageName=ASP.brief_result_aspx&DbPrefix=SCDB&DbCatalog="+URLEncoder.encode(DbCatalog, "utf-8") +"&ConfigFile=SCDB.xml&db_opt=CJFQ,CJFN,CDFD,CMFD,CPFD,IPFD,CCND,CCJD,HBRD&base_special1=%25&magazine_special1=%25&txt_1_sel=SU&txt_1_value1="+URLEncoder.encode(txt_1_value1, "utf-8") +"&txt_1_relation="+txt_1_relation+"&txt_1_special1=%3D&his=0&__="+sDAte;
    	return searchurl0;
	}

}
