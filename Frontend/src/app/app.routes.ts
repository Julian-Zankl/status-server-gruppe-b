import { Routes } from '@angular/router';
import {StatusGetPage} from "./routes/get/get.page";
import {StatusDeletePage} from "./routes/delete/delete.page";
import {StatusAddPage} from "./routes/add/add.page";

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'add'
  },
  {
    path: 'add',
    component: StatusAddPage
  },
  {
    path: 'get',
    component: StatusGetPage
  },
  {
    path: 'delete',
    component: StatusDeletePage
  },
  {
    path: '**',
    component: StatusAddPage
  }
];
