import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AdvancedSearch } from '../model/advanced-search.model';
import { BasicSearch } from '../model/basic-search.model';
import { ResultPage } from '../model/result-page.model';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
  constructor(private http: HttpClient) {}

  basicSearch(searchRequest: BasicSearch, page: number, size: number) {
    return this.http.post<ResultPage>(
      environment.apiEndpoint + '/search/basic?page=' + page + '&size=' + size,
      searchRequest
    );
  }

  advancedSearch(searchRequest: AdvancedSearch, page: number, size: number) {
    return this.http.post<ResultPage>(
      environment.apiEndpoint +
        '/search/advanced?page=' +
        page +
        '&size=' +
        size,
      searchRequest
    );
  }
}
