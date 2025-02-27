package co.com.pragma.model.bootcamp.model;

import java.util.List;

public class CapacityBootcamp {

    private List<Long> capacities;
    private Long bootcampId;

    public CapacityBootcamp(List<Long> capacities, Long bootcampId) {
        this.capacities = capacities;
        this.bootcampId = bootcampId;
    }

    public CapacityBootcamp() {
    }

    public List<Long> getCapacities() {
        return capacities;
    }

    public void setCapacities(List<Long> capacities) {
        this.capacities = capacities;
    }

    public Long getBootcampId() {
        return bootcampId;
    }

    public void setBootcampId(Long bootcampId) {
        this.bootcampId = bootcampId;
    }
}
