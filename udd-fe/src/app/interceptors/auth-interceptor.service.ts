import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const jwtTokenWS = sessionStorage.getItem('jwtToken');
    if (!jwtTokenWS) {
      return next.handle(req);
    }
    const modifiedReq = req.clone({
      headers: req.headers.set('Authorization', 'Bearer ' + jwtTokenWS),
    });
    return next.handle(modifiedReq);
  }
}
