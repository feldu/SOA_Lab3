package soa.lab.model;

import lombok.Data;

@Data
public class Address {
    private long id;
    private String zipCode;
    private Location town;
}
