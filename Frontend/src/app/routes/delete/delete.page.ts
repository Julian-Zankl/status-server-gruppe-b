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
  selector: 'app-status-delete',
  standalone: true,
  imports: [
    MatButton,
    MatError,
    MatFormField,
    MatInput,
    MatLabel,
    ReactiveFormsModule
  ],
  templateUrl: './delete.page.html',
  styleUrl: './delete.page.scss'
})
export class StatusDeletePage {
  private fb = inject(FormBuilder);
  private http = inject(HttpClient);
  private snackBar = inject(MatSnackBar);

  protected deleteStatusForm = this.fb.group({
    user: ['', Validators.required]
  });

  protected onSubmit() {
    if (this.deleteStatusForm.invalid) {
      return;
    }

    const status: StatusDto = {
      username: this.deleteStatusForm.value.user ?? ''
    }

    firstValueFrom(this.http.delete(`/api/statuses/${status.username}`)).then(() => {
      this.snackBar.open('Status wurde erfolgreich gelöscht', 'Schließen');
    }).catch(() => {
      this.snackBar.open('Fehler beim Löschen des Status', 'Schließen')
    });
  }
}
