package com.tecomgroup.energetics.client.graphCoord.Entities;

import javax.persistence.*;

/**
 * Created by uvarov.a on 16.05.2017.
 */
@Entity
@Table(name = "relations", schema = "public", catalog = "graphs")
public class RelationsEntity {
    private int relationsId;
    private int sourcerelations;
    private int receiverelation;

    @Id
    @Column(name = "relations_id")
    public int getRelationsId() {
        return relationsId;
    }

    public void setRelationsId(int relationsId) {
        this.relationsId = relationsId;
    }

    @Basic
    @Column(name = "sourcerelations")
    public int getSourcerelations() {
        return sourcerelations;
    }

    public void setSourcerelations(int sourcerelations) {
        this.sourcerelations = sourcerelations;
    }

    @Basic
    @Column(name = "receiverelation")
    public int getReceiverelation() {
        return receiverelation;
    }

    public void setReceiverelation(int receiverelation) {
        this.receiverelation = receiverelation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelationsEntity that = (RelationsEntity) o;

        if (relationsId != that.relationsId) return false;
        if (sourcerelations != that.sourcerelations) return false;
        if (receiverelation != that.receiverelation) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = relationsId;
        result = 31 * result + sourcerelations;
        result = 31 * result + receiverelation;
        return result;
    }
}
