import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { HttpClientModule, HttpClient } from "@angular/common/http";
import { Router } from "@angular/router";
import { AuthService } from "../services/auth.service";

@Component({
  selector: "app-login",
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, HttpClientModule],
  templateUrl: "./login.component.html",
})
export class LoginComponent {
  username: string = "";
  password: string = "";
  loading = false;
  error: string | null = null;

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const sessionMsg = localStorage.getItem("sessionError");
    if (sessionMsg) {
      this.error = sessionMsg;
      localStorage.removeItem("sessionError");
    }
  }

  onSubmit() {
    this.loading = true;
    console.time("connexion");

    this.http
      .post<{ token: string }>("http://localhost:8080/auth/login", {
        username: this.username,
        password: this.password,
      })
      .subscribe({
        next: (res) => {
          console.timeEnd("connexion");

          localStorage.setItem("token", res.token);

          this.authService.setLoggedIn();

          setTimeout(() => {
            this.router.navigate(["/patients"]);
          }, 100);
        },
        error: (err) => {
          console.error("Erreur de login :", err);
          this.error = "Identifiants invalides";
        },
      })
      .add(() => {
        this.loading = false;
      });
  }
}
