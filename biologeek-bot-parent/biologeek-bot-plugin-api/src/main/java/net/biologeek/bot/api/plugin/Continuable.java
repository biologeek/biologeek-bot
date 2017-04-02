package net.biologeek.bot.api.plugin;

/**
 * A continuable object is a paging mechanism provided by Mediawiki API. <br>
 * A field (cmcontinue or clcontinue) provides an ID to follow listing of the
 * objects you request. <br>
 * Example :<br>
 * <code> {<br>
   <b> "continue": {<br>
        "clcontinue": "736|American_Zionists",<br>
        "continue": "||"<br>
    }</b>,<br>
    "query": {<br>
        "pages": {<br>
            "736": {<br>
                "pageid": 736,<br>
                "ns": 0,<br>
                "title": "Albert Einstein",<br>
                "categories": [<br>
                    {<br>
                        "ns": 14,<br>
                        "title": "Category:1879 births"<br>
                    },<br>
                    {<br>
                        "ns": 14,<br>
                        "title": "Category:1955 deaths"<br>
                    },<br>
                    ...<br>
                 ]<br>
            }<br>
        }<br>
    }<br></code> <br>
 * <br>
 * 
 * To continue retrieving object, add &clcontinue=736|American_Zionists to your request
 * 
 */
public interface Continuable {

	public String getCmContinue();

	public void setCmContinue(String cmContinue);

}
