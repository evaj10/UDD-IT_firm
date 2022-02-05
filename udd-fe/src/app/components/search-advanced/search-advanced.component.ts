import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { AdvancedSearchField } from 'src/app/model/advanced-search-field.model';
import { AdvancedSearch } from 'src/app/model/advanced-search.model';
import { GeolocationSearch } from 'src/app/model/geolocation-search.model';
import { RangeSearch } from 'src/app/model/range-search.model';
import { ResultPage } from 'src/app/model/result-page.model';
import { SearchService } from 'src/app/services/search.service';
import { StateService } from 'src/app/services/state.service';

@Component({
  selector: 'app-search-advanced',
  templateUrl: './search-advanced.component.html',
  styleUrls: ['./search-advanced.component.css'],
})
export class SearchAdvancedComponent implements OnInit {
  results: ResultPage = {} as ResultPage;
  searchForm: FormGroup;
  pageEvent: PageEvent = new PageEvent();
  expandePanel: boolean = false;

  constructor(
    private searchService: SearchService,
    private stateService: StateService
  ) {
    this.searchForm = new FormGroup({
      applicantName: new FormControl(''),
      applicantNamePhrase: new FormControl(''),
      applicantNameMust: new FormControl('true'),
      applicantSurname: new FormControl(''),
      applicantSurnamePhrase: new FormControl(''),
      applicantSurnameMust: new FormControl('true'),
      applicantEducationLowerBound: new FormControl(''),
      applicantEducationUpperBound: new FormControl(''),
      applicantEducationMust: new FormControl('true'),
      applicantCvContent: new FormControl(''),
      applicantCvContentPhrase: new FormControl(''),
      applicantCvContentMust: new FormControl('true'),
      // location
      cityName: new FormControl(''),
      radius: new FormControl('', [Validators.min(1)]),
      radiusUnit: new FormControl(''),
    });
    this.pageEvent.pageIndex = 0;
    this.pageEvent.pageSize = 10;
  }

  ngOnInit(): void {
    this.results = this.stateService.advancedSearchPage.getValue();
    this.setStateSearchRequest();
  }

  search() {
    if (this.searchForm.invalid) {
      return;
    }

    const searchRequest = this.constructSearchRequest();
    this.stateService.advancedSearch.next(searchRequest);

    this.searchService
      .advancedSearch(
        searchRequest,
        this.pageEvent.pageIndex,
        this.pageEvent.pageSize
      )
      .subscribe((response) => {
        this.results = response;
        this.stateService.advancedSearchPage.next(response);
        this.expandePanel = false;
      });
  }

  pageChanged(event: PageEvent) {
    this.pageEvent = event;
    this.search();
  }

  setExpandePanel() {
    this.expandePanel = true;
  }

  constructSearchRequest(): AdvancedSearch {
    let fields = [];
    let rangeRequest;
    let geoLocation;
    if (this.searchForm.value.applicantName !== '') {
      fields.push(
        new AdvancedSearchField(
          'applicantName',
          this.searchForm.value.applicantName,
          this.searchForm.value.applicantNamePhrase === '' ? 'false' : 'true',
          this.searchForm.value.applicantNameMust
        )
      );
    }
    if (this.searchForm.value.applicantSurname !== '') {
      fields.push(
        new AdvancedSearchField(
          'applicantSurname',
          this.searchForm.value.applicantSurname,
          this.searchForm.value.applicantSurnamePhrase === ''
            ? 'false'
            : 'true',
          this.searchForm.value.applicantSurnameMust
        )
      );
    }
    if (this.searchForm.value.applicantCvContent !== '') {
      fields.push(
        new AdvancedSearchField(
          'cvContent',
          this.searchForm.value.applicantCvContent,
          this.searchForm.value.applicantCvContentPhrase === ''
            ? 'false'
            : 'true',
          this.searchForm.value.applicantCvContentMust
        )
      );
    }
    if (
      this.searchForm.value.applicantEducationLowerBound !== '' ||
      this.searchForm.value.applicantEducationUpperBound !== ''
    ) {
      rangeRequest = new RangeSearch(
        'applicantEducation',
        this.searchForm.value.applicantEducationLowerBound,
        this.searchForm.value.applicantEducationUpperBound,
        this.searchForm.value.applicantEducationMust
      );
    }
    if (
      this.searchForm.value.cityName !== '' &&
      this.searchForm.value.radius !== '' &&
      this.searchForm.value.radiusUnit !== ''
    ) {
      geoLocation = new GeolocationSearch(
        this.searchForm.value.cityName,
        this.searchForm.value.radius,
        this.searchForm.value.radiusUnit
      );
    }
    let searchRequest: AdvancedSearch = new AdvancedSearch(
      fields,
      rangeRequest,
      geoLocation
    );
    return searchRequest;
  }

  setStateSearchRequest() {
    const advancedSearch = this.stateService.advancedSearch.getValue();
    // fields
    this.setFieldsStateSearchRequest(advancedSearch);
    // range
    if (advancedSearch.rangeRequest) {
      this.searchForm.controls['applicantEducationLowerBound'].setValue(
        advancedSearch.rangeRequest?.lowerBound
      );
      this.searchForm.controls['applicantEducationUpperBound'].setValue(
        advancedSearch.rangeRequest?.upperBound
      );
      this.searchForm.controls['applicantEducationMust'].setValue(
        advancedSearch.rangeRequest?.mustContain
      );
    }
    // location
    if (advancedSearch.geoLocation) {
      this.searchForm.controls['cityName'].setValue(
        advancedSearch.geoLocation?.cityName
      );
      this.searchForm.controls['radius'].setValue(
        advancedSearch.geoLocation?.radius
      );
      this.searchForm.controls['radiusUnit'].setValue(
        advancedSearch.geoLocation?.radiusUnit
      );
    }
  }

  setFieldsStateSearchRequest(advancedSearch: AdvancedSearch) {
    const fields = advancedSearch.fields;
    if (fields) {
      fields.forEach((field) => {
        switch (field.field) {
          case 'applicantName':
            this.searchForm.controls['applicantName'].setValue(field.query);
            this.searchForm.controls['applicantNamePhrase'].setValue(
              field.phrase
            );
            this.searchForm.controls['applicantNameMust'].setValue(
              field.mustContain
            );
            break;
          case 'applicantSurname':
            this.searchForm.controls['applicantSurname'].setValue(field.query);
            this.searchForm.controls['applicantSurnamePhrase'].setValue(
              field.phrase
            );
            this.searchForm.controls['applicantSurnameMust'].setValue(
              field.mustContain
            );
            break;
          case 'cvContent':
            this.searchForm.controls['applicantCvContent'].setValue(
              field.query
            );
            this.searchForm.controls['applicantCvContentPhrase'].setValue(
              field.phrase
            );
            this.searchForm.controls['applicantCvContentMust'].setValue(
              field.mustContain
            );
            break;
        }
      });
    }
  }
}
