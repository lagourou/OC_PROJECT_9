import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { RouterModule, ActivatedRoute } from "@angular/router";
import { NoteService } from "../services/note.service";

@Component({
  selector: "app-note-details",
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: "./note-detail.component.html",
})
export class NoteDetailComponent implements OnInit {
  notes: string[] = [];
  patientId!: number;

  constructor(
    private noteService: NoteService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.patientId = +params["id"];
      this.noteService.getNotesByPatient(this.patientId).subscribe({
        next: (noteDtos) => {
          this.notes = noteDtos.map((n) => n.note);
        },
        error: (err) => console.error("Erreur chargement notes patient", err),
      });
    });
  }
}
