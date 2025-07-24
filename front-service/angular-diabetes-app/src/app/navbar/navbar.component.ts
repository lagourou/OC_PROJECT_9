import { Component } from "@angular/core";
import { Router, RouterModule } from "@angular/router";
import { CommonModule } from "@angular/common";

@Component({
  selector: "app-navbar",
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: "./navbar.component.html",
})
export class NavbarComponent {
  constructor(public router: Router) {}

  logout() {
    localStorage.removeItem("token");
    this.router.navigate(["/login"]);
  }

  isOnNoteListPage(): boolean {
    return this.router.url.match(/^\/patients\/\d+\/notes$/) !== null;
  }

  isOnPatientDetailPage(): boolean {
    return this.router.url.match(/^\/patients\/\d+$/) !== null;
  }

  getCurrentPatientId(): string | null {
    const match = this.router.url.match(/^\/patients\/(\d+)/);
    return match ? match[1] : null;
  }

  isOnPatientSubPage(): boolean {
    return (
      this.router.url.match(/^\/patients\/\d+\/(notes|assessment|risk)$/) !==
      null
    );
  }

  showBackToPatient(): boolean {
    return this.isOnPatientSubPage();
  }

  showBackToList(): boolean {
    return this.isOnNoteListPage() || this.isOnPatientDetailPage();
  }
}
