import { Injectable } from "@angular/core";
import { CanActivate, Router, UrlTree } from "@angular/router";

@Injectable({
  providedIn: "root",
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean | UrlTree {
    // Ici tu peux vérifier si l'utilisateur est connecté via un token
    const isLoggedIn = !!localStorage.getItem("token");
    if (isLoggedIn) {
      return true;
    } else {
      // Sinon, on redirige vers la page login
      return this.router.parseUrl("/login");
    }
  }
}
