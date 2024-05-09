import {Component, EventEmitter, Output} from '@angular/core';
import {MatToolbar} from "@angular/material/toolbar";
import {MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    MatToolbar,
    MatIconButton,
    MatIcon,
    RouterLink
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  @Output() public sidenavToggle = new EventEmitter();

  public title = 'Statusserver Frontend';

  public onToggleSidenav() {
    this.sidenavToggle.emit();
  }
}
