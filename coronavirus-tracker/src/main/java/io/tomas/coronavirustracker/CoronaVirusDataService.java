package io.tomas.coronavirustracker;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.tomas.coronavirustracker.models.LocationStats;

@Service
public class CoronaVirusDataService {
	private static String CONFIRMED_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
	
	private List<LocationStats> allConfirmedCases = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	@PostConstruct
	@Scheduled(cron = "* 1 * * * *")
	public void fetchConfirmedData() throws IOException, InterruptedException {
		
		List<LocationStats> newStats = new ArrayList<>();
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(CONFIRMED_DATA_URL)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		StringReader csvBodyReader = new StringReader(response.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		
		
		for (CSVRecord record : records) {
			LocationStats locationStats = new LocationStats();
			locationStats.setState(record.get("Province/State"));
		    locationStats.setCountry(record.get("Country/Region")); 
		    int latestCases = Integer.parseInt(record.get(record.size() - 1));
		    int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
		    locationStats.setLatestConfirmedCases(latestCases); 
		    locationStats.setDiffPreviousDay(latestCases - prevDayCases);
		    newStats.add(locationStats);
	
		}
		setAllConfirmedCases(newStats);
	}

	public List<LocationStats> getAllConfirmedCases() {
		return allConfirmedCases;
	}

	public void setAllConfirmedCases(List<LocationStats> allConfirmedCases) {
		this.allConfirmedCases = allConfirmedCases;
	}
	
}
