package soa.lab.service;

import soa.lab.model.Organization;

import java.util.List;

public interface OrganizationRestService {
    List<Organization> getOrganizationsFromMainService();
}
