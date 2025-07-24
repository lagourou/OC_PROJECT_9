import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Router } from "@angular/router";
import { BehaviorSubject, Observable } from "rxjs";
import { tap } from "rxjs/operators";

@Injectable({ providedIn: "root" })
export class AuthService {
  private isAuthenticated = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient, private router: Router) {}

  getAuthStatus(): Observable<boolean> {
    return this.isAuthenticated.asObservable();
  }

  login(username: string, password: string): Observable<{ token: string }> {
    return this.http
      .post<{ token: string }>("/auth/login", { username, password })
      .pipe(
        tap((response) => {
          console.log("Réponse du backend:", response);
          console.log("Token extrait:", response.token);

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
    this.isAuthenticated.next(false);
    localStorage.removeItem("token");
    this.router.navigate(["/login"]);
  }

  getToken(): string | null {
    return localStorage.getItem("token");
  }
}
