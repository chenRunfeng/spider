/**
 * 
 */
package pageprocessor;

import us.codecraft.webmagic.selector.Html;

import com.gargoylesoftware.htmlunit.html.*;

/**
 * Specific implementation class to PageProcessor for CNZ crawl
 * @author Mr.Chen RunFENG
 *
 */
public class CNZPageProcessor implements PageProcessor {
	@Override
	public void process(HtmlPage page) {
		Html html=new Html(page.asXml());
//		html.
//		page.getByXPath(arg0)
	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根

	}

}
