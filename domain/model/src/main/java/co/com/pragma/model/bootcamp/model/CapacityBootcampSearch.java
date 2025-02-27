package co.com.pragma.model.bootcamp.model;

import java.util.List;

public class CapacityBootcampSearch {

    private Long id;
    private String name;
    private List<Capacity> capacityList;
    private int quantityCapabilities;

    public CapacityBootcampSearch(Long id, String name, List<Capacity> capacityList, int quantityCapabilities) {
        this.id = id;
        this.name = name;
        this.capacityList = capacityList;
        this.quantityCapabilities = quantityCapabilities;
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

    public List<Capacity> getCapacityList() {
        return capacityList;
    }

    public void setCapacityList(List<Capacity> capacityList) {
        this.capacityList = capacityList;
    }

    public int getQuantityCapabilities() {
        return quantityCapabilities;
    }

    public void setQuantityCapabilities(int quantityCapabilities) {
        this.quantityCapabilities = quantityCapabilities;
    }
}
