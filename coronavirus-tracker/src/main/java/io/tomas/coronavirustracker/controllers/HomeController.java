package io.tomas.coronavirustracker.controllers;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.tomas.coronavirustracker.CoronaVirusDataService;
import io.tomas.coronavirustracker.models.LocationStats;

@Controller
public class HomeController {
	
	@Autowired
	CoronaVirusDataService coronaVirusDataService;
	
	@GetMapping("/")
	public String home(Model model) {
		List<LocationStats> allCases = coronaVirusDataService.getAllConfirmedCases();
		int totalReportedCases = allCases.stream().mapToInt(stat -> stat.getLatestConfirmedCases()).sum();
		int totalNewCases = allCases.stream().mapToInt(stat -> stat.getDiffPreviousDay()).sum();
		Locale ptLocale = new Locale("pt");
		
		model.addAttribute("locationStats", allCases);
		model.addAttribute("totalReportedCases", String.format(ptLocale,"%,d",totalReportedCases));
		model.addAttribute("totalNewCases", String.format(ptLocale,"%,d",totalNewCases));
		return "home";
	}
}
