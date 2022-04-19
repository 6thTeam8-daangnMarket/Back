package com.sparta.clone_backend.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "tb_sample")
@Entity
public class Sample implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sampleId;

    @Column
    private String sampleName;

    @Column(columnDefinition = "INTEGER DEFAULT 0")
    private int sampleTag;

    @Lob
    private String sampleDescription;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isEnabled;

    public Sample() {
    }

    public Sample(Long sampleId, String sampleName, int sampleTag, String sampleDescription, boolean isEnabled) {
        this.sampleId = sampleId;
        this.sampleName = sampleName;
        this.sampleTag = sampleTag;
        this.sampleDescription = sampleDescription;
        this.isEnabled = isEnabled;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public int getSampleTag() {
        return sampleTag;
    }

    public void setSampleTag(int sampleTag) {
        this.sampleTag = sampleTag;
    }

    public String getSampleDescription() {
        return sampleDescription;
    }

    public void setSampleDescription(String sampleDescription) {
        this.sampleDescription = sampleDescription;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "sampleId=" + sampleId +
                ", sampleName='" + sampleName + '\'' +
                ", sampleTag=" + sampleTag +
                ", sampleDescription='" + sampleDescription + '\'' +
                ", isEnabled=" + isEnabled +
                '}';
    }
}