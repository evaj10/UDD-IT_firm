import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Stats } from '../model/stats.model';

@Injectable({
  providedIn: 'root',
})
export class StatsService {
  constructor(private http: HttpClient) {}

  byCity() {
    return this.http.get<Stats[]>(environment.apiEndpoint + '/stats/city');
  }

  byDay() {
    return this.http.get<Stats[]>(environment.apiEndpoint + '/stats/day');
  }
}
