import {Component, inject} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";
import {StatusDto} from "../../shared/dtos/status.dto";
import {firstValueFrom} from "rxjs";
import * as _moment from 'moment';
// tslint:disable-next-line:no-duplicate-imports
import {default as _rollupMoment} from 'moment';

const moment = _rollupMoment || _moment;

@Component({
  selector: 'app-status-update',
  standalone: true,
  imports: [
    MatButton,
    MatError,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule
  ],
  templateUrl: './update.page.html',
  styleUrl: './update.page.scss'
})
export class StatusUpdatePage {
  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);

  protected updateStatusForm = this.fb.group({
    user: ['', Validators.required],
    status: ['', Validators.required],
  });

  protected onSubmit() {
    if (this.updateStatusForm.invalid) {
      return;
    }

    const status: StatusDto = {
      username: this.updateStatusForm.value.user ?? '',
      statusText: this.updateStatusForm.value.status ?? '',
      time: moment().format('YYYY-MM-DDTHH:mm:ss')
    }

    firstValueFrom(this.http.put(`/api/statuses/${status.username}`, status)).then(() => {
      console.log(status)
      this.snackBar.open('Status wurde erfolgreich geändert', 'Schließen');
    }).catch(() => {
      this.snackBar.open('Fehler bei der Änderung des Status', 'Schließen')
    });
  }
}
