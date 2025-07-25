import { NgModule } from "@angular/core";
import { PreloadAllModules, RouterModule, Routes } from "@angular/router";
import { AuthGuard } from "./guards/auth.guard";

const routes: Routes = [
  {
    path: "login",
    loadComponent: () =>
      import("./login/login.component").then((m) => m.LoginComponent),
  },
  {
    path: "",
    canActivate: [AuthGuard],
    children: [
      { path: "", redirectTo: "patients", pathMatch: "full" },

      {
        path: "patients",
        loadComponent: () =>
          import("./patient-list/patient-list.component").then(
            (m) => m.PatientListComponent
          ),
      },
      {
        path: "patients/add",
        loadComponent: () =>
          import("./patient-add/patient-add.component").then(
            (m) => m.PatientAddComponent
          ),
      },
      {
        path: "patients/:id/edit",
        loadComponent: () =>
          import("./patient-edit/patient-edit.component").then(
            (m) => m.PatientEditComponent
          ),
      },
      {
        path: "patients/:id/notes/add",
        loadComponent: () =>
          import("./note-add/note-add.component").then(
            (m) => m.NoteAddComponent
          ),
      },
      {
        path: "patients/:id/notes",
        loadComponent: () =>
          import("./note-detail/note-detail.component").then(
            (m) => m.NoteDetailComponent
          ),
      },
      {
        path: "patients/:id/risk",
        loadComponent: () =>
          import("./risk-assessment/risk-assessment.component").then(
            (m) => m.RiskAssessmentComponent
          ),
      },
      {
        path: "patients/:id",
        loadComponent: () =>
          import("./patient-detail/patient-detail.component").then(
            (m) => m.PatientDetailComponent
          ),
      },
    ],
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      preloadingStrategy: PreloadAllModules,
    }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
