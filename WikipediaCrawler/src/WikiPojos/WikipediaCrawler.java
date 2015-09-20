package WikiPojos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import WikiPojos.BackLinksPojo;

public class WikipediaCrawler {

	
	
	public static void main(String[] args) throws URISyntaxException {
	
			try {
				String pageToBeParsed = args[0];
				Document doc =  Jsoup.connect(pageToBeParsed).get();
				Element content = doc.getElementById("mw-content-text");
				Elements pars = content.getElementsByTag("p");
				Elements links = new Elements();
				for(Element par : pars){
					links.addAll( par.getElementsByTag("a").stream().filter(x->!x.attr("href").contains("#") ).collect(Collectors.toList()));
					
				}
				Map<String,Integer> titles = new HashMap<String,Integer>();
				for(Element link:links){
					if(link.attr("title")!=""&&!link.attr("title").contains("Wikipedia")){
					int temp = GetPageBacklinks(link.attr("title"),"");
					titles.put(link.attr("title"), temp);
					}
				}
				titles = MapUtil.sortByValue(titles);

				System.out.println("Winner is ["+titles.keySet().toArray()[titles.size()-1]+ "] with score " + titles.values().toArray()[titles.size()-1]);
				
			} catch (IOException e) {
				e.printStackTrace();
			}

			
	}
	
	/**
	 * @param title
	 * @return
	 * @throws IOException
	 */
	static int GetPageBacklinks(String title,String blcontinue) throws IOException{
		try	{

		    StringBuilder content = new StringBuilder();
			ObjectMapper mapper = new ObjectMapper();
			String urlTemplate;
			if(blcontinue==""){
			 urlTemplate="https://en.wikipedia.org/w/api.php?action=query&list=backlinks&format=json&bltitle="+URLEncoder.encode(title)+"&blnamespace=4&bldir=ascending&blfilterredir=nonredirects&bllimit=max";
			}else{
				urlTemplate="https://en.wikipedia.org/w/api.php?action=query&list=backlinks&format=json&bltitle="+URLEncoder.encode(title)+"&blcontinue="+URLEncoder.encode(blcontinue)+"&blnamespace=4&bldir=ascending&blfilterredir=nonredirects&bllimit=max";
			}
			
		//Document doc =  Jsoup.connect(urlTemplate).get();   blcontinue=0%7C5431
			 URL url = new URL(urlTemplate);
		 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
	        conn.setRequestMethod("GET");
	        int allBackLinks= 0;
	        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.93 Safari/537.36");
	        conn.setRequestProperty("Accept", "*/*");
	        //conn.setDoOutput(true);

	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String line;
	   
	        
	        while ((line = bufferedReader.readLine()) != null)
	        {
	          content.append(line + "\n");
	        }
	        bufferedReader.close();

	        BackLinksPojo data = mapper.readValue(content.toString(),BackLinksPojo.class);
	        allBackLinks =  data.query.backlinks.length;
	        if(data._continue!=null){
//	        	System.out.println(title+ " ->continue(" + data._continue.blcontinue + ") found with "+ data.query.backlinks.length + " entries");
	        	allBackLinks += GetPageBacklinks(title,data._continue.blcontinue);
	        }
	        
	        return allBackLinks ;
	        
	        }
		
		catch(IOException ex){
			return -1;
			
			
		}
	}

	
		

}

class MapUtil{
	
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map )
	{
	      Map<K,V> result = new LinkedHashMap<>();
	     Stream <Entry<K,V>> st = map.entrySet().stream();

	     st.sorted(Comparator.comparing(e -> e.getValue()))
	          .forEach(e ->result.put(e.getKey(),e.getValue()));

	     return result;
	}	
}