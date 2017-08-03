package WebScrapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class StockScrapper {
	public static void main(String [] args) throws IOException{
		Document doc= Jsoup.connect("http://www.dataroma.com/m/managers.php").get();
	    /*System.out.println(doc);*/
	    File file =new File("C:/Users/hp/Documents/stock.html");
  		if(!file.exists()){
  			file.createNewFile();
  		}
  		FileWriter fileWritter = new FileWriter(file,false);
  		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
  		bufferWritter.write(doc.toString());
  		bufferWritter.close();
		String[] words={"Portfolio Manager - Firm","Portfolio value","No. of stocks","Top 10 holdings (left to right)"};
		JSONArray result= new JSONArray();
		File nfile =new File("C:/Users/hp/Documents/stock.html");
		Document ndoc= Jsoup.parse(nfile,null);
		Elements elements=ndoc.select("tr");
		int j=0;
		for(Element element: elements){
			j++;
			if(j==1){
				continue;
			}
			JSONObject obj= new JSONObject();
			Elements tds=element.select("td");
			JSONObject internalObj= new JSONObject();
			int i=0;
			for(Element td: tds){
				if(i<3){
					obj.put(words[i], td.text());
				}
				else{
					String  text=td.text();
					if(td.select("b").first()==null){
						continue;
					}
					String stock=td.select("b").first().text();
					String [] perString=(text.split("%")[0]).split(" ");
					String percentage= perString[perString.length-1]+"%";
					//System.out.println(percentage);
					internalObj.put(stock, percentage);
				}
				i++;
			}
			obj.put(words[3], internalObj);
			result.put(obj);
		}
		System.out.println(result);
	}
}
