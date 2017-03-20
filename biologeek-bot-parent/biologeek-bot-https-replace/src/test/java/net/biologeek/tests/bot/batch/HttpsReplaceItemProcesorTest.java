package net.biologeek.tests.bot.batch;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import net.biologeek.bot.batch.HttpsReplaceItemProcesor;
import net.biologeek.bot.plugin.beans.article.ArticleContent;
import net.biologeek.bot.wiki.client.Wikipedia;
import retrofit2.http.Url;

@RunWith(MockitoJUnitRunner.class)
public class HttpsReplaceItemProcesorTest {

	@Mock
	Wikipedia wikipedia;

	@Mock
	URL url;
	@Spy
	HttpsReplaceItemProcesor sut;

	@Before
	public void init() {
		sut = new HttpsReplaceItemProcesor();
		wikipedia = mock(Wikipedia.class);
		url = mock(URL.class);
		sut.setWikipedia(wikipedia);
		sut.setUrl(url);
	}

	@Test
	public void shouldProcess() throws IOException {
		// TODO
	}

	private ArticleContent articleContent() {
		return new ArticleContent().title("Tintin").value(
				"<p><b>Tintin</b> may refer to:</p>\n<ul><li><i>The Adventures of Tintin</i>, the comics series by Belgian cartoonist Herg\u00e9\n<ul><li>Tintin (character), a fictional character in <i>The Adventures of Tintin</i></li>\n<li>Category:Tintin books</li>\n<li>Category:Tintin</li>\n<li><i>The Adventures of Tintin</i> (film), a <a href=\"http://www.google.fr/\">2011 film by Steven Spielberg</a> and <a href=\"http://en.wikipedia.org/w/api.php?action=query&titles=tintin&prop=extracts\">Peter Jackson\n<ul><li><i>The Adventures of Tintin: The Secret of the Unicorn</i> (video game), video game that accompanied the 2011 film</li>\n</ul></li>\n<li><i>The Adventures of Tintin</i> (TV series), a 1991\u20131992 TV series</li>\n<li><i>Tintin</i> (magazine), a 1946\u20131993 magazine</li>\n<li><i>Tintin and the Golden Fleece</i>, a 1961 film from France</li>\n<li><i>Herg\u00e9's Adventures of Tintin</i>, a 1959\u20131963 TV series</li>\n<li><i>Tintin: Destination Adventure</i>, the 4th Tintin video game</li>\n<li>Tintin (musical), a Belgian musical in two acts based on two of <i>The Adventures of Tintin</i></li>\n</ul></li>\n<li>TinTin++, a MUD client</li>\n<li>Tin Tin (band), a 1960s\u20131970s pop group\n<ul><li><i>Tin Tin</i> (album), the first studio album by the Australian group Tin Tin</li>\n</ul></li>\n<li>Tin Tin (British band), a 1980s British band featuring Stephen Duffy</li>\n<li>Tin-Tin Kyrano, a <i>Thunderbirds</i> character</li>\n<li>Tin Tin Out, a British music production team</li>\n<li>Tintin Anderzon (born 1964), a Swedish actress</li>\n</ul><h2><span id=\"See_also\">See also</span></h2>\n<ul><li>Rin Tin Tin</li>\n<li>Tenten (disambiguation)</li>\n</ul>");
	}
}
