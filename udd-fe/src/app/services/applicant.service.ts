import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ApplicantService {
  constructor(private http: HttpClient) {}

  apply(application: FormData) {
    return this.http.post<number>(
      environment.apiEndpoint + '/applicant',
      application,
      { responseType: 'text' as 'json' }
    );
  }
}
