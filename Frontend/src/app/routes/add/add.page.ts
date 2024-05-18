import {Component, inject} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import * as _moment from 'moment';
// tslint:disable-next-line:no-duplicate-imports
import {default as _rollupMoment} from 'moment';
import {MatButton} from "@angular/material/button";
import {HttpClient} from "@angular/common/http";
import {StatusDto} from "../../shared/dtos/status.dto";
import {firstValueFrom} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";

const moment = _rollupMoment || _moment;

@Component({
  selector: 'app-status-add',
  standalone: true,
  imports: [
    MatFormField,
    MatInput,
    ReactiveFormsModule,
    MatLabel,
    MatError,
    MatButton
  ],
  templateUrl: './add.page.html',
  styleUrl: './add.page.scss'
})
export class StatusAddPage {
  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);

  protected addStatusForm = this.fb.group({
    user: ['', Validators.required],
    status: ['', Validators.required],
  });

  protected onSubmit() {
    if (this.addStatusForm.invalid) {
      return;
    }

    const status: StatusDto = {
      username: this.addStatusForm.value.user ?? '',
      statustext: this.addStatusForm.value.status ?? '',
      uhrzeit: moment().toString()
    }

    firstValueFrom(this.http.post('/api/statuses', status)).then(() => {
      this.snackBar.open('Status wurde erfolgreich hinzugefügt', 'Schließen');
    }).catch(() => {
      this.snackBar.open('Fehler beim Hinzufügen des Status', 'Schließen')
    });
  }
}
