package co.com.pragma.model.bootcamp.model;

import java.util.List;


public class Capacity {

    private Long id;
    private String name;
    private List<Technology> technologies;
    private int quantityTechnologies;

    public Capacity(Long id, String name, List<Technology> technologies, int quantityTechnologies) {
        this.id = id;
        this.name = name;
        this.technologies = technologies;
        this.quantityTechnologies = quantityTechnologies;
    }

    public Capacity() {
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

    public List<Technology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<Technology> technologies) {
        this.technologies = technologies;
    }

    public int getQuantityTechnologies() {
        return quantityTechnologies;
    }

    public void setQuantityTechnologies(int quantityTechnologies) {
        this.quantityTechnologies = quantityTechnologies;
    }
}
