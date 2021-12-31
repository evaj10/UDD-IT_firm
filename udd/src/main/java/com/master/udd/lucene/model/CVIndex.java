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

    // store za vrednosti koje zelimo prikazemo
    // (cuvaju se i u originalnom zapisu, pre analiziranja)
    // (inace se cuvaju samo u indeksu - kao zasebne pretprocesirane reci)

    // store ne koristimo za content da bismo ustedeli na memoriji
    // sav originalni unos se nalazi u _source
    // mozemo iz _source da iscitamo vrednosti content
    // (nemamo previse velikih polja, pa lako moze da se pronadje)
    // (bitan nam originalni zapis zbog highlighta)

    // tip Text, a ne Keyword za ime i prezime
    // (ako je keyword cuvao bi se kao jedinstveni izraz i morao bi tako i da se pretrazuje)
    // (prezime Da Vinci bi se cuvalo tako i korisnik bi morao da unese Da Vinci da bi ga nasao)
    // (prezime Janković bi se cuvalo tako i korisnik bi morao da unese sa ć da bi ga nasao)
    // (ne radi se pretprocesiranje)
    // kada je Text, radi se pretprocesiranje i cuva se izdeljeno na Term-ove u okviru indeksa
    // resava oba navedena problema

    // posto koristimo text, i cuva se u okviru indeksa splitovan
    // onda dodajemo store=true kako bismo cuvali i originalni zapis

    @Id
    private String id;

    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian", store = true)
    private String applicantName;

    @Field(type = FieldType.Text, searchAnalyzer = "serbian", analyzer = "serbian", store = true)
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
