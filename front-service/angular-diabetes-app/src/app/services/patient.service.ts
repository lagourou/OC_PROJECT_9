import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { Observable } from "rxjs";
import { PatientDto } from "../models/patient.model";

@Injectable({ providedIn: "root" })
export class PatientService {
  private baseUrl = `${environment.apiUrl}/patients`;

  constructor(private http: HttpClient) {}

  getAllPatients(): Observable<PatientDto[]> {
    return this.http.get<PatientDto[]>(this.baseUrl, {});
  }

  getPatient(id: number): Observable<PatientDto> {
    return this.http.get<PatientDto>(`${this.baseUrl}/${id}`, {});
  }

  addPatient(patient: PatientDto): Observable<PatientDto> {
    return this.http.post<PatientDto>(this.baseUrl, patient);
  }

  updatePatient(id: number, patient: PatientDto): Observable<PatientDto> {
    return this.http.put<PatientDto>(`${this.baseUrl}/${id}`, patient);
  }
}
