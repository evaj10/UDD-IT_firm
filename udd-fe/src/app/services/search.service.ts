import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { BasicSearch } from '../model/basic-search.model';
import { ResultPage } from '../model/result-page.model';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
  constructor(private http: HttpClient) {}

  basicSearch(searchRequest: BasicSearch) {
    return this.http.post<ResultPage>(
      environment.apiEndpoint + '/search/basic',
      searchRequest
    );
  }
}
