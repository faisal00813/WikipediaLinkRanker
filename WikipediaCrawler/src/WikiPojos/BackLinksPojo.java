package WikiPojos;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)
public final class BackLinksPojo {
    //public final Warnings warnings;
    public final String batchcomplete;
    public final Continue _continue;
    public final Query query;

    @JsonCreator
    public BackLinksPojo( @JsonProperty("batchcomplete") String batchcomplete, @JsonProperty("continue") Continue _continue, @JsonProperty("query") Query query){
        //this.warnings = warnings;
        this.batchcomplete = batchcomplete;
        this._continue = _continue;
        this.query = query;
    }

//    public static final class Warnings {
//        public final Query query;
//
//        @JsonCreator
//        public Warnings(@JsonProperty("query") Query query){
//            this.query = query;
//        }
//
//
//    }

    public static final class Continue {
        public final String blcontinue;
        public final String _continue;

        @JsonCreator
        public Continue(@JsonProperty("blcontinue") String blcontinue, @JsonProperty("continue") String _continue){
            this.blcontinue = blcontinue;
            this._continue = _continue;
        }
    }

    public static final class Query {
        public final Backlink backlinks[];

        @JsonCreator
        public Query(@JsonProperty("backlinks") Backlink[] backlinks){
            this.backlinks = backlinks;
        }

        public static final class Backlink {
            public final long pageid;
            public final long ns;
            public final String title;
    
            @JsonCreator
            public Backlink(@JsonProperty("pageid") long pageid, @JsonProperty("ns") long ns, @JsonProperty("title") String title){
                this.pageid = pageid;
                this.ns = ns;
                this.title = title;
            }
        }
    }
}
//public class BacklinksPOJO {
//
//	   private Query query;
//
//	    private Continue _continue;
//
//	    private String batchcomplete;
//
//	    private Warnings warnings;
//
//	    public Query getQuery ()
//	    {
//	        return query;
//	    }
//
//	    public void setQuery (Query query)
//	    {
//	        this.query = query;
//	    }
//
//	    public Continue getContinue ()
//	    {
//	        return _continue;
//	    }
//
//	    public void setContinue (Continue _continue)
//	    {
//	        this._continue = _continue;
//	    }
//
//	    public String getBatchcomplete ()
//	    {
//	        return batchcomplete;
//	    }
//
//	    public void setBatchcomplete (String batchcomplete)
//	    {
//	        this.batchcomplete = batchcomplete;
//	    }
//
//	    public Warnings getWarnings ()
//	    {
//	        return warnings;
//	    }
//
//	    public void setWarnings (Warnings warnings)
//	    {
//	        this.warnings = warnings;
//	    }
//
//	    @Override
//	    public String toString()
//	    {
//	        return "ClassPojo [query = "+query+", continue = "+_continue+", batchcomplete = "+batchcomplete+", warnings = "+warnings+"]";
//	    }
//}
//
