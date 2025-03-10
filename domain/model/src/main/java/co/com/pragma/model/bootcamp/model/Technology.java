package co.com.pragma.model.bootcamp.model;

public class Technology {

    private Long id;
    private String name;

    public Technology(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Technology() {
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
}
