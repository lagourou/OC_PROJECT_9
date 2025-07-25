import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { NoteDto } from "../models/note.model";
import { Observable } from "rxjs";

@Injectable({ providedIn: "root" })
export class NoteService {
  private baseUrl = `${environment.apiUrl}/notes`;

  constructor(private http: HttpClient) {}

  getAllNotes(): Observable<NoteDto[]> {
    return this.http.get<NoteDto[]>(this.baseUrl, {});
  }

  addNote(note: NoteDto): Observable<NoteDto> {
    return this.http.post<NoteDto>(this.baseUrl, note);
  }

  getNotesByPatient(patId: number): Observable<NoteDto[]> {
    return this.http.get<NoteDto[]>(`${this.baseUrl}/${patId}`);
  }
}
