package soa.lab.service;

import org.jboss.ejb3.annotation.Pool;
import soa.lab.model.Organization;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Stateless
@Pool("slsb-strict-max-pool")
public class OrganizationRestServiceImpl implements OrganizationRestService {
    private final String URL = "https://localhost:3151/orgs";
    private Client client;

    @Override
    public List<Organization> getOrganizationsFromMainService() {
        client = ClientBuilder.newClient();
        List<Organization> orgs = client.target(URL)
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<>() {
                });
        client.close();
        return orgs;
    }
}
