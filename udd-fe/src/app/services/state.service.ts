import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { AdvancedSearch } from '../model/advanced-search.model';
import { BasicSearch } from '../model/basic-search.model';
import { ResultPage } from '../model/result-page.model';

@Injectable({
  providedIn: 'root',
})
export class StateService {
  searchPage = new BehaviorSubject<ResultPage>({} as ResultPage);
  advancedSearchPage = new BehaviorSubject<ResultPage>({} as ResultPage);

  basicSearch = new BehaviorSubject<BasicSearch>({} as BasicSearch);
  advancedSearch = new BehaviorSubject<AdvancedSearch>({} as AdvancedSearch);

  constructor() {}
}
