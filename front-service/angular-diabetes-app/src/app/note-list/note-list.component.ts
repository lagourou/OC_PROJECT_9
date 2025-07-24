import { Component, OnInit } from "@angular/core";
import { NoteDto } from "../models/note.model";
import { PatientDto } from "../models/patient.model";
import { NoteService } from "../services/note.service";
import { PatientService } from "../services/patient.service";
import { RouterModule } from "@angular/router";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";

@Component({
  selector: "app-note-list",
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: "./note-list.component.html",
})
export class NoteListComponent implements OnInit {
  notes: NoteDto[] = [];
  n: any;

  groupedNotes: { patId: number; patient: string; notes: string[] }[] = [];

  filter: string = "";

  constructor(
    private noteService: NoteService,
    private patientService: PatientService
  ) {}

  ngOnInit(): void {
    this.loadNotesWithAllPatients();
  }

  loadNotesWithAllPatients(): void {
    console.table(this.groupedNotes);

    this.patientService.getAllPatients().subscribe({
      next: (patients: PatientDto[]) => {
        this.noteService.getAllNotes().subscribe({
          next: (notes: NoteDto[]) => {
            const noteMap = new Map<number, string[]>();

            for (const note of notes) {
              const patId = Number(note.patId);
              if (!noteMap.has(patId)) {
                noteMap.set(patId, []);
              }
              noteMap.get(patId)!.push(note.note);
            }

            this.groupedNotes = patients.map((p) => ({
              patId: p.id!,
              patient: `${p.prenom} ${p.nom}`,
              notes: noteMap.get(Number(p.id!)) || [],
            }));
          },
          error: (err) => console.error("Erreur chargement notes", err),
        });
      },
      error: (err) => console.error("Erreur chargement patients", err),
    });
  }
  getFilteredNotes() {
    return this.groupedNotes.filter((group) =>
      group.patient.toLowerCase().includes(this.filter.toLowerCase())
    );
  }
}
