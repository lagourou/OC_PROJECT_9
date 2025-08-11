import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Router } from "@angular/router";
import { BehaviorSubject, Observable } from "rxjs";
import { tap } from "rxjs/operators";
import { environment } from "../../environments/environment";

@Injectable({ providedIn: "root" })
export class AuthService {
  private isAuthenticated = new BehaviorSubject<boolean>(false);
  private baseUrl = `${environment.apiUrl}/auth/login`;

  constructor(private http: HttpClient, private router: Router) {}

  getAuthStatus(): Observable<boolean> {
    return this.isAuthenticated.asObservable();
  }

  login(username: string, password: string): Observable<any> {
    return this.http
      .post<{ token: string }>(
        this.baseUrl,
        { username, password },
        { withCredentials: true }
      )
      .pipe(
        tap((response) => {
          localStorage.setItem("token", response.token);
          this.setLoggedIn();
          this.router.navigate(["/patients"]);
        })
      );
  }

  setLoggedIn(): void {
    this.isAuthenticated.next(true);
  }

  logout(): void {
    localStorage.removeItem("token");
    this.isAuthenticated.next(false);
    this.router.navigate(["/login"]);
  }

  getToken(): string | null {
    return localStorage.getItem("token");
  }
}
