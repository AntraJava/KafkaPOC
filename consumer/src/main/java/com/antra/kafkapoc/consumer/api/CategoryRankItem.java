package com.antra.kafkapoc.consumer.api;

public class CategoryRankItem {
    private String name;
    private Long count;

    public CategoryRankItem(String name, Long count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CategoryRankItem{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
