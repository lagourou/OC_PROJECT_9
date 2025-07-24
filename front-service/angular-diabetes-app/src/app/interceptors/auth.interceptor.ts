import { Injectable } from "@angular/core";
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
} from "@angular/common/http";
import { Observable } from "rxjs";
import { AuthService } from "../services/auth.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();

    console.log("Interceptor appelé pour:", req.url);
    console.log("Token utilisé:", token);

    const cloned = token
      ? req.clone({
          withCredentials: true,
          setHeaders: {
            Authorization: `Bearer ${token}`,
          },
        })
      : req.clone({
          withCredentials: true,
        });

    return next.handle(cloned);
  }
}
