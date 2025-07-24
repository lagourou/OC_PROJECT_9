import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { PatientService } from "../services/patient.service";
import { PatientDto } from "../models/patient.model";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";

@Component({
  selector: "app-patient-add",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./patient-add.component.html",
})
export class PatientAddComponent {
  patient: PatientDto = {
    nom: "",
    prenom: "",
    dateDeNaissance: new Date().toISOString().split("T")[0] as any,
    genre: "M",
    adresse: "",
    telephone: "",
  };
  error: string | null = null;

  constructor(private patientService: PatientService, private router: Router) {}

  addPatient() {
    this.patientService.addPatient(this.patient).subscribe({
      next: (newPatient: PatientDto) =>
        this.router.navigate(["/patients", newPatient.id]),
      error: () => (this.error = "Erreur lors de l’ajout du patient."),
    });
  }
}
