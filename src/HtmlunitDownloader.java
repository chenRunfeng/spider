import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.UrlUtils;

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
import java.util.Map;
public class HtmlunitDownloader implements Downloader {
	public HtmlunitDownloader(){
		
	}

	@Override
	public Page download(Request request, Task task) {
		// TODO 自动生成的方法存根
		String sttrurl=request.getUrl();
		String str;
		 //创建一个webclient
        WebClient webClient = new WebClient();
        //htmlunit 对css和javascript的支持不好，所以请关闭之
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        //获取页面
        HtmlPage page;
        String content;
		try {
			page = webClient.getPage(sttrurl);
			 //获取页面的TITLE
	        str = page.getTitleText();
	        System.out.println(str);
	        //获取页面的XML代码
	         content  = page.asXml();
	        //System.out.println(str);
	        //获取页面的文本
	        str = page.asText();
	        System.out.println(str);
			Page page1 = new Page();
			page1.setRawText(content);
			page1.setHtml(new Html(UrlUtils.fixAllRelativeHrefs(content,
					request.getUrl())));
			page1.setUrl(new PlainText(sttrurl));
			page1.setRequest(request);
	        //关闭webclient
	        webClient.closeAllWindows();
			return page1;
		} catch (FailingHttpStatusCodeException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		} catch (MalformedURLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void setThread(int arg0) {
		// TODO 自动生成的方法存根
		
	}
	

}
