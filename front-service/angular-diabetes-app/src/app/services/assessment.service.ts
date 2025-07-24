import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "../../environments/environment";
import { Observable } from "rxjs";
import { AssessmentDto } from "../models/assessment.model";

@Injectable({ providedIn: "root" })
export class AssessmentService {
  private baseUrl = `${environment.apiUrl}/assessments`;

  constructor(private http: HttpClient) {}

  getAssessment(patId: number): Observable<AssessmentDto> {
    return this.http.get<AssessmentDto>(`${this.baseUrl}/${patId}`);
  }
}
