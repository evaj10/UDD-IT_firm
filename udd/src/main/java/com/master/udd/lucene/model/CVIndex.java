package com.master.udd.lucene.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Document(indexName = "cvs")
public class CVIndex {

    @Id
    @Field(type = FieldType.Text, index = false, store = true)
    private String id;

    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian")
    private String applicantName;

    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian")
    private String applicantSurname;

    @Field(type = FieldType.Integer)
    private Integer applicantEducation;

    @GeoPointField
    private GeoPoint applicantLocation;

    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian")
    private String cvContent;

    public CVIndex(String applicantName, String applicantSurname, Integer applicantEducation, String content) {
        this.applicantName = applicantName;
        this.applicantSurname = applicantSurname;
        this.applicantEducation = applicantEducation;
        this.applicantLocation = new GeoPoint(39.56553881520639, 2.650095237636433);
        this.cvContent = content;
    }

}
