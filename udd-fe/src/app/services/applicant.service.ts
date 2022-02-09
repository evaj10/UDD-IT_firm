import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';

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

  formAccess() {
    this.getIpAddress().subscribe((response: any) => {
      this.http
        .post<void>(
          environment.apiEndpoint +
            '/applicant/access?ipAddress=' +
            response.ip,
          {}
        )
        .subscribe();
    });
  }

  getIpAddress() {
    return this.http.get(
      'https://cors-anywhere.herokuapp.com/http://api.ipify.org/?format=json'
    );
  }

  getApplicantCv(cvId: number): Observable<Blob> {
    return this.http
      .get(environment.apiEndpoint + '/applicant/cv/' + cvId, {
        responseType: 'blob',
        observe: 'response',
      })
      .pipe(
        map((res: any) => {
          return new Blob([res.body], { type: 'application/pdf' });
        })
      );
  }

  viewApplicantCv(cvId: number) {
    this.getApplicantCv(cvId).subscribe((res) => {
      const fileURL = URL.createObjectURL(res);
      window.open(fileURL, '_blank');
    });
  }
}
