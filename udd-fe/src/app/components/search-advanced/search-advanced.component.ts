import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { PageEvent } from '@angular/material/paginator';
import { AdvancedSearch } from 'src/app/model/advanced-search.model';
import { BasicSearch } from 'src/app/model/basic-search.model';
import { ResultPage } from 'src/app/model/result-page.model';
import { SearchService } from 'src/app/services/search.service';

@Component({
  selector: 'app-search-advanced',
  templateUrl: './search-advanced.component.html',
  styleUrls: ['./search-advanced.component.css'],
})
export class SearchAdvancedComponent implements OnInit {
  results: ResultPage = {} as ResultPage;
  searchForm: FormGroup;
  pageEvent: PageEvent = new PageEvent();

  constructor(private searchService: SearchService) {
    this.searchForm = new FormGroup({
      // query: new FormControl('', [Validators.required]),
      // field: new FormControl('', [Validators.required]),
    });
    this.pageEvent.pageIndex = 0;
    this.pageEvent.pageSize = 10;
  }

  ngOnInit(): void {}

  search() {
    if (this.searchForm.invalid) {
      return;
    }

    const searchRequest: BasicSearch = new BasicSearch('sistem', 'all');
    this.searchService
      .basicSearch(
        searchRequest,
        this.pageEvent.pageIndex,
        this.pageEvent.pageSize
      )
      .subscribe((response) => {
        this.results = response;
      });
  }

  pageChanged(event: PageEvent) {
    this.pageEvent = event;
    this.search();
  }
}
