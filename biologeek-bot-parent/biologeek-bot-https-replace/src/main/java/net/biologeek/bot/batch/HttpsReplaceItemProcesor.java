package net.biologeek.bot.batch;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.ConnectException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.biologeek.bot.plugin.beans.article.ArticleContent;
import net.biologeek.bot.plugin.exceptions.HttpConnectException;
import net.biologeek.bot.plugin.exceptions.HttpsConnectionException;
import net.biologeek.bot.wiki.client.Wikipedia;

@Component
public class HttpsReplaceItemProcesor implements ItemProcessor<ArticleContent, ArticleContent> {

	private Logger logger;

	private URL url;

	@Autowired
	private Wikipedia wikipedia;

	public HttpsReplaceItemProcesor() {
		logger = Logger.getLogger(this.getClass().getName());
	}

	public HttpsReplaceItemProcesor(URL url, Wikipedia wikipedia) {
		logger = Logger.getLogger(this.getClass().getName());
		this.url = url;
		this.wikipedia = wikipedia;
	}

	@Override
	/**
	 * Replaces all occurences of http:// by https:// when it is available
	 */
	public ArticleContent process(ArticleContent arg0) {
		Pattern pattern = Pattern.compile("<\\b(http|ftp)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]>");
		Matcher matcher = pattern.matcher(arg0.getValue());
		int count = 0;
		while (matcher.find()) {
			String address = matcher.group(count);
			try {
				// 1. Connect to page in HTTP
				// 2. If OK connect in HTTPS
				// 3. If OK ends, else throws exception
				String newAddress = connectToPageAndCheckStatus(address);
				replaceOldAddressWithNewAddressInArticle(arg0, address, newAddress);
			} catch (HttpsConnectionException e) {
				// If HTTPS fails, it does not support it
				logger.info("Https connection failed for " + address + ". Continuing...");
				continue;
			} catch (HttpConnectException e) {
				// If HTTP fails, do nothing
				logger.info("HTTP : Could not connect to " + address + ". Continuing...");
				continue;
			}
		}
		return arg0;
	}

	/**
	 * Replaces all occurences of address by new HTTPS newAddress in arg0
	 * article content
	 * 
	 * @param arg0
	 * @param address
	 * @param newAddress
	 * @return
	 */
	private ArticleContent replaceOldAddressWithNewAddressInArticle(ArticleContent arg0, String address,
			String newAddress) {
		if (!newAddress.equals(newAddress)) {
			arg0.getValue().replaceAll(address, newAddress);
		}
		return arg0;
	}

	/**
	 * Tries to connect to a page in Http then in Https
	 * 
	 * @param address
	 * @return
	 * @throws ConnectException
	 * @throws HttpsConnectionException
	 *             in case Https connection fails
	 * @throws HttpConnectException
	 *             in case http connection fails
	 */
	private String connectToPageAndCheckStatus(String address) throws HttpsConnectionException, HttpConnectException {

		int status;
		try {
			if (address.startsWith("https:")) {
				address.replace("https:", "http:");
			}
			status = sendHttpOrHttpsRequest(address);
		} catch (ConnectException e) {
			throw new HttpConnectException(e.getMessage());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new HttpConnectException(e.getMessage());
		}

		if (status >= 200 && status < 300) {
			int httpsResult = 0;
			try {
				httpsResult = sendHttpOrHttpsRequest(address.replace("http:", "https:"));
			} catch (MalformedURLException | ConnectException e) {
				e.printStackTrace();
				throw new HttpsConnectionException(e.getMessage());
			}
			if (httpsResult < 200 || httpsResult >= 300) {
				throw new HttpsConnectionException("Request Https return code is not correct");
			}
			return address;
		}
		return address;
	}

	private int sendHttpOrHttpsRequest(String address) throws MalformedURLException, ConnectException {
		url = new URL(address);
		try {
			if (address.startsWith("https;")) {
				return this.sendHttpsRequest(url);
			} else if (address.startsWith("http:")) {
				return this.sendHttpRequest(url);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new ConnectException("Could not get page " + address);
		}
		return 0;
	}

	/**
	 * Sends Https GET request to page
	 * 
	 * @param url
	 * @return the HTTP response code
	 * @throws IOException
	 */
	private int sendHttpsRequest(URL url) throws IOException {
		HttpsURLConnection con = null;
		if (url.getProtocol().equals("http")) {
			url = new URL(url.toString().replace("http:", "https:"));
		}
		con = (HttpsURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		return con.getResponseCode();
	}

	/**
	 * Sends an HTTP GET request to address
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private int sendHttpRequest(URL url) throws IOException {
		HttpURLConnection con = null;
		if (url.getProtocol().equals("http")) {
			url = new URL(url.toString().replace("https:", "http:"));
		}

		con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("GET");
		return con.getResponseCode();
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public Wikipedia getWikipedia() {
		return wikipedia;
	}

	public void setWikipedia(Wikipedia wikipedia) {
		this.wikipedia = wikipedia;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

}
