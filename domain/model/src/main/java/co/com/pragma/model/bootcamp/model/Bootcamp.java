package co.com.pragma.model.bootcamp.model;

import java.util.List;

public class Bootcamp {

    private Long id;
    private String name;
    private String description;
    private int quantityCapabilities;
    private List<Long> capabilitiesIds;

    public Bootcamp(Long id, String name, String description, int quantityCapabilities, List<Long> capabilitiesIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantityCapabilities = quantityCapabilities;
        this.capabilitiesIds = capabilitiesIds;
    }

    public Bootcamp() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantityCapabilities() {
        return quantityCapabilities;
    }

    public void setQuantityCapabilities(int quantityCapabilities) {
        this.quantityCapabilities = quantityCapabilities;
    }

    public List<Long> getCapabilitiesIds() {
        return capabilitiesIds;
    }

    public void setCapabilitiesIds(List<Long> capabilitiesIds) {
        this.capabilitiesIds = capabilitiesIds;
    }
}
