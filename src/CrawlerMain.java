

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.client.params.CookiePolicy;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlBody;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHtml;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class CrawlerMain {
	final static WebClient webClient=new WebClient(); 
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException 
	{
		CrawlerMain crawlerMain = new CrawlerMain();
		crawlerMain.setWebClient();
		try {
			System.out.println(crawlerMain.getHtml());
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	
	private void setWebClient(){
        webClient.getOptions().setCssEnabled(true);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setRedirectEnabled(true);
    }

    private String getHtml() throws Exception{
        webClient.getPage("http://epub.cnki.net/kns/Request/login.aspx?&td="+System.currentTimeMillis()*1000);
        HtmlPage firstPage = webClient.getPage("http://epub.cnki.net/kns/brief/result.aspx?dbprefix=scdb&action=scdbsearch&db_opt=SCDB");
        StringBuilder stringBuilder = new StringBuilder();
        for (Cookie c : webClient.getCookieManager().getCookies()) {
            stringBuilder.append(c.getName()).append("=").append(c.getValue()).append(";");
        }
        stringBuilder.append("RsPerPage").append("=").append("20");
        System.out.print(stringBuilder.toString());
        webClient.getOptions().setJavaScriptEnabled(false);
        WebRequest webRequestList = new WebRequest(new URL("http://epub.cnki.net/kns/brief/brief.aspx?curpage=1&RecordsPerPage=20&QueryID=0&ID=&turnpage=1&tpagemode=L&dbPrefix=SCDB&Fields=&DisplayMode=listmode&PageName=ASP.brief_result_aspx#J_ORDER"));
        webRequestList.setAdditionalHeader("Cookie", stringBuilder.toString());
        webRequestList.setAdditionalHeader("Host", "epub.cnki.net");
        webRequestList.setAdditionalHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        webRequestList.setAdditionalHeader("Referer","http://epub.cnki.net/kns/brief/brief.aspx?pagename=ASP.brief_result_aspx&dbPrefix=SCDB&dbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDB.xml&research=off&t=5573487109&keyValue=&S=1");
        //get the content of the whole page
        HtmlPage resultPage = webClient.getPage(webRequestList);
        HtmlHtml div=(HtmlHtml)resultPage.getByXPath("//html ").get(0);
        System.out.println(div.asXml());
//		System.out.println(page.asText());
        webClient.closeAllWindows();
        return  div.asXml();
    }
}
