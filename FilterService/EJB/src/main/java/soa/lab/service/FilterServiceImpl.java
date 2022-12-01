package soa.lab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import soa.lab.exception.DataNotFoundException;
import soa.lab.model.Organization;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Stateless
public class FilterServiceImpl implements FilterService {

    @EJB
    private OrganizationRestService organizationRestService;

    @Override
    public String filterOrgsByTurnover(Float min, Float max) throws JsonProcessingException {
        List<Organization> responseBody = organizationRestService.getOrganizationsFromMainService();
        log.info("Request from main service received with {} elements.\nFiltering by anal.", responseBody.size());
        List<Organization> organizations = responseBody.stream().filter(o -> o.getAnnualTurnover() >= min && o.getAnnualTurnover() <= max).collect(Collectors.toList());
        return marshalOrgs(organizations);
    }

    @Override
    public String filterOrgsByEmployeesCount(Float min, Float max) throws JsonProcessingException {
        List<Organization> responseBody = organizationRestService.getOrganizationsFromMainService();
        log.info("Request from main service received with {} elements.\nFiltering by employee.", responseBody.size());
        List<Organization> organizations = responseBody.stream().filter(o -> o.getEmployeesCount() >= min && o.getEmployeesCount() <= max).collect(Collectors.toList());
        return marshalOrgs(organizations);
    }

    private String marshalOrgs(List<Organization> organizations) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(organizations);
    }
}
