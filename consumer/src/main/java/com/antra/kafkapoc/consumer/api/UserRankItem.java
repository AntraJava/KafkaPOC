package com.antra.kafkapoc.consumer.api;

public class UserRankItem {
    private String id;
    private Long count;

    public UserRankItem(String id, Long count) {
        this.id = id;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "UserRankItem{" +
                "id='" + id + '\'' +
                ", count=" + count +
                '}';
    }
}
