import {Component} from '@angular/core';
import {MatSidenav, MatSidenavContainer, MatSidenavContent} from "@angular/material/sidenav";
import {RouterOutlet} from "@angular/router";
import {NavComponent} from "../nav/nav.component";
import {HeaderComponent} from "../header/header.component";

@Component({
  selector: 'app-sidenav',
  standalone: true,
  imports: [
    MatSidenavContainer,
    RouterOutlet,
    MatSidenav,
    NavComponent,
    HeaderComponent,
    MatSidenavContent
  ],
  templateUrl: './sidenav.component.html',
  styleUrl: './sidenav.component.scss'
})
export class SidenavComponent {}
