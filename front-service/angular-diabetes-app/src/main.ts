import { bootstrapApplication } from "@angular/platform-browser";
import { AppComponent } from "./app/app.component";
import { importProvidersFrom, LOCALE_ID } from "@angular/core";
import { CommonModule, registerLocaleData } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { AppRoutingModule } from "./app/app-routing.module";
import {
  provideHttpClient,
  withInterceptorsFromDi,
  withFetch,
} from "@angular/common/http";
import { appProviders } from "./app/app.config";

import localeFr from "@angular/common/locales/fr";
registerLocaleData(localeFr);

bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(CommonModule, FormsModule, AppRoutingModule),
    provideHttpClient(withInterceptorsFromDi(), withFetch()),
    ...appProviders,

    { provide: LOCALE_ID, useValue: "fr-FR" },
  ],
});
