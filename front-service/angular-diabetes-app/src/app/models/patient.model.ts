export interface PatientDto {
  id?: number;
  prenom: string;
  nom: string;
  dateDeNaissance: Date;
  genre: string;
  adresse?: string;
  telephone?: string;
}
