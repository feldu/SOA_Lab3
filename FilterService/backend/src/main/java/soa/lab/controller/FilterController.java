package soa.lab.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soa.lab.exception.DataNotFoundException;
import soa.lab.model.Organization;
import soa.lab.service.FilterService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orgdirectory")
public class FilterController {
    private final FilterService filterService;

    @Autowired
    public FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    @CrossOrigin(origins = "https://localhost:3151")
    @GetMapping("/filter/turnover/{min-annual-turnover}/{max-annual-turnover}")
    public ResponseEntity<List<Organization>> filterOrgsByTurnover(@PathVariable("min-annual-turnover") Float min, @PathVariable("max-annual-turnover") Float max) throws JsonProcessingException {
        log.info("Request to get orgs between {} and {} turnover values", min, max);
        List<Organization> orgs = unmarshalOrgs(filterService.filterOrgsByTurnover(min, max));
        if (orgs.isEmpty()) throw new DataNotFoundException("Organizations not found");
        log.info("Sending anal filtered orgs collection with {} elements", orgs.size());
        return new ResponseEntity<>(orgs, HttpStatus.OK);
    }

    @CrossOrigin(origins = "https://localhost:3151")
    @GetMapping("/filter/employees/{min-employees-count}/{max-employees-count}")
    public ResponseEntity<List<Organization>> filterOrgsByEmployeesCount(@PathVariable("min-employees-count") Float min, @PathVariable("max-employees-count") Float max) throws JsonProcessingException {
        log.info("Request to get orgs between {} and {} employees count values", min, max);
        List<Organization> orgs = unmarshalOrgs(filterService.filterOrgsByEmployeesCount(min, max));
        if (orgs.isEmpty()) throw new DataNotFoundException("Organizations not found");
        log.info("Sending employee filtered orgs collection with {} elements", orgs.size());
        return new ResponseEntity<>(orgs, HttpStatus.OK);
    }

    private List<Organization> unmarshalOrgs(String jsonOrgsArray) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(jsonOrgsArray, new TypeReference<>() {
        });
    }
}
