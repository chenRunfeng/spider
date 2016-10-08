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

import java.awt.List;
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
import java.util.Scanner;
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
        Scanner scanner=new Scanner(System.in);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        CookieManager CM = webClient.getCookieManager(); //获得cookie管理
        //获取页面
//        String Ltimemillis=String.valueOf(System.currentTimeMillis());
//        HtmlPage page0= webClient.getPage("http://epub.cnki.net/kns/Request/login.aspx?pt=1&p=/kns&td="+Ltimemillis);
        webClient.getPage(url0);
        System.out.println("请输入关键字：");
        String keyword=scanner.nextLine();
       webClient.getPage(getwebrequest(CM, getSearchURL(Dbcatalog, keyword), url0));
       //webClient.getOptions().setJavaScriptEnabled(false);
       webClient.getOptions().setCssEnabled(true);
        String Stimemillis=String.valueOf(System.currentTimeMillis());
        WebRequest webRequestList =getwebrequest(CM, getpage1url(keyword, Stimemillis), url0);
       HtmlPage htBody= webClient.getPage(webRequestList);
        //System.out.print(htBody.asXml());
        getResult(htBody);
        System.out.println("请输入页码:");
        int page=scanner.nextInt();
        WebRequest webRequest=getwebrequest(CM, getPageURL(page), getpage1url(keyword, Stimemillis));
        HtmlPage pagehtml= webClient.getPage(webRequest);
       // System.out.println(pagehtml.asXml());
        getResult(pagehtml);
        webClient.closeAllWindows();
	}

	public static void getResult(HtmlPage htmlpage) {
		java.util.List<?> LhtmlInput=htmlpage.getByXPath("//table[@class='GridTableContent']/tbody/tr/td[10]/div/input");
        for (Object object : LhtmlInput) {
        	HtmlInput htmlInput=(HtmlInput)object;
        	String outstrvalue=htmlInput.getDefaultValue();
        	String outString=outstrvalue.substring(0, outstrvalue.indexOf("|"));
			System.out.print(outString+"\r\n");
		}
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
	public static String getpage1url(String keyvalue,String timeString) throws UnsupportedEncodingException {
		String page1url="http://epub.cnki.net/kns/brief/brief.aspx?pagename=ASP.brief_result_aspx&dbPrefix=SCDB&dbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDB.xml&research=off&t="+timeString+"&keyValue="+URLEncoder.encode(keyvalue, "utf-8")+"&S=1";
		return page1url;
	}
	public static String getPageURL(int i) {
		String paStringurl="http://epub.cnki.net/kns/brief/brief.aspx?curpage="+i+"&RecordsPerPage=20&QueryID=0&ID=&turnpage=1&tpagemode=L&dbPrefix=SCDB&Fields=&DisplayMode=listmode&PageName=ASP.brief_result_aspx";
		return paStringurl;
	}

}
