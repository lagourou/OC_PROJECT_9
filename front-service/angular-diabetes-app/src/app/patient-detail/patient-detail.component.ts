import { Component, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { PatientDto } from "../models/patient.model";
import { PatientService } from "../services/patient.service";
import { ActivatedRoute } from "@angular/router";
import { RouterModule } from "@angular/router";

@Component({
  selector: "app-patient-detail",
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: "./patient-detail.component.html",
})
export class PatientDetailComponent implements OnInit {
  patient?: PatientDto;
  error: string | null = null;

  constructor(
    private patientService: PatientService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get("id"));
    if (!isNaN(id)) {
      this.patientService.getPatient(id).subscribe({
        next: (data: PatientDto) => {
          this.patient = data;
        },
        error: (err) => {
          this.error = "Patient introuvable.";
          console.error("Échec du chargement du patient", err);
        },
      });
    } else {
      this.error = "Identifiant de patient invalide.";
    }
  }
}
