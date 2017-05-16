package com.tecomgroup.energetics.client.graphCoord.Entities;

import javax.persistence.*;

/**
 * Created by uvarov.a on 16.05.2017.
 */
@Entity
@Table(name = "vertex", schema = "public", catalog = "graphs")
public class VertexEntity {
    private int id;
    private String caption;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "caption")
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VertexEntity that = (VertexEntity) o;

        if (id != that.id) return false;
        if (caption != null ? !caption.equals(that.caption) : that.caption != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (caption != null ? caption.hashCode() : 0);
        return result;
    }
}
