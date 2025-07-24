import { Component, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { PatientService } from "../services/patient.service";
import { PatientDto } from "../models/patient.model";

@Component({
  selector: "app-patient-list",
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: "./patient-list.component.html",
})
export class PatientListComponent implements OnInit {
  patients: PatientDto[] = [];
  filter: string = "";
  loading: boolean = true;
  error: string | null = null;

  constructor(private patientService: PatientService) {}

  ngOnInit(): void {
    this.patientService.getAllPatients().subscribe({
      next: (data: PatientDto[]) => {
        this.patients = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = "Impossible de charger la liste des patients.";
        console.error(err);
        this.loading = false;
      },
    });
  }

  filteredPatients() {
    return this.patients.filter((p) =>
      (p.prenom + " " + p.nom).toLowerCase().includes(this.filter.toLowerCase())
    );
  }

  trackById(index: number, patient: PatientDto): number {
    return patient.id ?? 0;
  }
}
