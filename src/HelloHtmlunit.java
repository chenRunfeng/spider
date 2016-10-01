import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlBody;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
public class HelloHtmlunit {

	public static void main(String[] args) throws Exception {
		// TODO 自动生成的方法存根
		String str;
        //创建一个webclient
        WebClient webClient = new WebClient();
        //htmlunit 对css和javascript的支持不好，所以请关闭之
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        //获取页面
        HtmlPage page = webClient.getPage("http://epub.cnki.net/kns/brief/result.aspx?dbprefix=scdb&action=scdbsearch&db_opt=SCDB");
        //HtmlPage page0=(HtmlPage)
        		webClient.getCookies(new URL("http://epub.cnki.net/KNS/request/SearchHandler.ashx?action=undefined&NaviCode=*&PageName=ASP.brief_result_aspx&DbPrefix=SCDB&DbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDB.xml&db_opt=CJFQ%2CCJFN%2CCDFD%2CCMFD%2CCPFD%2CIPFD%2CCCND%2CCCJD%2CHBRD&base_special1=%25&magazine_special1=%25&txt_1_sel=SU&txt_1_value1=%E5%8C%BB%E9%99%A2&txt_1_relation=%23CNKI_AND&txt_1_special1=%3D&his=0&__=Sat%20Oct%2001%202016%2023%3A05%3A32%20GMT%2B0800"));
        HtmlPage page2=webClient.getPage("http://epub.cnki.net/kns/brief/brief.aspx?pagename=ASP.brief_result_aspx&dbPrefix=SCDB&dbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDB.xml&research=off&t=1475334333082&keyValue=%E5%8C%BB%E9%99%A2&S=1");
        HtmlPage page1=webClient.getPage("http://epub.cnki.net/kns/brief/brief.aspx?curpage=3&RecordsPerPage=20&QueryID=35&ID=&turnpage=1&tpagemode=L&dbPrefix=SCDB&Fields=&DisplayMode=listmode&PageName=ASP.brief_default_result_aspx");
        //获取页面的TITLE
        str = page.getTitleText();
        System.out.println(str);
        //获取页面的XML代码
        str = page1.asXml();
        System.out.println(str);
        //获取页面的文本
        str = page.asText();
        System.out.println(str);
        //关闭webclient
        webClient.closeAllWindows();
	}

}
