import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { BasicSearch } from 'src/app/model/basic-search.model';
import { ResultPage } from 'src/app/model/result-page.model';
import { Result } from 'src/app/model/result.model';
import { SearchService } from 'src/app/services/search.service';
import { StateService } from 'src/app/services/state.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  results: ResultPage = {} as ResultPage;
  searchForm: FormGroup;
  pageEvent: PageEvent = new PageEvent();

  constructor(
    private searchService: SearchService,
    private stateService: StateService
  ) {
    this.searchForm = new FormGroup({
      query: new FormControl('', [Validators.required]),
      field: new FormControl('', [Validators.required]),
    });
    this.pageEvent.pageIndex = 0;
    this.pageEvent.pageSize = 10;
  }

  ngOnInit(): void {
    this.results = this.stateService.searchPage.getValue();
    this.searchForm.controls['query'].setValue(
      this.stateService.basicSearch.getValue().query
    );
    this.searchForm.controls['field'].setValue(
      this.stateService.basicSearch.getValue().field
    );
  }

  search() {
    if (this.searchForm.invalid) {
      return;
    }

    const searchRequest: BasicSearch = new BasicSearch(
      this.searchForm.value.query,
      this.searchForm.value.field
    );
    this.stateService.basicSearch.next(searchRequest);

    if (
      searchRequest.field === 'applicantEducation' &&
      isNaN(Number(searchRequest.query))
    ) {
      return;
    }
    this.searchService
      .basicSearch(
        searchRequest,
        this.pageEvent.pageIndex,
        this.pageEvent.pageSize
      )
      .subscribe((response) => {
        this.results = response;
        this.stateService.searchPage.next(response);
      });
  }

  pageChanged(event: PageEvent) {
    this.pageEvent = event;
    this.search();
  }
}
