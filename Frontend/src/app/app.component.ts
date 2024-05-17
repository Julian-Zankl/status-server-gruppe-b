import { Component } from '@angular/core';
import { SidenavComponent } from "./shared/components/sidenav/sidenav.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [SidenavComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {}
