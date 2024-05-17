import {ApplicationConfig} from '@angular/core';
import {routes} from './app.routes';
import {provideRouter} from '@angular/router';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {provideHttpClient, withFetch} from "@angular/common/http";
import {provideMomentDateAdapter} from "@angular/material-moment-adapter";
import {DATE_FORMAT} from "../../locale.config";

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideAnimationsAsync(),
    provideHttpClient(withFetch()),
    provideMomentDateAdapter(DATE_FORMAT)
  ]
};
