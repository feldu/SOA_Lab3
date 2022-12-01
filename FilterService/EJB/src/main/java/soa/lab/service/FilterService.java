package soa.lab.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ejb.Remote;

@Remote
public interface FilterService {
    String filterOrgsByTurnover(Float min, Float max) throws JsonProcessingException;

    String filterOrgsByEmployeesCount(Float min, Float max) throws JsonProcessingException;
}
