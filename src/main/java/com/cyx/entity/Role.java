package com.cyx.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @date 2021/3/3
 */
public class Role implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String resource_ids;
    private List<String> resourcesName;
    //是否可用
    private Boolean available = Boolean.TRUE;

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

    public String getResource_ids() {
        return resource_ids;
    }

    public void setResource_ids(String resource_ids) {
        this.resource_ids = resource_ids;
    }

    public List<Long> getResourceIdsList() {
        List<Long> resourceIds = new ArrayList<>();
        if(!resource_ids.isEmpty()) {
            String[] split = resource_ids.split(",");
            for (String resourceId : split) {
                resourceIds.add(Long.valueOf(resourceId));
            }
        }
        return resourceIds;
    }

    public List<String> getResourcesName() {
        return resourcesName;
    }

    public void setResourcesName(List<String> resourcesName) {
        this.resourcesName = resourcesName;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", resource_ids='" + resource_ids + '\'' +
                ", resourcesName=" + resourcesName +
                ", available=" + available +
                '}';
    }
}
