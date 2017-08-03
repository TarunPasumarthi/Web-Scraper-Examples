package WebScrapper;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class CSE110Scraper2 {
	public static void main(String [] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		HtmlPage page1=webClient.getPage("http://ucsd.edu/catalog/front/courses.html#jsoe");
		Document document= Jsoup.parse(page1.getWebResponse().getContentAsString());
		Elements elements =document.getElementsByAttributeValueStarting("href","../courses");
		JSONObject obj=new JSONObject();
		JSONArray array= new JSONArray();
		for(Element e: elements){
			String path=e.attr("href").substring(3);
			HtmlPage page2=webClient.getPage("http://ucsd.edu/catalog/"+path);
			Document document2= Jsoup.parse(page2.getWebResponse().getContentAsString());
			Elements elements2 = document2.getElementsByClass("course-name");
			for(Element j: elements2){
				array.put(j.text());
			}
		}
		obj.put("classes", array);
		System.out.println(obj);
		webClient.close();
	}
}
