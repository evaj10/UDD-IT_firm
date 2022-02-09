import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthRequest } from '../model/auth-request.model';
import { AuthResponse } from '../model/auth-response.model';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private http: HttpClient) {}

  login(authRequest: AuthRequest) {
    return this.http.post<AuthResponse>(
      environment.apiEndpoint + '/auth/login',
      authRequest
    );
  }
}
