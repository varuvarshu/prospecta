package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.masai.model.Data;
import com.masai.model.Entry;
import com.masai.model.EntryDTO;
import com.masai.repository.EntryRepository;

@RestController
public class ControllerClass {
	
	
	//@Autowired
	//private EntryRepository 
	
	
	@Autowired
	private RestTemplate re;
	
	@Autowired
	private EntryRepository rep;
	
	
	
	@GetMapping("/entries/{category}")
	public ResponseEntity<List<EntryDTO>> getEntriesHandler(@PathVariable("category") String category)
	{
		Data dats = re.getForObject("https://api.publicapis.org/entries", Data.class);
		List<Entry> lists = dats.getEntries();
		List<EntryDTO> list = lists.stream().filter(e -> e.getCategory().equalsIgnoreCase(category)).map(e -> new EntryDTO(e.getApi(), e.getDescription())).toList();
		return new ResponseEntity<List<EntryDTO>>(list,HttpStatus.OK);
		
	}
	
	
	
	@PostMapping("/entries")
	public ResponseEntity<Entry> saveEntriesHandler(@RequestBody EntryDTO resultDto)
	{
		Data take =  re.getForObject("https://api.publicapis.org/entries", Data.class);
		Entry e = take.getEntries().get(0);
		Entry n = new Entry(resultDto.getTitle(),resultDto.getDescription(),e.getAuth(),e.ishTTPS(),e.getCors(),e.getLink(),e.getCategory());
		Entry save = rep.save(n);
		return new ResponseEntity<Entry>(save,HttpStatus.OK);
	}
	
}
