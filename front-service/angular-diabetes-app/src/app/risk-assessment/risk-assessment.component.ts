import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { CommonModule } from "@angular/common";
import { AssessmentService } from "../services/assessment.service";
import { AssessmentDto } from "../models/assessment.model";
import { PatientDto } from "../models/patient.model";
import { PatientService } from "../services/patient.service";

@Component({
  selector: "app-risk-assessment",
  standalone: true,
  imports: [CommonModule],
  templateUrl: "./risk-assessment.component.html",
})
export class RiskAssessmentComponent implements OnInit {
  assessment: AssessmentDto | null = null;
  loading: boolean = true;
  error: string | null = null;
  patient: PatientDto | null = null;

  constructor(
    private route: ActivatedRoute,
    private assessmentService: AssessmentService,
    private patientService: PatientService
  ) {}

  ngOnInit(): void {
    const patId = Number(this.route.snapshot.paramMap.get("id"));
    if (!isNaN(patId)) {
      this.assessmentService.getAssessment(patId).subscribe({
        next: (data: AssessmentDto) => {
          this.assessment = data;
          this.loading = false;
        },
        error: (err) => {
          this.error = "Échec de l’évaluation";
          console.error(err);
          this.loading = false;
        },
      });

      this.patientService.getPatient(patId).subscribe({
        next: (data: PatientDto) => {
          this.patient = data;
        },
        error: (err) => {
          console.error("Échec du chargement du patient", err);
        },
      });
    }
  }
}
