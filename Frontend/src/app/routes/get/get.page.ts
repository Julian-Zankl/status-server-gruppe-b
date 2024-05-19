import {Component, inject} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";
import {StatusDto} from "../../shared/dtos/status.dto";
import {firstValueFrom} from "rxjs";

@Component({
  selector: 'app-status-get',
  standalone: true,
  imports: [
    MatButton,
    MatError,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule
  ],
  templateUrl: './get.page.html',
  styleUrl: './get.page.scss'
})
export class StatusGetPage {
  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);

  protected getStatusForm = this.fb.group({
    user: ['', Validators.required]
  });

  protected onSubmit() {
    if (this.getStatusForm.invalid) {
      return;
    }

    const status: StatusDto = {
      username: this.getStatusForm.value.user ?? '',
    }

    firstValueFrom(this.http.get<StatusDto>(`/api/statuses/${status.username}`)).then((res: StatusDto) => {
      this.snackBar.open(`${res.username} - ${res.statusText} - ${res.time}`, 'Schließen');
    }).catch(() => {
      this.snackBar.open('Fehler beim Abfragen des Status', 'Schließen')
    });
    }
}
