package soa.lab.model;

import lombok.Data;

@Data
public class Organization {
    private long id;
    private String name;
    private Integer employeesCount;
    private Coordinates coordinates;
    private java.time.ZonedDateTime creationDate;
    private Float annualTurnover;
    private OrganizationType type;
    private Address officialAddress;
}
