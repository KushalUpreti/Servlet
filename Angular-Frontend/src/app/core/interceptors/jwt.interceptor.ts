import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { Auth } from 'src/app/shared/interfaces/auth.interface';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private readonly authService: AuthService) {}
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const auth: Auth = this.authService.getAuth();
    if (!!auth.token) {
      const tokenizedReq = req.clone({
        headers: req.headers.set('AUTHORIZATION', auth.token),
      });
      return next.handle(tokenizedReq);
    }

    return next.handle(req);
  }
}
