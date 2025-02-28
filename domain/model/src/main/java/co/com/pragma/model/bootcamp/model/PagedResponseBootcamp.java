package co.com.pragma.model.bootcamp.model;

import java.util.List;

public class PagedResponseBootcamp {

    private long count;
    private int page;
    private int size;
    private List<CapacityBootcampSearch> capabilities;

    public PagedResponseBootcamp(long count, int page, int size, List<CapacityBootcampSearch> capabilities) {
        this.count = count;
        this.page = page;
        this.size = size;
        this.capabilities = capabilities;
    }

    // Getters y Setters
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<CapacityBootcampSearch> getItems() {
        return capabilities;
    }

    public void setItems(List<CapacityBootcampSearch> capabilites) {
        this.capabilities = capabilites;
    }

}
