import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { PatientDto } from "../models/patient.model";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { PatientService } from "../services/patient.service";

@Component({
  selector: "app-patient-edit",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "patient-edit.component.html",
})
export class PatientEditComponent implements OnInit {
  patient!: PatientDto;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private patientService: PatientService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get("id"));
    this.patientService.getPatient(id).subscribe({
      next: (data) => (this.patient = data),
      error: () => (this.error = "Patient introuvable."),
    });
  }

  updatePatient() {
    if (this.patient.id !== undefined) {
      this.patientService
        .updatePatient(this.patient.id, this.patient)
        .subscribe({
          next: () => this.router.navigate(["/patients", this.patient.id]),
          error: () => (this.error = "Erreur lors de la mise à jour."),
        });
    } else {
      this.error = "Identifiant de patient invalide.";
    }
  }
}
