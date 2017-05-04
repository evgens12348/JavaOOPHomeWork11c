package com.gmail.s12348.evgen;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class URLParser {
	private HashSet<String>urlset= new HashSet<>();
	private String url;
	private File file;

	public URLParser(String url, File file) {
		super();
		this.url = url;
		this.file = file;
	}

	public URLParser() {
		super();
	}

	public String getStringFromUrl(String urlAdress) {
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(urlAdress);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String text = "";
			for (; (text = br.readLine()) != null;) {
				sb.append(text).append(System.lineSeparator());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private HashSet<String> getURL(String html) {
		Pattern hrefp = Pattern.compile("href=\"(.+?)\"");
		Pattern srcp = Pattern.compile("src=\"(.+?)\"");
		Matcher hrefm = hrefp.matcher(html);
		Matcher srcm = srcp.matcher(html);
		while (hrefm.find()) {
			urlset.add(hrefm.group(1));
		}
		while (srcm.find()) {
			urlset.add(srcm.group(1));
		}
		HashSet<String> list = (HashSet<String>) urlset.stream()
				.filter(line -> line.contains("http"))
				.collect(Collectors.toSet());
		return urlset = list;
	}

	private void saveToFile(HashSet<String> list, File file) {
		try (PrintWriter pw = new PrintWriter(file)) {
			for (String text : list) {
				pw.println(text);
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void startingProcessing() {
		saveToFile(getURL(getStringFromUrl(url)), file);
	}
}
