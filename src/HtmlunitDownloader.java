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
		// TODO �Զ����ɵķ������
		String sttrurl=request.getUrl();
		String str;
		 //����һ��webclient
        WebClient webClient = new WebClient();
        //htmlunit ��css��javascript��֧�ֲ��ã�������ر�֮
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        //��ȡҳ��
        HtmlPage page;
        String content;
		try {
			page = webClient.getPage(sttrurl);
			 //��ȡҳ���TITLE
	        str = page.getTitleText();
	        System.out.println(str);
	        //��ȡҳ���XML����
	         content  = page.asXml();
	        //System.out.println(str);
	        //��ȡҳ����ı�
	        str = page.asText();
	        System.out.println(str);
			Page page1 = new Page();
			page1.setRawText(content);
			page1.setHtml(new Html(UrlUtils.fixAllRelativeHrefs(content,
					request.getUrl())));
			page1.setUrl(new PlainText(sttrurl));
			page1.setRequest(request);
	        //�ر�webclient
	        webClient.closeAllWindows();
			return page1;
		} catch (FailingHttpStatusCodeException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return null;
		} catch (MalformedURLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void setThread(int arg0) {
		// TODO �Զ����ɵķ������
		
	}
	

}
