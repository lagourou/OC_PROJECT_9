import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { NoteService } from "../services/note.service";
import { NoteDto } from "../models/note.model";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { PatientService } from "../services/patient.service";

@Component({
  selector: "app-note-add",
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: "./note-add.component.html",
})
export class NoteAddComponent implements OnInit {
  noteText: string = "";
  patId: string = "";
  patientName: string = "";

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private noteService: NoteService,
    private patientService: PatientService
  ) {}

  ngOnInit(): void {
    this.patId = this.route.snapshot.paramMap.get("id") ?? "";
    const id = parseInt(this.patId);

    this.patientService.getPatient(id).subscribe({
      next: (patient) => {
        this.patientName = `${patient.prenom} ${patient.nom}`;
      },
      error: (err) => {
        console.error("Erreur chargement patient", err);
        this.patientName = "Inconnu";
      },
    });
  }

  submitNote() {
    if (!this.noteText.trim()) return;

    const newNote: NoteDto = {
      patId: parseInt(this.patId),
      patient: this.patientName,
      note: this.noteText,
    };

    this.noteService.addNote(newNote).subscribe({
      next: () => this.router.navigate(["/patients", this.patId, "notes"]),
      error: (err) => console.error("Erreur lors de l'ajout de la note", err),
    });
  }

  cancel() {
    this.router.navigate(["/patients", this.patId, "notes"]);
  }
}
